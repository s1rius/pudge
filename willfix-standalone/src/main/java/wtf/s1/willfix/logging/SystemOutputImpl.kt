package wtf.s1.willfix.logging

import org.gradle.api.logging.LogLevel

class SystemOutputImpl : BaseLogger() {
    override fun write(level: LogLevel?, prefix: String?, msg: String?, t: Throwable?) {
        var ps = System.out
        if (level == LogLevel.WARN || level == LogLevel.ERROR) {
            ps = System.err
        }
        ps.println(String.format("%s [%-10s] %s", level!!.name, prefix, msg))
        if (t != null) {
            ps.println(level.name + " " + stackToString(t))
        }
    }
}