package wtf.s1.willfix.core.visitors

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.GOTO
import org.objectweb.asm.tree.*
import wtf.s1.willfix.core.IWillFixContext

class VoidReturnMethodTransformer(
    context: IWillFixContext,
    methodVisitor: MethodVisitor,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?,
) : WillFixMethodTransformer(
    context,
    methodVisitor,
    api,
    access,
    name,
    descriptor,
    signature,
    exceptions
) {

    private val from = LabelNode()
    private val to = LabelNode()
    private val target = LabelNode()
    private val go = LabelNode()

    override fun transform() {
        findReturnInsn(instructions.last)?.let { returnNode ->
            val first = instructions.first
            instructions.insertBefore(first, from)

            val addInsnList = InsnList()
            addInsnList.add(to)
            addInsnList.add(JumpInsnNode(GOTO, go))
            addInsnList.add(target)
            val nextLocal = localVariables.size
            addInsnList.add(VarInsnNode(Opcodes.ASTORE, nextLocal))
            addInsnList.add(VarInsnNode(Opcodes.ALOAD, nextLocal))
            addInsnList.add(
                MethodInsnNode(
                    Opcodes.INVOKEVIRTUAL, "java/lang/Exception",
                    "printStackTrace",
                    "()V",
                    false
                )
            )
            addInsnList.add(go)
            instructions.insertBefore(returnNode, addInsnList)

            tryCatchBlocks.add(TryCatchBlockNode(from, to, target, "java/lang/Exception"))
        }
    }

    private fun findReturnInsn(lastNode: AbstractInsnNode): AbstractInsnNode? {
        if (lastNode.opcode == Opcodes.RETURN) {
            return lastNode
        }
        lastNode.previous?.let {
            return findReturnInsn(it)
        }
        return null
    }


}