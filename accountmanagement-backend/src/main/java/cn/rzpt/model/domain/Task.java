package cn.rzpt.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName task
 */
@TableName(value ="task")
@Data
public class Task implements Serializable {
    /**
     * ID主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 状态 0 未完成 1 完成
     */
    private Integer status;

    /**
     * 是否删除 0 未删除 1 删除
     */
    private Integer isDelete;

    /**
     * 用户ID
     */
    private Integer uid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}