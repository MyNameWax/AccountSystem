package cn.rzpt.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName account
 */
@Data
public class AccountDTO implements Serializable {


    /**
     * 验证码
     */
    private String code;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String userPassword;




}