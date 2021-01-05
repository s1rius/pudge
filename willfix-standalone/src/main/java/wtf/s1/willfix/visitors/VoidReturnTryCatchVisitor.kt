package wtf.s1.willfix.visitors

import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter
import wtf.s1.willfix.WillFixContext

class VoidReturnTryCatchVisitor(
    private val context: WillFixContext,
    methodVisitor: MethodVisitor,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    private var finalGoLabel: Label? = null
    private val error = "java/lang/Exception"
    private val start: Label = Label()
    private val end: Label = Label()
    private val catchStart = Label()

    override fun visitCode() {
        super.visitCode()
        context.logger().d("visitCode")
    }

    override fun visitEnd() {
        context.logger().d("visitEnd")
        super.visitEnd()
    }

    override fun onMethodEnter() {
        visitTryCatchBlock(
            start,
            end,
            catchStart,
            error
        )
        visitLabel(start)
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        mv.visitLabel(end)
        finalGoLabel = newLabel()
        mv.visitJumpInsn(GOTO, finalGoLabel)
        mv.visitLabel(catchStart)
        mv.visitVarInsn(Opcodes.ASTORE, nextLocal)
        mv.visitVarInsn(Opcodes.ALOAD, nextLocal)
        mv.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            "java/lang/Exception",
            "printStackTrace",
            "()V",
            false
        )

        mv.visitLabel(finalGoLabel)
        super.onMethodExit(opcode)
        context.logger().d("onMethodExit")
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        context.logger().d("visitMaxs $maxStack $maxLocals")
        super.visitMaxs(maxStack, maxLocals)
    }
}