package com.example.springboot3_2vue3.domain.equipment;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/16 8:21
 **/
//设备开启时间表
@Component
public class Deviceuse {
    private Long id;
    private Long    date;
    private Long equipmentid;

    @Override
    public String toString() {
        return "Deviceuse{" +
                "id=" + id +
                ", date=" + date +
                ", equipmentid=" + equipmentid +
                '}';
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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
}
