package com.example.springboot3_2vue3.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description: 包含日功率曲线数据Variation表+equipment表的name字段
 * @DateTime: 2023/6/16 16:54
 **/
public class VariationNameResp {
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;//记录时间
    private float data;//耗电量
    private Long equipmentid;//对用设备id

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    private String name;

    @Override
    public String toString() {
        return "VariationNameResp{" +
                "id=" + id +
                ", date=" + date +
                ", data=" + data +
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

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public void setId(int id) {
        this.id = id;
    }
}
