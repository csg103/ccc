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
 * 参数
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@TableName("ccc_auto_para")
public class AutoPara extends Model<AutoPara> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 参数类型id
     */
    @TableField("PARA_TYPE_ID")
    private Integer paraTypeId;
    /**
     * 参数名称
     */
    @TableField("PARA_NAME")
    private String paraName;
    /**
     * 参数编码
     */
    @TableField("PARA_CODE")
    private String paraCode;
    /**
     * 零部件id
     */
    @TableField("CAR_PARTS_ID")
    private Integer carPartsId;
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
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParaTypeId() {
        return paraTypeId;
    }

    public void setParaTypeId(Integer paraTypeId) {
        this.paraTypeId = paraTypeId;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode;
    }

    public Integer getCarPartsId() {
        return carPartsId;
    }

    public void setCarPartsId(Integer carPartsId) {
        this.carPartsId = carPartsId;
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
        return "AutoPara{" +
        "id=" + id +
        ", paraTypeId=" + paraTypeId +
        ", paraName=" + paraName +
        ", paraCode=" + paraCode +
        ", carPartsId=" + carPartsId +
        ", status=" + status +
        ", tips=" + tips +
        ", createTime=" + createTime +
        "}";
    }
}
