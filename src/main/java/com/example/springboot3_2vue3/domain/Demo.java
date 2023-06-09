package com.example.springboot3_2vue3.domain;

import org.springframework.stereotype.Component;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/9 16:01
 **/
@Component
public class Demo {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "text{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
