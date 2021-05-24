package wtf.s1.pudge.hugo2

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.MethodNode

fun MethodNode.isInit(): Boolean {
    return "<init>" == name
}

fun MethodNode.isClinit(): Boolean {
    return "<clinit>" == name
}

fun Type.returnCode(): Int {
    return when (this) {
        Type.BYTE_TYPE,
        Type.CHAR_TYPE,
        Type.INT_TYPE,
        Type.SHORT_TYPE,
        Type.BOOLEAN_TYPE -> Opcodes.IRETURN
        Type.LONG_TYPE -> Opcodes.LRETURN
        Type.FLOAT_TYPE -> Opcodes.FRETURN
        Type.DOUBLE_TYPE -> Opcodes.DRETURN
        else -> Opcodes.ARETURN
    }
}