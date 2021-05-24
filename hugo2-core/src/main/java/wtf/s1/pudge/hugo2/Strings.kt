package wtf.s1.pudge.hugo2

import java.lang.AssertionError
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

class Strings private constructor() {
    companion object {
        fun toString(obj: Any?): String {
            if (obj == null) {
                return "null"
            }
            if (obj is CharSequence) {
                return '"'.toString() + printableToString(obj.toString()) + '"'
            }
            val cls: Class<*> = obj.javaClass
            if (Byte::class.java == cls) {
                return byteToString(obj as Byte?)
            }
            return if (cls.isArray) {
                arrayToString(cls.componentType, obj)
            } else obj.toString()
        }

        private fun printableToString(string: String): String {
            val length = string.length
            val builder = StringBuilder(length)
            var i = 0
            while (i < length) {
                val codePoint = string.codePointAt(i)
                when ((Character.getType(codePoint)).toByte()) {
                    Character.CONTROL,
                    Character.FORMAT,
                    Character.PRIVATE_USE,
                    Character.SURROGATE,
                    Character.UNASSIGNED ->
                        when (codePoint.toChar()) {
                            '\n' -> builder.append("\\n")
                            '\r' -> builder.append("\\r")
                            '\t' -> builder.append("\\t")
                            //'\f' -> builder.append("\\f")
                            '\b' -> builder.append("\\b")
                            else -> builder.append("\\u").append(
                                String.format("%04x", codePoint).uppercase(Locale.US)
                            )
                        }
                    else -> builder.append(Character.toChars(codePoint))
                }
                i += Character.charCount(codePoint)
            }
            return builder.toString()
        }

        private fun arrayToString(cls: Class<*>, obj: Any): String {
            if (Byte::class.javaPrimitiveType == cls) {
                return byteArrayToString(obj as ByteArray)
            }
            if (Short::class.javaPrimitiveType == cls) {
                return (obj as ShortArray).contentToString()
            }
            if (Char::class.javaPrimitiveType == cls) {
                return (obj as CharArray).contentToString()
            }
            if (Int::class.javaPrimitiveType == cls) {
                return (obj as IntArray).contentToString()
            }
            if (Long::class.javaPrimitiveType == cls) {
                return (obj as LongArray).contentToString()
            }
            if (Float::class.javaPrimitiveType == cls) {
                return (obj as FloatArray).contentToString()
            }
            if (Double::class.javaPrimitiveType == cls) {
                return (obj as DoubleArray).contentToString()
            }
            if (Boolean::class.javaPrimitiveType == cls) {
                return (obj as BooleanArray).contentToString()
            }

            if (cls.isArray) {
                return arrayToString(obj as Array<Any>)
            }
            return obj.toString()
        }

        /** A more human-friendly version of Arrays#toString(byte[]) that uses hex representation.  */
        private fun byteArrayToString(bytes: ByteArray): String {
            val builder = StringBuilder("[")
            for (i in bytes.indices) {
                if (i > 0) {
                    builder.append(", ")
                }
                builder.append(byteToString(bytes[i]))
            }
            return builder.append(']').toString()
        }

        private fun byteToString(b: Byte?): String {
            return if (b == null) {
                "null"
            } else "0x" + String.format("%02x", b).uppercase(Locale.US)
        }

        private fun arrayToString(array: Array<Any>): String {
            val buf = StringBuilder()
            arrayToString(array, buf, HashSet())
            return buf.toString()
        }

        private fun arrayToString(
            array: Array<Any>?,
            builder: StringBuilder,
            seen: MutableSet<Array<Any>>
        ) {
            if (array == null) {
                builder.append("null")
                return
            }
            seen.add(array)
            builder.append('[')
            for (i in array.indices) {
                if (i > 0) {
                    builder.append(", ")
                }
                val element = array[i]
                val elementClass: Class<*> = element.javaClass
                if (elementClass.isArray && elementClass.componentType == Any::class.java) {
                    try {
                        val arrayElement = element as Array<Any>
                        if (seen.contains(arrayElement)) {
                            builder.append("[...]")
                        } else {
                            arrayToString(arrayElement, builder, seen)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    builder.append(toString(element))
                }
            }
            builder.append(']')
            seen.remove(array)
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}