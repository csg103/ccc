package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 车型系统
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@TableName("ccc_auto_model_system")
public class AutoModelSystem extends Model<AutoModelSystem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 车型ID
     */
    @TableField("MODEL_ID")
    private Integer modelId;
    /**
     * 系统ID
     */
    @TableField("SYSTEM_ID")
    private Integer systemId;
    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;
    /**
     * 备注
     */
    @TableField("TIPS")
    private String tips;
    /**
     * 创建日期
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AutoModelSystem{" +
        "id=" + id +
        ", modelId=" + modelId +
        ", systemId=" + systemId +
        ", status=" + status +
        ", tips=" + tips +
        ", createTime=" + createTime +
        "}";
    }
}
