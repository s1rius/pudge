package wtf.s1.willfix

import wtf.s1.willfix.logging.LevelLog

open class WillFixExtension {
    /**
     * 是否开启该插件
     */
    @JvmField
    var isEnable = true
    @JvmField
    var isEnableInDebug = false
    @JvmField
    var isNeedVerify = true

    /**
     * 需要用TryCatchBlock包住的方法
     */
    var methodList: List<String>? = null

    @JvmField
    var separator = "#"

    var exceptionHandler: String? = null

    var logLevel = LevelLog.Level.INFO
        private set

    fun enable(enable: Boolean) {
        isEnable = enable
    }

    fun logLevel(level: String?) {
        logLevel = LevelLog.Level.valueOf(level!!)
    }

    fun enableInDebug(enableInDebug: Boolean) {
        isEnableInDebug = enableInDebug
    }

    fun needVerify(needVerify: Boolean) {
        isNeedVerify = needVerify
    }

    fun exceptionHandler(catchHandler: String?) {
        exceptionHandler = catchHandler
    }
}