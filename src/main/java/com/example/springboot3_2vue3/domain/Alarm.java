package com.example.springboot3_2vue3.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/18 11:33
 **/
public class Alarm {
    @TableId(type= IdType.INPUT)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date date;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long equipmentid;
    private boolean read;
    private int level;

    private Equipment equipment;

    @Override
    public String toString() {
        return "alarm{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", equipmentid=" + equipmentid +
                ", read=" + read +
                ", level=" + level +
                ", equipment=" + equipment +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
