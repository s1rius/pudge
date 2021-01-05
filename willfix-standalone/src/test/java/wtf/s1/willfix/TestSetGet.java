package wtf.s1.willfix;

public class TestSetGet {

    public String title = "title";
    public String desc = "desc";

    public int getDesc() {
        if (desc.equals("desc")) {
            return 1;
        } else {
            return 0;
        }
    }

    public float getDescc() {
        if (desc.equals("desc")) {
            return 0f;
        } else {
            return 1f;
        }
    }

    public long getDesc1() {
        if (desc.equals("desc")) {
            return 0L;
        } else {
            return 1L;
        }
    }

    public void setDesc(String desc, boolean b) {
        StringBuilder sb = new StringBuilder("sss");
        sb.append("bbb");

        title = desc;
        int j = 0;
        if (title.equals("sss")) {
            sb.append("ttttt");
            j = 1;
        }
        int s = 1/j;
        System.out.println(s);
        System.out.println(sb);
        this.desc = desc;
    }
}