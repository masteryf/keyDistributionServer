package com.example.demo.util;

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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rsamodel", "", "");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
