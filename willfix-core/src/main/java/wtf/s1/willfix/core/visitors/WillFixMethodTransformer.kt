package wtf.s1.willfix.core.visitors

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.MethodNode
import wtf.s1.willfix.core.IWillFixContext

abstract class WillFixMethodTransformer(
    protected val context: IWillFixContext,
    private val methodVisitor: MethodVisitor,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?,
) : MethodNode(
    api,
    access,
    name,
    descriptor,
    signature,
    exceptions
) {
    override fun visitEnd() {
        super.visitEnd()
        transform()
        accept(methodVisitor)
    }

    abstract fun transform()
}