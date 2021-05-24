package wtf.s1.stick.core

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.MethodVisitor
import wtf.s1.stick.bytex.StickByteXContext
import wtf.s1.stick.core.MethodTrace.Companion.STICK_PREFIX

class TraceClassVisitor(private val context: StickByteXContext, private val asmApi: Int) :
    BaseClassVisitor() {

    companion object {
        val WELL_KNOW_PACKAGE_PREFIX = arrayOf(
            "kotlin",
            "androidx",
            "com/google",
            "okhttp",
            "okio",
            "android/support")
    }

    private var clazzName: String = ""
    private var notTrans = false

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        clazzName = name ?: ""
        notTrans = isInternalClazz(clazzName, interfaces)
                || isWellKnowSource(clazzName)
        super.visit(version, access, name, signature, superName, interfaces)

    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val superVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (notTrans) {
            return superVisitor
        }
        return TraceMethodVisitor(
            context,
            superVisitor,
            clazzName,
            asmApi,
            access,
            name,
            descriptor,
            signature,
            exceptions
        )
    }

    private fun isInternalClazz(clazzName: String?, interfaces: Array<out String>?): Boolean {
        if (clazzName?.startsWith(STICK_PREFIX) == true) {
            return true
        }

        interfaces?.forEach {
            if (it.startsWith(STICK_PREFIX)) {
                return true
            }
        }
        return false
    }

    private fun isWellKnowSource(clazzName: String?): Boolean {
        WELL_KNOW_PACKAGE_PREFIX.forEach {
            if (clazzName?.startsWith(it) == true)
                return true
        }
        return false
    }
}