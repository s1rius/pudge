package wtf.s1.willfix.core.visitors

import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter
import wtf.s1.willfix.core.IWillFixContext
import wtf.s1.willfix.core.const0Opcode
import java.lang.RuntimeException

class HasReturnVisitor(
    private val context: IWillFixContext,
    methodVisitor: MethodVisitor,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    private var tempStore: Int = -1
    private val error = "java/lang/Exception"
    private lateinit var from: Label
    private lateinit var to: Label
    private val target = Label()

    override fun visitCode() {
        super.visitCode()

        checkReturnTypeValid()

        context.logger().d("visitCode")
    }

    override fun visitEnd() {
        context.logger().d("visitEnd")
        super.visitEnd()
    }

    override fun visitInsn(opcode: Int) {
        super.visitInsn(opcode)
        context.logger().d("visitInsn $opcode")
    }

    override fun onMethodEnter() {
        super.onMethodEnter()
        context.logger().d("onMethodEnter")

        if (tempStore == -1) {
            tempStore = newLocal(returnType)
        }

        mv.visitInsn(returnType.const0Opcode())
        mv.visitVarInsn(returnType.getOpcode(ISTORE), tempStore)
        from = newLabel()
        to = newLabel()
        visitTryCatchBlock(
            from,
            to,
            target,
            error
        )
        visitLabel(from)
    }

    override fun onMethodExit(opcode: Int) {

        mv.visitVarInsn(returnType.getOpcode(ISTORE), tempStore)
        mv.visitVarInsn(returnType.getOpcode(ILOAD), tempStore)

        mv.visitLabel(to)
        context.logger().d("onMethodExit")
        super.onMethodExit(opcode)
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
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

        mv.visitVarInsn(returnType.getOpcode(ILOAD), tempStore)
        mv.visitInsn(returnType.getOpcode(IRETURN))

        context.logger().d("visitMaxs $maxStack $maxLocals")
        super.visitMaxs(maxStack, maxLocals)
    }

    private fun checkReturnTypeValid() {
        when (returnType.sort) {
            Type.ARRAY,
            Type.OBJECT,
            Type.VOID,
            Type.METHOD -> throw RuntimeException("return type is not support")
        }
    }
}