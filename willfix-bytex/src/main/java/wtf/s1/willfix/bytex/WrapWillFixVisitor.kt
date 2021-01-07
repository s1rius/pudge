package wtf.s1.willfix.bytex

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import wtf.s1.willfix.core.visitors.WillFixClassVisitor

class WrapWillFixVisitor(private val context: WillFixByteXContext) : BaseClassVisitor() {

    private var clazzName: String? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        clazzName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return context.methodVisitor(clazzName, access, name, descriptor, signature, exceptions, methodVisitor)
    }

}