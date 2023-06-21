package com.example.springboot3_2vue3.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description: 在alarm字段基础上添加equipmen 的name字段
 * @DateTime: 2023/6/18 11:40
 **/
public class AlarmResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date date;
    private Long equipmentid;
    private boolean read;
    private int level;
    private String name;

    @Override
    public String toString() {
        return "alarmResp{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", equipmentid=" + equipmentid +
                ", read=" + read +
                ", level=" + level +
                ", name='" + name + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
