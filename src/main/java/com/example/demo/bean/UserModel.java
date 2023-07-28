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
 * @date 2023/07/28 14:01
 **/
@Data
@Component
@TableName(SystemConstPool.MYSQL_TABLE_USER_MODEL_MAPPING)
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @TableId(type = IdType.AUTO)
    private Integer MappingId;
    private String UserId;
    private String ModelId;
}
