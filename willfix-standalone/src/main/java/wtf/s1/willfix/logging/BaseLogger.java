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

package wtf.s1.willfix.logging;

import org.gradle.api.logging.LogLevel;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import wtf.s1.willfix.core.ILogger;


public abstract class BaseLogger implements ILogger, Closeable {
    private static final String DEFAULT_TAG = "WillFix";
    private String tag = DEFAULT_TAG;

    @Override
    public void setTag(String tag) {
        this.tag = tag == null || "".equals(tag) ? DEFAULT_TAG : tag;
    }

    @Override
    public void d(String msg) {
        d(this.tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        write(LogLevel.DEBUG, tag, msg, null);
    }

    @Override
    public void i(String msg) {
        i(this.tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        write(LogLevel.INFO, tag, msg, null);
    }

    @Override
    public void w(String msg) {
        w(this.tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        w(tag, msg, null);
    }

    @Override
    public void w(String msg, Throwable t) {
        w(this.tag, msg, t);
    }

    @Override
    public void w(String tag, String msg, Throwable t) {
        write(LogLevel.WARN, tag, msg, t);
    }

    @Override
    public void e(String msg) {
        e(this.tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        e(tag, msg, null);
    }

    @Override
    public void e(String msg, Throwable t) {
        e(tag, msg, t);
    }

    @Override
    public void e(String tag, String msg, Throwable t) {
        write(LogLevel.ERROR, tag, msg, t);
    }

    protected abstract void write(LogLevel level, String prefix, String msg, Throwable t);

    static String stackToString(Throwable t) {
        StringWriter sw = new StringWriter(128);
        PrintWriter ps = new PrintWriter(sw);
        t.printStackTrace(ps);
        ps.flush();
        return sw.toString();
    }

    @Override
    public void close() throws IOException {

    }
}
