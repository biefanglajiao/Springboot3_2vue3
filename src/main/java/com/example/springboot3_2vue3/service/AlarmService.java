package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot3_2vue3.domain.Alarm;

import com.example.springboot3_2vue3.mapper.AlarmMapper;
import com.example.springboot3_2vue3.resp.AlarmResp;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/18 11:47
 **/
@Service
public class AlarmService {
    @Resource
    private AlarmMapper alarmMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public List<AlarmResp> selectAllinfo() {
        List<Alarm> alarms = alarmMapper.selectAllinfo();
        System.out.println(alarms +
                "-------------------");
        List<AlarmResp> alarmResps = new ArrayList<>();
        for (Alarm alarm : alarms) {
            AlarmResp alarmResp = new AlarmResp();
            alarmResp.setId(alarm.getId());
            alarmResp.setDescription(alarm.getDescription());
            alarmResp.setDate(alarm.getDate());
            alarmResp.setEquipmentid(alarm.getEquipmentid());
            alarmResp.setRead(alarm.isRead());
            alarmResp.setLevel(alarm.getLevel());
            alarmResp.setName(alarm.getEquipment().getName());
            alarmResps.add(alarmResp);
        }
        return alarmResps;
    }

    public List<AlarmResp> selectAllNoreadinfo() {
        List<Alarm> alarms = alarmMapper.selectAllNoreadinfo();

        List<AlarmResp> alarmResps = new ArrayList<>();
        for (Alarm alarm : alarms) {
            AlarmResp alarmResp = new AlarmResp();
            alarmResp.setId(alarm.getId());
            alarmResp.setDescription(alarm.getDescription());
            alarmResp.setDate(alarm.getDate());
            alarmResp.setEquipmentid(alarm.getEquipmentid());
            alarmResp.setRead(alarm.isRead());
            alarmResp.setLevel(alarm.getLevel());
            alarmResp.setName(alarm.getEquipment().getName());
            alarmResps.add(alarmResp);
        }
        return alarmResps;
    }


    public int read(Long id) {
        UpdateWrapper<Alarm> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("`read`", 1);
        return alarmMapper.update(null, updateWrapper);
    }

    public  int addalarm(Alarm alarm){
        rocketMQTemplate.convertAndSend("VOTE_TOPIC","【请注意】，你有一条告警信息，请及时查看！！");
        return alarmMapper.insert(alarm);
    }
}
