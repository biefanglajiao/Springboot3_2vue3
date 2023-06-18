package com.example.springboot3_2vue3.domain.equipment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.List;

/**
 * ClassName: ClassificationOption
 * Package: com.example.springboot3_2vue3.domain.equipment
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:18
 * @Version 1.0
 */
public class ClassificationOption {
    @TableId
    private Long id;     //分类项id
    private String name;    //分类项名
    @TableField
    private List<Equipment> equipments; //分类项下对应的设备


    @Override
    public String toString() {
        return "ClassificationOption{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", equipments=" + equipments +
                '}';
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
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
}
