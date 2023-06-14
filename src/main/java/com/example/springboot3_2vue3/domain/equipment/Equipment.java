package com.example.springboot3_2vue3.domain.equipment;

import org.springframework.stereotype.Component;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/14 11:47
 **/
@Component
public class Equipment {
    private Long id;
    private String name;
    private String power;
    private String location;
    private boolean state;

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", power='" + power + '\'' +
                ", location='" + location + '\'' +
                ", state=" + state +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
