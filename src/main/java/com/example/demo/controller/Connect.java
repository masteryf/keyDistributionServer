package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.bean.Model;
import com.example.demo.bean.Serial;
import com.example.demo.bean.User;
import com.example.demo.bean.UserModel;
import com.example.demo.constpool.SystemConstPool;
import com.example.demo.mapper.ModelMapper;
import com.example.demo.mapper.SerialMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserModelMapper;
import com.example.demo.util.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>TODO</p>
 *
 * @author welsir
 * @date 2023/7/25 21:18
 */
@RestController
public class Connect {

    @Resource
    ModelMapper modelMapper;

    @Resource
    SerialMapper serialMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserModelMapper userModelMapper;

    @PostMapping("register")
    public void register(String jsonStr,String MAC) throws SQLException {
        SystemConstPool.LOGGER.info("获取 jsonStr-MAC:{}-{}",jsonStr,MAC);
        //数据库查询
        synchronized (jsonStr){
            List<Serial> serials = serialMapper.selectByMap(
                    Map.of("SerialNumber", jsonStr)
            );
            if(serials.size()>0){
                    Serial serial = serials.get(0);
                    String serialNumber = serial.getSerialNumber();
                    String modelId = serial.getModelId(); //这里直接获取第一个序列number
                    SystemConstPool.LOGGER.info("查询到相同<序列号>-<modelId>:<{}>-<{}>",serialNumber,modelId);
                    //获得用户mac地址，插入数据库
                    User user = new User();
                    user.setMacAddress(MAC);
                    if(userMapper.insert(user)==1){
                        SystemConstPool.LOGGER.info("成功添加用户mac:{}地址",MAC);
                        //更新用户和模型映射关系
                        if(modelId!=null){
                            if (userModelMapper.insert(new UserModel(0,MAC,modelId))==1) {
                                String code = Generator.generateMicrosoftRegistrationCode();
                                SystemConstPool.LOGGER.info("生成序列号:{}",code);
                                serial.setSerialNumber(code);
                                serialMapper.update(serial,new UpdateWrapper<>());
                            }
                        }
                    }
                }
        }
    }

    @GetMapping("getKey")
    public String getMAC(String mac,String model) throws SQLException {
        try {
            mac = AESDecryption.decrypt(mac, TimeStringGenerator.generateTimeString());
            SystemConstPool.LOGGER.info("当前时间：{}",TimeStringGenerator.generateTimeString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SystemConstPool.LOGGER.info("Mac-ModelId:{}-{}",mac,model);
        List<UserModel> userModels = userModelMapper.selectByMap(
                Map.of("UserId", mac, "modelId", model)
        );
        if(userModels.size()>0){
            UserModel userModel = userModels.get(0);
            if(userModel.getUserId().equals(mac)){
                List<Model> models = modelMapper.selectByMap(Map.of(
                        "ModelId", model
                ));
                if(models.size()>0){
                    Model modelObj = models.get(0);
                    try {
                        return AESEncryption.encrypt(modelObj.getModelKey(),TimeStringGenerator.generateTimeString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }
        return "error!";
    }
}
