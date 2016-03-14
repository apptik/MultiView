/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.multiview.common;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log {

    private static final int CALL_STACK_INDEX = 5;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
    private static boolean DEBUG = false;

    public static void on() {
        DEBUG = true;
    }
    public static void off() {
        DEBUG = false;
    }
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
