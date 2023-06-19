package com.example.springboot3_2vue3.domain.equipment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 21:59
 **/
@Component
public class Variation {
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date date;//记录时间
    private float data;//耗电量
    private Long equipmentid;//对用设备id

    @TableField(exist = false)
    private Equipment equipment;

    @Override
    public String toString() {
        return "Variation{" +
                "id=" + id +
                ", date=" + date +
                ", data=" + data +
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
