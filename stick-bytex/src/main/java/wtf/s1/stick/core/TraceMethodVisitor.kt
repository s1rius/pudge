package wtf.s1.stick.core

import org.objectweb.asm.*
import org.objectweb.asm.tree.*
import wtf.s1.stick.bytex.StickByteXContext
import wtf.s1.stick.core.MethodTrace.Companion.TRACE_CLAZZ
import wtf.s1.stick.isClinit
import wtf.s1.stick.isInit
import wtf.s1.stick.returnCode

class TraceMethodVisitor(
    private val context: StickByteXContext,
    private val methodVisitor: MethodVisitor,
    private val clazz: String,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?
) : MethodNode(api, access, name, descriptor, signature, exceptions) {

    var hasStickAnn: Boolean = false
    var traceConfig = context.config()

    override fun visitEnd() {
        super.visitEnd()
        methodTransform()
        accept(methodVisitor)
    }

    private fun methodTransform() {
        visibleAnnotations?.forEach {
            if (Stick.CLAZZNAME == it.desc) {
                hasStickAnn = true
            }
        }

        if (!hasStickAnn && traceConfig.onlyAnn) {
            return
        }

        if (clazz.startsWith(MethodTrace.STICK_PREFIX)) {
            return
        }

        if (isInit() || isClinit()) {
            return
        }

        if (instructions == null || instructions.size() <= 0) {
            return
        }

        val methodFind = MethodTrace.methodFind(clazz, name, desc)
        context.logger.d("trace method $methodFind")

        instructions.insertBefore(instructions.first, genI(methodFind))

        if (Type.getReturnType(desc) == Type.VOID_TYPE) {
            var lastNode = instructions.last

            while (true) {
                if (lastNode != null && lastNode.opcode == Opcodes.RETURN) {
                    break
                }
                if (lastNode == null) {
                    return
                }
                lastNode = lastNode.previous
            }
            instructions.insertBefore(lastNode, genO(methodFind))
        } else {
            val returnCode = Type.getReturnType(desc).returnCode()

            var lastNode = instructions.last

            while (true) {
                if (lastNode != null && lastNode.opcode == returnCode) {
                    instructions.insertBefore(lastNode, genO(methodFind))
                }
                if (lastNode == null) {
                    return
                }
                lastNode = lastNode.previous
            }
        }
    }

    private fun genI(methodFind: MethodInfo): InsnList {
        val beginList = InsnList()
        beginList.add(LdcInsnNode(methodFind.id))
        beginList.add(
            MethodInsnNode(
                Opcodes.INVOKESTATIC,
                TRACE_CLAZZ, "i", "(I)V", false
            )
        )
        return beginList
    }

    private fun genO(methodFind: MethodInfo): InsnList {

        val afterList = InsnList()

        afterList.add(LdcInsnNode(methodFind.id))
        afterList.add(
            MethodInsnNode(
                Opcodes.INVOKESTATIC,
                TRACE_CLAZZ, "o", "(I)V", false
            )
        )
        return afterList
    }


}