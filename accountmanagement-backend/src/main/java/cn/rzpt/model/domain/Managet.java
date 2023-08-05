package cn.rzpt.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName managet
 */
@TableName(value ="managet")
@Data
public class Managet implements Serializable {
    /**
     * ID主键
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 关联ID
     */
    private Integer uid;

    /**
     * 逻辑删除 1:删除 0:没有  默认0
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}