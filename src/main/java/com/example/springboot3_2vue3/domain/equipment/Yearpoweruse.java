package com.example.springboot3_2vue3.domain.equipment;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/17 10:55
 **/
@Component
public class Yearpoweruse {
    private Date date;
    private int power;

    @Override
    public String toString() {
        return "Yearpoweruse{" +
                "date=" + date +
                ", power=" + power +
                '}';
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}