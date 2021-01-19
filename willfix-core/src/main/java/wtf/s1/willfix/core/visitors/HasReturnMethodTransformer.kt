package wtf.s1.willfix.core.visitors

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.tree.*
import wtf.s1.willfix.core.IWillFixContext
import wtf.s1.willfix.core.const0Opcode

class HasReturnMethodTransformer(
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

    override fun transform() {
        val firstNode = instructions.first
        val returnType = Type.getReturnType(desc)

        val nextLocalIndex = localVariables?.size ?: 0
        val beforeInsnList = InsnList()
        beforeInsnList.add(InsnNode(returnType.const0Opcode()))
        beforeInsnList.add(VarInsnNode(returnType.getOpcode(Opcodes.ISTORE), nextLocalIndex))
        beforeInsnList.add(from)

        var iterator = instructions.iterator()

        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.opcode in Opcodes.IRETURN until Opcodes.ARETURN) {
                val storeReturnInsnList = InsnList()
                storeReturnInsnList.add(
                    VarInsnNode(
                        returnType.getOpcode(Opcodes.ISTORE),
                        nextLocalIndex
                    )
                )
                storeReturnInsnList.add(
                    VarInsnNode(
                        returnType.getOpcode(Opcodes.ILOAD),
                        nextLocalIndex
                    )
                )
                instructions.insertBefore(next, storeReturnInsnList)
            }
        }

        val afterInsnList = InsnList()
        afterInsnList.add(to)
        afterInsnList.add(target)
        val exceptionLocal = nextLocalIndex + 1
        afterInsnList.add(VarInsnNode(Opcodes.ASTORE, exceptionLocal))
        afterInsnList.add(VarInsnNode(Opcodes.ALOAD, exceptionLocal))
        afterInsnList.add(
            MethodInsnNode(
                Opcodes.INVOKEVIRTUAL, "java/lang/Exception",
                "printStackTrace",
                "()V",
                false
            )
        )
        afterInsnList.add(VarInsnNode(returnType.getOpcode(AdviceAdapter.ILOAD), nextLocalIndex))
        afterInsnList.add(InsnNode(returnType.getOpcode(AdviceAdapter.IRETURN)))

        instructions.insertBefore(firstNode, beforeInsnList)
        instructions.add(afterInsnList)
        tryCatchBlocks.add(TryCatchBlockNode(from, to, target, "java/lang/Exception"))
    }
}