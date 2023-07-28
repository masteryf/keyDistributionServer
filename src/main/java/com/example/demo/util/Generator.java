package com.example.demo.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>TODO</p>
 *
 * @author welsir
 * @date 2023/7/26 14:24
 */
public class Generator {
    // 定义生成随机序列号所使用的字符集
    private static final String CHARACTERS = "BCDFGHJKMPQRTVWXY2346789";

    // 随机数生成器
    private static final Random random = new SecureRandom();

    // 生成微软注册序列号
    public static String generateMicrosoftRegistrationCode() {
        int codeLength = 25;
        StringBuilder code = new StringBuilder(codeLength);

        // 生成序列号的前24位
        for (int i = 0; i < codeLength - 1; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
            // 添加分隔符 '-'，每5个字符分隔一次
            if ((i + 1) % 5 == 0) {
                code.append("-");
            }
        }

        // 生成校验位
        char checksum = calculateChecksum(code.toString());
        code.append(checksum);

        return code.toString();
    }

    // 计算校验位
    private static char calculateChecksum(String code) {
        int sum = 0;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            // 将字符的ASCII码值相加
            sum += c;
        }
        // 取最后八位的二进制数作为校验位
        int checksumValue = sum & 0xFF;
        return intToBase32Char(checksumValue);
    }

    // 将0-31的整数转换为Base32字符
    private static char intToBase32Char(int value) {
        return CHARACTERS.charAt(value % CHARACTERS.length());
    }

    public static void main(String[] args) {
        String registrationCode = generateMicrosoftRegistrationCode();
        System.out.println("生成的仿微软注册序列号: " + registrationCode);
    }
}
