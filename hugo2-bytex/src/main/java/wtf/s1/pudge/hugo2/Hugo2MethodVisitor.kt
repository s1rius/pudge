package wtf.s1.pudge.hugo2

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class Hugo2MethodVisitor(
    private val methodVisitor: MethodVisitor,
    private val context: Hugo2ByteXContext,
    private val clazz: String?,
    private val clazzAnnotated: Boolean = false,
    api: Int,
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    exceptions: Array<out String>?
) : MethodNode(api, access, name, descriptor, signature, exceptions) {

    private var methodAnnotated: Boolean = false
    private var firstLabel: Label? = null
    private val paramList = arrayListOf<TraceMethodParam>()

    override fun visitLabel(label: Label?) {
        super.visitLabel(label)
        if (firstLabel == null) {
            firstLabel = label
        }
    }

    override fun visitLocalVariable(
        name: String?,
        descriptor: String?,
        signature: String?,
        start: Label?,
        end: Label?,
        index: Int
    ) {
        super.visitLocalVariable(name, descriptor, signature, start, end, index)
        if (start == firstLabel && "this" != name) {
            paramList.add(TraceMethodParam(name, descriptor, index))
        }
    }

    override fun visitEnd() {
        super.visitEnd()
        methodTransform()
        accept(methodVisitor)
    }

    private fun methodTransform() {
        visibleAnnotations?.forEach {
            if (Hugo2.DEBUGLOGDESC == it.desc) {
                methodAnnotated = true
            }
        }

        if (!methodAnnotated && !clazzAnnotated) {
            return
        }

        if (isInit() || isClinit()) {
            return
        }

        if (instructions == null || instructions.size() <= 0) {
            return
        }

        context.logger.d("$clazz $name transform")

        logIIL().let {
            instructions.insertBefore(instructions.first, it)
        }

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
            instructions.insertBefore(lastNode, genO())
        } else {
            val returnCode = Type.getReturnType(desc).returnCode()

            var lastNode = instructions.last

            while (true) {
                if (lastNode != null && lastNode.opcode == returnCode) {
                    instructions.insertBefore(lastNode, genO())
                }
                if (lastNode == null) {
                    return
                }
                lastNode = lastNode.previous
            }
        }
    }

    private fun genO(): InsnList {

        val afterList = InsnList()

        if (context.extension.isEnable) {
            afterList.add(LdcInsnNode(clazz))
            afterList.add(LdcInsnNode(name))
            afterList.add(
                MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    Hugo2.CLAZZ,
                    "logO",
                    "(Ljava/lang/String;Ljava/lang/String;)V",
                    false
                )
            )
        }
        return afterList
    }

    private fun logIIL(): InsnList {
        if (paramList.size == 0) {
            val il = InsnList()
            il.add(LdcInsnNode(clazz))
            il.add(LdcInsnNode(name))
            il.add(LdcInsnNode("void"))

            il.add(
                MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    Hugo2.CLAZZ,
                    "logI",
                    "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                    false
                )
            )
            return il
        }

        val il = InsnList()
        il.add(TypeInsnNode(Opcodes.NEW, ParamsBuilder.CLAZZ))
        il.add(InsnNode(Opcodes.DUP))
        il.add(MethodInsnNode(Opcodes.INVOKESPECIAL, ParamsBuilder.CLAZZ, "<init>", "()V", false))
        val pIndex = localVariables.size
        il.add(VarInsnNode(Opcodes.ASTORE, pIndex))
        paramList.forEach {
            it.paramDesc()?.let { desc ->
                il.add(VarInsnNode(Opcodes.ALOAD, pIndex))
                il.add(LdcInsnNode(it.name))
                il.add(VarInsnNode(it.loadOpcode(), it.varIndex))
                il.add(
                    MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        ParamsBuilder.CLAZZ,
                        "append",
                        ParamsBuilder.APPEND_DESC.format(desc),
                        false
                    )
                )
            }
        }

        il.add(LdcInsnNode(clazz))
        il.add(LdcInsnNode(name))
        il.add(VarInsnNode(Opcodes.ALOAD, pIndex))
        il.add(
            MethodInsnNode(
                Opcodes.INVOKEVIRTUAL,
                ParamsBuilder.CLAZZ,
                "toString",
                "()Ljava/lang/String;",
                false
            )
        )

        il.add(
            MethodInsnNode(
                Opcodes.INVOKESTATIC,
                Hugo2.CLAZZ,
                "logI",
                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                false
            )
        )

        return il
    }
}