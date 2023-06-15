package com.example.springboot3_2vue3.resp;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 9:10
 **/
public class TemperatureResp {
    private int id;

    private double temperatures;
    private boolean level;

    private boolean classes;

    private Long equipid;
    private String location;
    private String name;

    @Override
    public String toString() {
        return "TemperatureResp{" +
                "id=" + id +
                ", temperatures=" + temperatures +
                ", level=" + level +
                ", classes=" + classes +
                ", equipid=" + equipid +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                '}';
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

    public Long getEquipid() {
        return equipid;
    }

    public void setEquipid(Long equipid) {
        this.equipid = equipid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
