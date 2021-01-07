package wtf.s1.willfix.bytex

import wtf.s1.willfix.core.ILogger

class ByteXLogWrapper(private val l: com.ss.android.ugc.bytex.common.log.ILogger): ILogger {

    override fun setTag(tag: String?) {
        l.setTag(tag)
    }

    override fun d(msg: String?) {
        l.d(msg)
    }

    override fun d(tag: String?, msg: String?) {
        l.d(tag, msg)
    }

    override fun i(msg: String?) {
        l.i(msg)
    }

    override fun i(tag: String?, msg: String?) {
        l.i(tag, msg)
    }

    override fun w(msg: String?) {
        l.w(msg)
    }

    override fun w(tag: String?, msg: String?) {
        l.w(tag, msg)
    }

    override fun w(msg: String?, t: Throwable?) {
        l.w(msg, t)
    }

    override fun w(tag: String?, msg: String?, t: Throwable?) {
        l.w(tag, msg, t)
    }

    override fun e(msg: String?) {
        l.e(msg)
    }

    override fun e(tag: String?, msg: String?) {
        l.e(tag, msg)
    }

    override fun e(msg: String?, t: Throwable?) {
        l.e(msg, t)
    }

    override fun e(tag: String?, msg: String?, t: Throwable?) {
        l.e(tag, msg, t)
    }
}