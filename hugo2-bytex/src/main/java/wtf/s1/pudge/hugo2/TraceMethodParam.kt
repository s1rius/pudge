package wtf.s1.pudge.hugo2

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

data class TraceMethodParam(val name: String?, val desc: String?, val varIndex: Int) {

    fun loadOpcode(): Int {
        return Type.getType(desc).getOpcode(Opcodes.ILOAD)
    }

    fun paramDesc(): String? {
        desc?.let {
            if(desc.startsWith("[") || desc.startsWith("L")) {
                return "Ljava/lang/Object;"
            }
        }
        return desc
    }
}