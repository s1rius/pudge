package wtf.s1.willfix;

public class Test {

    public String msg;
    public int error;

    public int getMsgSize() {
        return msg.length();
    }

    public void setError(int err) {
        error = 100 / err;
    }
}