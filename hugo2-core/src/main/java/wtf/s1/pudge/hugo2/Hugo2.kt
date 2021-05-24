package wtf.s1.pudge.hugo2

class Hugo2 {
    companion object {

        const val CLAZZ = "wtf/s1/pudge/hugo2/Hugo2"
        const val DEBUGLOGDESC = "Lwtf/s1/pudge/hugo2/DebugLog;"

        private var hugoImpl: Hugo2Logger? = object: Hugo2Logger {}

        @JvmStatic
        fun logI(clazz: String, method: String, params: String) {
            hugoImpl?.logI(clazz, method, params)
        }

        @JvmStatic
        fun logO(clazz: String, method: String) {
            hugoImpl?.logO(clazz, method)
        }

        @JvmStatic
        fun setLogger(logger: Hugo2Logger) {
            hugoImpl = logger
        }
    }


    interface Hugo2Logger {
        fun logI(clazz: String, method: String, params: String) {}
        fun logO(clazz: String, method: String) {}
    }
}