package com.example.springboot3_2vue3.domain.scheduled;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.sql.Date;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/10 16:32
 **/
public class Dailyequipmentpoweruse {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long equipmentid;
    private Date date;
    private float poweruse;

    @Override
    public String toString() {
        return "Dailyequipmentpoweruse{" +
                "id=" + id +
                ", equipmentid=" + equipmentid +
                ", date=" + date +
                ", poweruse=" + poweruse +
                '}';
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPoweruse() {
        return poweruse;
    }

    public void setPoweruse(float poweruse) {
        this.poweruse = poweruse;
    }
}
