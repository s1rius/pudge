package wtf.s1.pudge.hugo2

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor

class Hugo2ClassVisitor(private val context: Hugo2ByteXContext) : BaseClassVisitor() {

    private var clazz: String? = null
    private var clazzAnnotated = false

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        clazz = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        if (Hugo2.DEBUGLOGDESC == descriptor) {
            clazzAnnotated = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val visitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return Hugo2MethodVisitor(visitor, context, clazz, clazzAnnotated, api, access, name, descriptor, signature, exceptions)
    }
}