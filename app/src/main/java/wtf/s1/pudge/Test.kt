package wtf.s1.pudge

import wtf.s1.pudge.hugo2.DebugLog

class Test {

    var msg: String? = null

    @JvmField
    var error = 0

    fun getMsgSize(): Int {
        return msg?.length?:0
    }

    fun setError(err: Int) {
        error = 100 / err
    }

    @DebugLog
    fun test(p: Boolean) {
        print(p)
    }

    @DebugLog
    fun test1(time: Byte) {
        print(time)
    }

    @DebugLog
    fun test2(time: Short) {
        print(time)
    }

    @DebugLog
    fun test3(p: Int) {
        print(p)
    }

    @DebugLog
    fun test4(p: Long) {
        print(p)
    }

    @DebugLog
    fun test5(s: String?) {
        print(s)
    }

    @DebugLog
    fun test6(o: Any?) {
        print(o)
    }

    @DebugLog
    fun test7(b: Boolean, i: Int, s: String?, ooo: IntArray?) {
        test(b)
        test3(i)
        test5(s)
        test6(ooo)
        val sb = StringBuilder()
        sb.append(b)
        sb.append(i)
        sb.append(s)
        if (ooo != null) {
            sb.append(ooo.size)
        }
        print(sb)
    }


}