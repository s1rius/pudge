package wtf.s1.willfix.core.visitors

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import wtf.s1.willfix.core.IWillFixContext

class WillFixClassVisitor(
    version: Int,
    visitor: ClassVisitor?,
    private val context: IWillFixContext
) : ClassVisitor(version, visitor) {

    private var currentClazzName: String? = null

    override fun visitMethod(
        access: Int,
        methodName: String?,
        methodDesc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val visitMethod = super.visitMethod(access, methodName, methodDesc, signature, exceptions)
        return context.methodVisitor(
            currentClazzName,
            access,
            methodName,
            methodDesc,
            signature,
            exceptions,
            visitMethod
        )
    }

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        context.logger().d(name)
        currentClazzName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    fun replaceVisitor(visitor: ClassVisitor?) {
        this.cv = visitor
    }
}