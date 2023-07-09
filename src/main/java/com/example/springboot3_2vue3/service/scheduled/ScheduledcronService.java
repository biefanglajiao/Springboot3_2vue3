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
    @Resource
    private ScheduledUtils scheduledUtils;


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

        return  scheduledUtils.codeToWord(scheduledcrons);
    }
    //根据cronKey模糊查询


    public List<Scheduledcron> findByExplanLike(String explan){
        QueryWrapper<Scheduledcron> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("explan",explan);
        return scheduledcronMapper.selectList(queryWrapper);

    }


    public List<Scheduledcron> findByStatus(String status) {
        QueryWrapper<Scheduledcron> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",status);
        List<Scheduledcron> scheduledcrons = scheduledcronMapper.selectList(queryWrapper);
        return scheduledUtils.codeToWord(scheduledcrons);//返回转化过的
    }

    public List<Scheduledcron> findByCronkey(String key) {

        QueryWrapper<Scheduledcron> queryWrapper = new QueryWrapper<>();
        if (key.equals("1")){
            queryWrapper.eq("cronkey",ScheduledUtils.START_PACKAGE);
        }else if (key.equals("0")){
            queryWrapper.eq("cronkey",ScheduledUtils.STOP_PACKAGE);
        }
        List<Scheduledcron> scheduledcrons = scheduledcronMapper.selectList(queryWrapper);
        return scheduledUtils.codeToWord(scheduledcrons);
    }
}
