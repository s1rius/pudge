package wtf.s1.willfix;

public class TestSetGet {

    public String title = "title";
    public String desc = "desc";

    public int getDesc() {
        if (desc.equals("desc")) {
            title = "asdlfkafk";
            return 1;
        } else {
            title = "asdlfkafkqwert";
            return 0;
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