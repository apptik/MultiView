package io.apptik.widget.multiview.common;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log {

    private static final int CALL_STACK_INDEX = 5;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
    private static final boolean DEBUG = true;
    public static void v(String msg) {
        if(DEBUG) {
            android.util.Log.v(getTag(), msg);
        }
    }

    public static void d(String msg) {
        if(DEBUG) {
            android.util.Log.d(getTag(), msg);
        }
    }

    public static void i(String msg) {
        if(DEBUG) {
            android.util.Log.i(getTag(), msg);
        }
    }

    public static void w(String msg) {
        if(DEBUG) {
            android.util.Log.w(getTag(), msg);
        }
    }

    public static void e(String msg) {
        if(DEBUG) {
            android.util.Log.e(getTag(), msg);
        }
    }

    public static void wtf(String msg) {
        if(DEBUG) {
            android.util.Log.wtf(getTag(), msg);
        }
    }

    private static String getTag() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
    }

    protected static String createStackElementTag(StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }
}
