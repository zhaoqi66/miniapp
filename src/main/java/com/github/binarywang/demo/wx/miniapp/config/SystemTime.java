package com.github.binarywang.demo.wx.miniapp.config;

import java.util.Date;

/**
 * SystemTime
 *
 * @author juan
 * @date 2018/8/2 10:41
 */

public class SystemTime {

    private static final TimeSource defaultSrc = new TimeSource() {

        @Override
        public long millis() {
            return System.currentTimeMillis();
        }
    };

    private static TimeSource source = null;

    public static long asMills() {
        return getTimeSource().millis();
    }

    public static Date asDate() {
        return new Date(asMills());
    }

    public static void reset() {
        setTimeSource(null);
    }

    public static TimeSource getTimeSource() {
        if (source != null) {
            return source;
        } else {
            return defaultSrc;
        }
    }

    public static void setTimeSource(TimeSource source) {
        SystemTime.source = source;
    }

    public interface TimeSource {
        long millis();
    }

}
