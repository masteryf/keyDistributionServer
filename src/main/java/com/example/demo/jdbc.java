package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <p>TODO</p>
 *
 * @author welsir
 * @date 2023/7/25 22:10
 */
public class jdbc {

    public static Connection getConnect() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //里面内容依次是："jdbc:mysql://主机名:端口号/数据库名","用户名","登录密码
            connection = DriverManager.getConnection("jdbc:mysql://62.234.54.176:3306/rsamodel", "yf", "Nvidiafucky0u!");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
