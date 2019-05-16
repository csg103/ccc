package com.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 零部件
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@TableName("ccc_auto_parts")
public class AutoParts extends Model<AutoParts> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 零部件名称
     */
    @TableField("PARTS_NAME")
    private String partsName;
    /**
     * 零部件编码
     */
    @TableField("PARTS_CODE")
    private String partsCode;
    /**
     * 车辆系统ID
     */
    @TableField("CAR_SYSTEM_ID")
    private Integer carSystemId;
    /**
     * 父ID
     */
    @TableField("PID")
    private Integer pid;
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

    @TableField(exist = false)
    private String parentPartsName;

    public String getParentPartsName() {
        return parentPartsName;
    }

    public void setParentPartsName(String parentPartsName) {
        this.parentPartsName = parentPartsName;
    }

    public String getCarSystemName() {
        return carSystemName;
    }

    public void setCarSystemName(String carSystemName) {
        this.carSystemName = carSystemName;
    }

    @TableField(exist = false)
    private String carSystemName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public String getPartsCode() {
        return partsCode;
    }

    public void setPartsCode(String partsCode) {
        this.partsCode = partsCode;
    }

    public Integer getCarSystemId() {
        return carSystemId;
    }

    public void setCarSystemId(Integer carSystemId) {
        this.carSystemId = carSystemId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
        return "AutoParts{" +
        "id=" + id +
        ", partsName=" + partsName +
        ", partsCode=" + partsCode +
        ", carSystemId=" + carSystemId +
        ", pid=" + pid +
        ", status=" + status +
        ", tips=" + tips +
        ", createTime=" + createTime +
        "}";
    }
}
