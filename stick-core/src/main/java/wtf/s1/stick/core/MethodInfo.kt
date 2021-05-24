package wtf.s1.stick.core

/**
 * Method structure use to trace
 */
data class MethodInfo(val clazz: String, val name: String, val desc: String) {

    val id: Int

     init {
        var result = clazz.hashCode()
        result = 31 * result + name.hashCode()
        id = 31 * result + desc.hashCode()
    }

    override fun toString(): String {
        return "method(clazz='$clazz', name='$name', desc='$desc', id=$id)"
    }
}