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
 * 车型参数
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@TableName("ccc_auto_model_para")
public class AutoModelPara extends Model<AutoModelPara> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 车型ID
     */
    @TableField("MODEL_ID")
    private Integer modelId;
    /**
     * 参数ID
     */
    @TableField("PARA_ID")
    private Integer paraId;

    public String getParaUrl() {
        return paraUrl;
    }

    public void setParaUrl(String paraUrl) {
        this.paraUrl = paraUrl;
    }

    /**
     * 参数附件
     */
    @TableField("PARA_URL")
    private String paraUrl;
    /**
     * 参数值
     */
    @TableField("PARA_VALUE")
    private String paraValue;
    /**
     * 备注
     */
    @TableField("TIPS")
    private String tips;
    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;
    /**
     * 创建日期
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 版本
     */
    @TableField("VERSION")
    private String version;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifyer() {
        return modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer = modifyer;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode;
    }

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 修改人人
     */
    @TableField("MODIFYER")
    private String modifyer;

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public int getParaTypeId() {
        return paraTypeId;
    }

    public void setParaTypeId(int paraTypeId) {
        this.paraTypeId = paraTypeId;
    }

    @TableField(exist = false)
    private String paraName;
    @TableField(exist = false)
    private String systemName;
    @TableField(exist = false)
    private String paraCode;
    @TableField(exist = false)
    private int paraTypeId;


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

    public Integer getParaId() {
        return paraId;
    }

    public void setParaId(Integer paraId) {
        this.paraId = paraId;
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AutoModelPara{" +
        "id=" + id +
        ", modelId=" + modelId +
        ", paraId=" + paraId +
        ", paraValue=" + paraValue +
        ", tips=" + tips +
        ", status=" + status +
        ", createTime=" + createTime +
        ", version=" + version +
        "}";
    }
}
