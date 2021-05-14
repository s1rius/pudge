/*
    Copyright 2020 byteX

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
 */
package wtf.s1.willfix.logging

import org.gradle.api.logging.LogLevel
import wtf.s1.willfix.core.ILogger
import java.io.Closeable
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter

abstract class BaseLogger : ILogger, Closeable {
    private var tag = DEFAULT_TAG
    override fun setTag(tag: String?) {
        this.tag = if (tag == null || "" == tag) DEFAULT_TAG else tag
    }

    override fun d(msg: String?) {
        d(tag, msg)
    }

    override fun d(tag: String?, msg: String?) {
        write(LogLevel.DEBUG, tag, msg, null)
    }

    override fun i(msg: String?) {
        i(tag, msg)
    }

    override fun i(tag: String?, msg: String?) {
        write(LogLevel.INFO, tag, msg, null)
    }

    override fun w(msg: String?) {
        w(tag, msg)
    }

    override fun w(tag: String?, msg: String?) {
        w(tag, msg, null)
    }

    override fun w(msg: String?, t: Throwable?) {
        w(tag, msg, t)
    }

    override fun w(tag: String?, msg: String?, t: Throwable?) {
        write(LogLevel.WARN, tag, msg, t)
    }

    override fun e(msg: String?) {
        e(tag, msg)
    }

    override fun e(tag: String?, msg: String?) {
        e(tag, msg, null)
    }

    override fun e(msg: String?, t: Throwable?) {
        e(tag, msg, t)
    }

    override fun e(tag: String?, msg: String?, t: Throwable?) {
        write(LogLevel.ERROR, tag, msg, t)
    }

    protected abstract fun write(level: LogLevel?, prefix: String?, msg: String?, t: Throwable?)

    @kotlin.jvm.Throws(IOException::class)
    override fun close() {
    }

    companion object {
        private const val DEFAULT_TAG = "WillFix"
        @JvmStatic
        fun stackToString(t: Throwable): String {
            val sw = StringWriter(128)
            val ps = PrintWriter(sw)
            t.printStackTrace(ps)
            ps.flush()
            return sw.toString()
        }
    }
}