package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>TODO</p>
 *
 * @author welsir
 * @date 2023/7/25 21:18
 */
@RestController
public class Connect {

    @PostMapping("register")
    public void register(String jsonStr,String MAC) throws SQLException {
        boolean flag = false;
        String modelId = null;
        System.out.println(jsonStr);
        System.out.println(MAC);
        //获取数据库链接
        Connection connect = jdbc.getConnect();
        //sql语句
        String sql = "SELECT * FROM SerialNumbers WHERE SerialNumber = ?";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setString(1,jsonStr);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            //查找序列号和模型id
            flag = jsonStr.equals(resultSet.getString("SerialNumber"));
            modelId = resultSet.getString("ModelID");
            System.out.println("是否查询到相同序列号:"+flag+"    modeID:"+modelId);
        }
        if(flag){
            //插入mac到数据库
            String sql2 = "insert into users(MacAddress) value(?)";
            connect.prepareStatement(sql2);
            PreparedStatement statement2 = connect.prepareStatement(sql2);
            statement2.setString(1,MAC);
            int i = statement2.executeUpdate();
            System.out.println(i);
            //映射用户和模型关系表
            if(modelId!=null){
                String sql4 = "insert into usermodelmapping(UserID,modelId) value(?,?)";
                PreparedStatement statement4 = connect.prepareStatement(sql4);
                statement4.setString(1,MAC);
                statement4.setString(2,modelId);
                statement4.executeUpdate();
                statement4.close();
                //刷新序列号
                String code = Generator.generateMicrosoftRegistrationCode();
                System.out.println("生成新的序列号为:"+code);
                String sql1 = "update SerialNumbers set SerialNumber = ? where SerialNumber = ?";
                PreparedStatement statement1 = connect.prepareStatement(sql1);
                statement1.setString(1,code);
                statement1.setString(2,jsonStr);
                statement1.executeUpdate();
                statement1.close();
            }
            statement2.close();
        }
        //关闭数据库链接
        connect.close();
    }

    @GetMapping("getKey")
    public String getMAC(String mac,String modelID) throws SQLException {
        try {
            mac = AESDecryption.decrypt(mac, TimeStringGenerator.generateTimeString());
            System.out.println(TimeStringGenerator.generateTimeString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(mac+"   "+modelID);
        Connection connect = jdbc.getConnect();
        String sql = "select * from usermodelmapping where UserID = ? and modelID = ?";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setString(1,mac);
        statement.setString(2,modelID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            if(mac.equals(resultSet.getString("UserID"))){
                String sql1 = "select * from models where ModelID = ?";
                PreparedStatement statement1 = connect.prepareStatement(sql1);
                statement1.setString(1,modelID);
                ResultSet query = statement1.executeQuery();
                while(query.next()){
                    try {
                        return AESEncryption.encrypt(query.getString("ModelKey"), TimeStringGenerator.generateTimeString());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return "error!";
    }
}
