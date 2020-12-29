package wtf.s1.willfix.visitors

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import wtf.s1.willfix.WillFixContext

class WillFixClassVisitor(
    version: Int,
    visitor: ClassVisitor,
    private val context: WillFixContext
) : ClassVisitor(version, visitor) {

    private var currentClazzName: String? = null
    private var thisClazzNeedFix = false

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val visitMethod = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (thisClazzNeedFix && context.isMethodNeedTryCatch(currentClazzName, name, descriptor)) {
            context.logger().i("transform this method $access $name $descriptor")
            if (Type.getReturnType(descriptor) == Type.VOID_TYPE) {
                context.logger().d("void return")
                 VoidReturnTryCatchVisitor(context, visitMethod, this.api, access, name, descriptor)
            } else {
                context.logger().d("has return")
                HasReturnVisitor(context, visitMethod, this.api, access, name, descriptor)
            }
        } else {
            visitMethod
        }
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
        thisClazzNeedFix = context.isClazzNeedVisit(name)
        if (thisClazzNeedFix) {
            context.logger().i("visit this class $name")
        }
        currentClazzName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }
}