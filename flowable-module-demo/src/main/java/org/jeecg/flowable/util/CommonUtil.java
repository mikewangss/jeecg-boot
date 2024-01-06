package org.jeecg.flowable.util;

import org.springframework.stereotype.Component;

public class CommonUtil {
    public static String parseDuration(long durationMillis) {
        long seconds = durationMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String durationString = String.format("%d天 %02d时 %02d分 %02d秒", days, hours % 24, minutes % 60, seconds % 60);
        System.out.println("Execution Time: " + durationString);
        return durationString;
    }
}
