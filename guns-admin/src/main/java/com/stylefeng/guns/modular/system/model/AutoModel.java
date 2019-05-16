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
 * 车型
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@TableName("ccc_auto_model")
public class AutoModel extends Model<AutoModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 车型名称
     */
    @TableField("MODEL_NAME")
    private String modelName;
    /**
     * 车型编码
     */
    @TableField("MODEL_CODE")
    private String modelCode;
    /**
     * 车型状态
     */
    @TableField("STATUS")
    private String status;
    /**
     * 备注
     */
    @TableField("TIPS")
    private String tips;
    /**
     * 穿件日期
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
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
        return "AutoModel{" +
        "id=" + id +
        ", modelName=" + modelName +
        ", modelCode=" + modelCode +
        ", status=" + status +
        ", tips=" + tips +
        ", createTime=" + createTime +
        "}";
    }
}
