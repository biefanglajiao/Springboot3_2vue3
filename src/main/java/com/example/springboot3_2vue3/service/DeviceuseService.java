package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse;
import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.mapper.equipmapper.DeviceuseMapper;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.DeviceusePower;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/16 8:24
 **/
@Service
public class DeviceuseService {
    @Resource
    private DeviceuseMapper deciveuseMapper;
    @Resource
    private  Deviceuse deviceuse;

    public List<Deviceuse> selectList(){
        return  deciveuseMapper.selectList(null);
    }


    public CommonResp opendevice(Deviceuse deviceuse){
        QueryWrapper<Deviceuse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("equipmentid",deviceuse.getEquipmentid());
        CommonResp commonResp=new CommonResp();
      if(  deciveuseMapper.exists(queryWrapper)){
          commonResp.setMessage("设备已打开，请勿重复开启");
      }else {

          deciveuseMapper.insert(deviceuse);
      } commonResp.setMessage("设备已打开，信息已经记录");
      return commonResp;
    }
    public CommonResp deletdevice(Deviceuse deviceuse){
        QueryWrapper<Deviceuse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("equipmentid",deviceuse.getEquipmentid());
        CommonResp commonResp=new CommonResp();
      if(  !deciveuseMapper.exists(queryWrapper)){
          commonResp.setMessage("设备无相关开启信息");
      }else {

          deciveuseMapper.delete(queryWrapper);
      } commonResp.setMessage("设备已关闭，记录信息已经删除");
      return commonResp;
    }
}
