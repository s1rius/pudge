package wtf.s1.willfix.core.visitors

import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter
import wtf.s1.willfix.core.IWillFixContext

class VoidReturnTryCatchVisitor(
    private val context: IWillFixContext,
    methodVisitor: MethodVisitor,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    private val error = IWillFixContext.ERR

    private var finalGoLabel: Label? = null
    private lateinit var from: Label
    private lateinit var to: Label
    private val target = Label()
    private val tryCatchMap = hashMapOf<Label, Label>()

    override fun visitCode() {
        super.visitCode()
        context.logger().d("visitCode")
    }

    override fun visitEnd() {
        context.logger().d("visitEnd")
        super.visitEnd()
    }

    override fun onMethodEnter() {
        context.logger().d("onMethodEnter")
        from = newLabel()
        to = newLabel().apply {
            tryCatchMap[from] = this
        }
        visitTryCatchBlock(
            from,
            to,
            target,
            error
        )
        visitLabel(from)
        super.onMethodEnter()
    }

    override fun onMethodExit(opcode: Int) {
        tryCatchMap.remove(from)?.let {
            mv.visitLabel(it)
            finalGoLabel = newLabel()
            mv.visitJumpInsn(GOTO, finalGoLabel)
            mv.visitLabel(target)
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
        }
        super.onMethodExit(opcode)
        context.logger().d("onMethodExit")
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        context.logger().d("visitMaxs $maxStack $maxLocals")
        super.visitMaxs(maxStack, maxLocals)
    }
}