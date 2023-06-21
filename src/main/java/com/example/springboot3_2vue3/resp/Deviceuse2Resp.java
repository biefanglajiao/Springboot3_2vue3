package com.example.springboot3_2vue3.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description:  返回String格式的endate时间数据  并添加name属性
 * @DateTime: 2023/6/16 8:21
 **/
//设备开启时间表
@Component
public class Deviceuse2Resp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )//这个是返回给前端的格式
    private Date begindate;  //开启时间  写这个记录的时候自动添加

    private String enddate;   //关闭时间  后台传
    private float powerconsumption;//每次开启关闭的耗电量
    private Long equipmentid;
    private  String name;

    @Override
    public String toString() {
        return "Deviceuse2Resp{" +
                "id=" + id +
                ", begindate=" + begindate +
                ", enddate='" + enddate + '\'' +
                ", powerconsumption=" + powerconsumption +
                ", equipmentid=" + equipmentid +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public float getPowerconsumption() {
        return powerconsumption;
    }

    public void setPowerconsumption(float powerconsumption) {
        this.powerconsumption = powerconsumption;
    }
}
