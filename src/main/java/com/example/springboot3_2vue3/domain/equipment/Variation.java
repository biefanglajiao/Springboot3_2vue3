package com.example.springboot3_2vue3.domain.equipment;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;//记录时间
    private String data;//耗电量
    private Long equipmentid;//对用设备id

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Variation{" +
                "id=" + id +
                ", date=" + date +
                ", data='" + data + '\'' +
                ", equipmentid=" + equipmentid +
                '}';
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
