package wtf.s1.pudge;

import wtf.s1.pudge.hugo2.DebugLog;
import wtf.s1.stick.core.Stick;

public class Test {

    public String msg;
    public int error;

    @Stick
    public int getMsgSize() {
        return msg.length();
    }

    @Stick
    public void setError(int err) {
        error = 100 / err;
    }

    @Stick
    @DebugLog
    public void testStick(boolean b, int i, String s, int[] ooo) {
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append(i);
        sb.append(s);
        if (ooo != null) {
            sb.append(ooo.length);
        }
        System.out.print(sb);
    }
}