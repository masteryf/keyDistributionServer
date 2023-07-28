package com.example.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.constpool.SystemConstPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Genius
 * @date 2023/07/28 13:59
 **/

@Data
@Component
@TableName(SystemConstPool.MYSQL_TABLE_USERS)
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer UserId;
    private String MacAddress;


}
