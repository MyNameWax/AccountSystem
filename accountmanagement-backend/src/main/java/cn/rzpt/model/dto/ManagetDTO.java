package cn.rzpt.model.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName managet
 */
@Data
public class ManagetDTO implements Serializable {

    /**
     * ID
     */
    private Integer id;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台账号
     */
    private String account;

    /**
     * 平台密码
     */
    private String password;


}