package com.example.springboot3_2vue3.domain.scheduled;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 13:58
 **/
public class Scheduledcron {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//定时任务id

    private String cronkey;//定时任务对应类
    private boolean status;//定时任务状态
    private String explan;//定时任务名称

    private String cronexpression;//定时任务表达式

    @Override
    public String toString() {
        return "Scheduledcron{" +
                "id=" + id +
                ", cronkey='" + cronkey + '\'' +
                ", status=" + status +
                ", explan='" + explan + '\'' +
                ", cronexpression='" + cronexpression + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCronkey() {
        return cronkey;
    }

    public void setCronkey(String cronkey) {
        this.cronkey = cronkey;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getExplan() {
        return explan;
    }

    public void setExplan(String explan) {
        this.explan = explan;
    }

    public String getCronexpression() {
        return cronexpression;
    }

    public void setCronexpression(String cronexpression) {
        this.cronexpression = cronexpression;
    }
}
