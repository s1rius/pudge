package wtf.s1.pudge

import android.util.Log

object ExceptionHandler {

    @JvmStatic
    fun log(e: Exception) {
        Log.i("exception found", "new one")
        e.printStackTrace()
    }
}