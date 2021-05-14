package wtf.s1.willfix.logging

import wtf.s1.willfix.core.ILogger

class LevelLog @JvmOverloads constructor(logger: ILogger = DEFAULT) : ILogger {

    var impl: ILogger = logger
    private var level = Level.INFO

    fun setLevel(l: Level) {
        level = l
    }

    override fun setTag(tag: String?) {
        impl.setTag(tag)
    }

    override fun d(msg: String?) {
        if (level <= Level.DEBUG) {
            impl.d(msg)
        }
    }

    override fun d(tag: String?, msg: String?) {
        if (level <= Level.DEBUG) {
            impl.d(tag, msg)
        }
    }

    override fun i(msg: String?) {
        if (level <= Level.INFO) {
            impl.i(msg)
        }
    }

    override fun i(tag: String?, msg: String?) {
        if (level <= Level.INFO) {
            impl.i(tag, msg)
        }
    }

    override fun w(msg: String?) {
        if (level <= Level.WARN) {
            impl.w(msg)
        }
    }

    override fun w(tag: String?, msg: String?) {
        if (level <= Level.WARN) {
            impl.w(tag, msg)
        }
    }

    override fun w(msg: String?, t: Throwable?) {
        if (level <= Level.WARN) {
            impl.w(msg, t)
        }
    }

    override fun w(tag: String?, msg: String?, t: Throwable?) {
        if (level <= Level.WARN) {
            impl.w(tag, msg, t)
        }
    }

    override fun e(msg: String?) {
        if (level <= Level.ERROR) {
            impl.e(msg)
        }
    }

    override fun e(tag: String?, msg: String?) {
        if (level <= Level.ERROR) {
            impl.e(tag, msg)
        }
    }

    override fun e(msg: String?, t: Throwable?) {
        if (level <= Level.ERROR) {
            impl.e(msg, t)
        }
    }

    override fun e(tag: String?, msg: String?, t: Throwable?) {
        if (level <= Level.ERROR) {
            impl.e(tag, msg, t)
        }
    }

    enum class Level(var value: String) {
        DEBUG("DEBUG"), INFO("INFO"), WARN("WARN"), ERROR("ERROR");
    }

    companion object {
        var DEFAULT: ILogger = SystemOutputImpl()
    }

}