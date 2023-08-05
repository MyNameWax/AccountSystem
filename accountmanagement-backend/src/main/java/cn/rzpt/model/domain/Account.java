package cn.rzpt.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName account
 */
@TableName(value ="account")
@Data
public class Account implements Serializable {
    /**
     * ID编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 是否为管理员 0:用户 1:管理员
     */
    private Integer isAdmin;

    /**
     * 用户状态 0:正常 1:禁用
     */
    private Integer status;

    /**
     * 是否删除 0:没有  1:删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}