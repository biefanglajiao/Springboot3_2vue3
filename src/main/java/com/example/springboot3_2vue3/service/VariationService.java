package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse2;
import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.mapper.equipmapper.VariationMapper;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.VariationNameResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 22:50
 **/
@Service
public class VariationService {
    @Resource
    private VariationMapper variationMapper;


    public List<Variation> getAllVariation() {
        QueryWrapper<Variation> queryWrapper = new QueryWrapper<>();

        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        queryWrapper.like("date", formatter.format(date));
        return variationMapper.selectList(queryWrapper);
    }

//插入新数据
    public boolean insertonedata(Variation variation) {
        if (variationMapper.insert(variation) == 1) {
            return true;
        } else
            return false;
    }
//获取今日所有信息 包含equipment表的name字段
    public List<VariationNameResp> getAllVariationName() {
        QueryWrapper<Variation> queryWrapper = new QueryWrapper<>();

        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        String format = formatter.format(date);
   List<Variation> variation=variationMapper.findAllInfo(format);
   List<VariationNameResp> variationNameResps =new ArrayList<>();
        for (Variation v:variation
             ) {
            VariationNameResp resp=new VariationNameResp();
            resp.setName(v.getEquipment().getName());
            resp.setId(v.getId());
            resp.setData(v.getData());
            resp.setDate(v.getDate());
            resp.setEquipmentid(v.getEquipmentid());
            variationNameResps.add(resp);

        }
        return  variationNameResps;

    }

    //获取有几种用过电今天
    public List<Variation> getallclass(){
        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        String format = formatter.format(date);
        return variationMapper.getallclass(format);
    }


    public Float selectAlltodayByIdd(Long equipmentid){
        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datems = new Date(System.currentTimeMillis());
        String date = formatter.format(datems);
        List<Variation> variations= variationMapper.selectAlltodayById(equipmentid,date);

        if (variations.size()==0){
            return 0.0f;
        }
        else {
            return variations.get(0).getData();
        }


    }

}
