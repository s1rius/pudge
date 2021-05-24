package wtf.s1.stick.bytex

import com.ss.android.ugc.bytex.common.BaseExtension

open class StickByteXExtension : BaseExtension() {

    var annotationEnable: Boolean = false

    override fun getName(): String {
        return "stickByteX"
    }

    fun enableAnnotation(enable: Boolean) {
        this.annotationEnable = enable
    }
}