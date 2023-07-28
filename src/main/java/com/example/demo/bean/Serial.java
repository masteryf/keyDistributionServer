package com.example.demo.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.constpool.SystemConstPool;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Genius
 * @date 2023/07/28 13:51
 **/
@Data
@Component
@TableName(SystemConstPool.MYSQL_TABLE_SERIAL_NUMBERS)
@NoArgsConstructor
public class Serial {
    private Integer SerialId;

    private String SerialNumber;

    private String ModelId;
}
