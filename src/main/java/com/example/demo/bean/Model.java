package com.example.demo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.constpool.SystemConstPool;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Genius
 * @date 2023/07/28 14:00
 **/
@Data
@Component
@TableName(SystemConstPool.MYSQL_TABLE_Models)
@NoArgsConstructor
public class Model {
    private String ModelId;
    private String ModelName;
    private String ModelKey;
}
