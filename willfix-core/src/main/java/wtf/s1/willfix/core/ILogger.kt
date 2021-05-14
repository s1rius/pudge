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
package wtf.s1.willfix.core

interface ILogger {
    fun setTag(tag: String?)
    fun d(msg: String?)
    fun d(tag: String?, msg: String?)
    fun i(msg: String?)
    fun i(tag: String?, msg: String?)
    fun w(msg: String?)
    fun w(tag: String?, msg: String?)
    fun w(msg: String?, t: Throwable?)
    fun w(tag: String?, msg: String?, t: Throwable?)
    fun e(msg: String?)
    fun e(tag: String?, msg: String?)
    fun e(msg: String?, t: Throwable?)
    fun e(tag: String?, msg: String?, t: Throwable?)
}