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
 * 系统
 * </p>
 *
 * @author zhaokai
 * @since 2019-03-12
 */
@TableName("ccc_auto_system")
public class AutoSystem extends Model<AutoSystem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 车辆系统名称
     */
    @TableField("CAR_SYSTEM_NAME")
    private String carSystemName;
    /**
     * 车辆系统编码
     */
    @TableField("CAR_SYSTEM_CODE")
    private String carSystemCode;
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

    public String getCarSystemName() {
        return carSystemName;
    }

    public void setCarSystemName(String carSystemName) {
        this.carSystemName = carSystemName;
    }

    public String getCarSystemCode() {
        return carSystemCode;
    }

    public void setCarSystemCode(String carSystemCode) {
        this.carSystemCode = carSystemCode;
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
        return "AutoSystem{" +
        "id=" + id +
        ", carSystemName=" + carSystemName +
        ", carSystemCode=" + carSystemCode +
        ", status=" + status +
        ", tips=" + tips +
        ", createTime=" + createTime +
        "}";
    }
}
