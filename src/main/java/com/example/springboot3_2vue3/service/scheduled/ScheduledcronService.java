package com.example.springboot3_2vue3.service.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.Utils.ScheduledUtils;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.mapper.scheduled.ScheduledcronMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 14:24
 **/
@Service
public class ScheduledcronService {

    @Resource
    private ScheduledcronMapper scheduledcronMapper;


    public List<Scheduledcron> findByCronKey(String cronKey) {


        List<Scheduledcron> byCronKey = scheduledcronMapper.findByCronKey(cronKey);
//        System.out.println(byCronKey.isStatus()+"!!!!!!!");
        return byCronKey;
    }

    //新增
    public int insert(Scheduledcron scheduledcron){
        return scheduledcronMapper.insert(scheduledcron);
    }
    //删除
    public int deleteByPrimaryKey(Long id){
        return scheduledcronMapper.deleteById(id);
    }

    //修改
    public int updateByPrimaryKey(Scheduledcron scheduledcron){
        return scheduledcronMapper.updateById(scheduledcron);
    }
    //查询
    public Scheduledcron selectByPrimaryKey(Long id){
        return scheduledcronMapper.selectById(id);
    }
    //查询所有
    public List<Scheduledcron> selectAll(){
        List<Scheduledcron> scheduledcrons = scheduledcronMapper.selectList(null);
        for (Scheduledcron scheduledcron : scheduledcrons) {
            if (scheduledcron.getCronkey().equals(ScheduledUtils.TEST_PACKAGE)){
                scheduledcron.setCronkey("测试");
            }else if (scheduledcron.getCronkey().equals(ScheduledUtils.START_PACKAGE)){
                scheduledcron.setCronkey("开启定时任务");
            }
            else if (scheduledcron.getCronkey().equals(ScheduledUtils.STOP_PACKAGE)){
                scheduledcron.setCronkey("关闭定时任务");
            }
        }
        return scheduledcrons;
    }
    //根据cronKey模糊查询


    public List<Scheduledcron> findByExplanLike(String explan){
        QueryWrapper<Scheduledcron> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("explan",explan);
        return scheduledcronMapper.selectList(queryWrapper);

    }





}
