package wtf.s1.willfix.bytex

import com.ss.android.ugc.bytex.common.BaseExtension

open class WillFixByteXExtension : BaseExtension() {
    override fun getName(): String {
        return "willFixByteX"
    }

    var methodList: List<String>? = null
    var separator = "#"
    var exceptionHandler: String? = null

    fun methodList(methodList: List<String>?) {
        this.methodList = methodList
    }

    fun separator(separator: String) {
        this.separator = separator
    }

    fun exceptionHandler(catchHandler: String?) {
        exceptionHandler = catchHandler
    }
}