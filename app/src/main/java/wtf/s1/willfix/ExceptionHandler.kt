package wtf.s1.willfix;

import android.util.Log;

public class ExceptionHandler {

    public static void log(Exception e) {
        Log.i("exception found", "new one");
        e.printStackTrace();
    }
}
