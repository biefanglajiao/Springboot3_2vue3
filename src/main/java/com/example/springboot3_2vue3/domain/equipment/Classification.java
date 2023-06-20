package com.example.springboot3_2vue3.domain.equipment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.List;

/**
 * ClassName: Classification
 * Package: com.example.springboot3_2vue3.domain.equipment
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:19
 * @Version 1.0
 */
public class Classification {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;     //分类id
    @TableField("className")
    private String name;    //分类名
    @TableField(exist = false)
    private List<ClassificationOption> classificationOptions;   //分类所对应的分类项


    @Override
    public String toString() {
        return "Classification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classificationOptions=" + classificationOptions +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClassificationOption> getClassificationOptions() {
        return classificationOptions;
    }

    public void setClassificationOptions(List<ClassificationOption> classificationOptions) {
        this.classificationOptions = classificationOptions;
    }
}
