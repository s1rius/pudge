package wtf.s1.stick.core

import java.util.concurrent.ConcurrentHashMap

class MethodTrace {

    companion object {

        // modify need sync ASM call
        const val TRACE_CLAZZ = "wtf/s1/stick/core/MethodTrace"
        const val STICK_PREFIX = "wtf/s1/stick"
        private val methodMap = ConcurrentHashMap<Int, MethodInfo>()
        private var tracer: ITracer? = null

        // modify need sync ASM call
        @JvmStatic
        fun i(methodId: Int) {
            tracer?.i(methodId)
        }

        // modify need sync ASM call
        @JvmStatic
        fun o(methodId: Int) {
            tracer?.o(methodId)
        }

        @JvmStatic
        fun setImpl(tracer: ITracer) {
            this.tracer = tracer
        }

        fun methodFind(clazz: String, name: String, desc: String): MethodInfo {
            return MethodInfo(clazz, name, desc).apply {
                methodMap[this.id] = this
            }
        }
    }

    data class Config(val onlyAnn: Boolean,
                      val blackList: List<String>? = null)
}