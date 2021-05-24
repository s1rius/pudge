package wtf.s1.pudge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import wtf.s1.pudge.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = Test()
        a.setError(0)
        a.testStick(false, 4, null, intArrayOf(1,9,9,7,0,7,0,1))
        Log.i("willfix", "${a.msgSize}")
    }
}