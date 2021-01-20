package wtf.s1.willfix.bytex;

import com.ss.android.ugc.bytex.common.BaseExtension;

import java.util.List;

public class WillFixByteXExtension extends BaseExtension {
    @Override
    public String getName() {
        return "willFixByteX";
    }

    public List<String> methodList;

    public String separator = "#";

    public String exceptionHandler;

    public List<String> getMethodList() {
        return methodList;
    }

    public void methodList(List<String> methodList) {
        this.methodList = methodList;
    }

    public String getSeparator() {
        return separator;
    }

    public void separator(String separator) {
        this.separator = separator;
    }

    public String getExceptionHandler() {
        return exceptionHandler;
    }

    public void exceptionHandler(String catchHandler) {
        this.exceptionHandler = catchHandler;
    }
}
