package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStringGenerator {

    public static String generateTimeString() {
        // 获取当前系统时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

        // 确保字符串长度为16字节
        String timeString = sdf.format(now);
        int paddingLength = 16 - timeString.length();
        for (int i = 0; i < paddingLength; i++) {
            timeString += "0";
        }

        return timeString;
    }
    public static void main(String[] args) {
        String result = generateTimeString();
        System.out.println(result);
    }
}