package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.domain.equipment.Classification;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.equipment.Equipment_Classification_Option;
import com.example.springboot3_2vue3.mapper.equipmapper.Equipment_Classification_OptionMapper;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.AlarmService;
import com.example.springboot3_2vue3.service.ClassificationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ClassificationController
 * Package: com.example.springboot3_2vue3.controller
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/18 19:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/classification")
public class ClassificationController {

    @Resource
    private ClassificationService classificationService;
    @Resource
    private Equipment_Classification_OptionMapper equipmentClassificationOptionMapper;



    @GetMapping("/selectAll")
    public CommonResp selectAll(){
        List<Classification> lists= classificationService.findAll();
        CommonResp resp = new CommonResp<>();
        resp.setContent(lists);

        return resp;
    }

    @GetMapping("/selectById")
    public CommonResp selectById(Long id){
        List<Classification> lists= classificationService.findById(id);
        CommonResp resp = new CommonResp<>();
        resp.setContent(lists);

        return resp;
    }
    @GetMapping("/selectallById/{id}")
    public CommonResp selectAllById(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        List<Classification> lists = classificationService.findAllById(id);
        resp.setContent(lists);


        return resp;
    }

    @GetMapping("/selectallchild")
    public CommonResp selectAllchild(){
        CommonResp resp = new CommonResp<>();
        List<Classification> lists = classificationService.findAllchild();
        resp.setContent(lists);


        return resp;
    }



    //设备对应分类的更改的保存
    @PostMapping("/save")
    @Transactional
    public CommonResp selectAllchild(@RequestBody List<Long> list){
//list传4个数组数据    第一个时 设备id  第二个时第一个大分类的小分类  、、、、、
Long equipmentid = list.get(0);
Integer classificationid[] = new Integer[list.size()-1];
for (int i = 1; i < list.size(); i++) {
 classificationid[i-1] = list.get(i).intValue();
}

if (equipmentClassificationOptionMapper.selectByeid(equipmentid).size()>0){
    //查所有eid为equipmentid的数据 如果有 就删除

    equipmentClassificationOptionMapper.deleteByeid(equipmentid);//删除所有eid为equipmentid的数据
}
//新增数据

        for (int i = 0; i < classificationid.length; i++) {
            Equipment_Classification_Option equipmentClassificationOption=new Equipment_Classification_Option();
            equipmentClassificationOption.setEid(equipmentid);
            equipmentClassificationOption.setOptionId(classificationid[i]);

            //添加 eid为equipmentid optionId为 classificationid[i]的值
           equipmentClassificationOptionMapper.insertinfo(equipmentClassificationOption);
        }
        CommonResp resp = new CommonResp<>();
resp.setMessage("更改成功");

        return resp;
    }

}
