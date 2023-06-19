package com.example.springboot3_2vue3.domain.equipment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/16 8:21
 **/
//设备开启时间表
@Component
public class Deviceuse2 {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")//这个是返回给前端的格式
    private Date begindate;  //开启时间  写这个记录的时候自动添加
    private Long enddate;   //关闭时间  后台传
    private float powerconsumption;//每次开启关闭的耗电量
    private Long equipmentid;
    @TableField(exist = false)
    private Equipment equipment;

    @Override
    public String toString() {
        return "Deviceuse2{" +
                "id=" + id +
                ", begindate=" + begindate +
                ", enddate=" + enddate +
                ", powerconsumption=" + powerconsumption +
                ", equipmentid=" + equipmentid +
                ", equipment=" + equipment +
                '}';
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Long getEnddate() {
        return enddate;
    }

    public void setEnddate(Long enddate) {
        this.enddate = enddate;
    }

    public float getPowerconsumption() {
        return powerconsumption;
    }

    public void setPowerconsumption(float powerconsumption) {
        this.powerconsumption = powerconsumption;
    }
}
