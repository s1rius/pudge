package wtf.s1.willfix.core

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import java.lang.IllegalStateException

fun Type.const0Opcode(): Int {
    return when (sort) {
        Type.BOOLEAN,
        Type.BYTE,
        Type.CHAR,
        Type.INT -> Opcodes.ICONST_0
        Type.FLOAT -> Opcodes.FCONST_0
        Type.DOUBLE -> Opcodes.DCONST_0
        Type.LONG -> Opcodes.LCONST_0
        else -> throw IllegalStateException("UnExcept type cant const0")
    }

}