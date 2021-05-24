package wtf.s1.pudge.hugo2

import java.lang.StringBuilder

open class ParamsBuilder {

    companion object {
        const val CLAZZ = "wtf/s1/pudge/hugo2/ParamsBuilder"
        const val APPEND_DESC = "(Ljava/lang/String;%s)V"
    }

    private val sb = StringBuilder()

     fun append(name: String, value: Boolean) {
         append(name, value as Any?)
     }

    fun append(name: String, value: Byte) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Short) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Char) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Int) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Long) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Double) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Float) {
        append(name, value as Any?)
    }

    fun append(name: String, value: Any?) {
        sb.append(name)
        sb.append("=")
        sb.append(Strings.toString(value))
        sb.append(",")
    }

    override fun toString(): String {
        return sb.toString()
    }
}