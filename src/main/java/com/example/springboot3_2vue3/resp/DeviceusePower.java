package com.example.springboot3_2vue3.resp;

/**
 * @Author: 常兆海
 * @Description: 接收所有开着的设备信息  包含功率
 * @DateTime: 2023/6/16 9:58
 **/
public class DeviceusePower {

    private Long id;
    private Long date;
    private Long equipmentid;
    private float power;

    public void setPower(float power) {

        this.power = power;
    }

    public float getPower() {
        return power;
    }

    @Override
    public String toString() {
        return "DeviceusePower{" +
                "id=" + id +
                ", date=" + date +
                ", equipmentid=" + equipmentid +
                ", power='" + power + '\'' +
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
