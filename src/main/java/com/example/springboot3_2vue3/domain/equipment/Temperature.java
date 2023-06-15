package com.example.springboot3_2vue3.domain.equipment;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 11:27
 **/

public class Temperature {
    private int id;
    private double temperatures;
    private boolean level;
    private boolean classes;
    @TableField(exist = false)
    private Equipment equipment;

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", temperatures=" + temperatures +
                ", level=" + level +
                ", classes=" + classes +
                ", equipment=" + equipment +
                '}';
    }

    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public boolean isClasses() {
        return classes;
    }

    public void setClasses(boolean classes) {
        this.classes = classes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(double temperatures) {
        this.temperatures = temperatures;
    }


    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
