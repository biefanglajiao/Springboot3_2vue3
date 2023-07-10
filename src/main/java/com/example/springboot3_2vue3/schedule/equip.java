package com.example.springboot3_2vue3.schedule;

import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.domain.Alarm;
import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.resp.DeviceusePowerResp;
import com.example.springboot3_2vue3.service.*;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 22:34
 **/
@Component
public class equip {
    @Resource
    private VariationService variationService;
    @Resource
    private DeviceuseService deviceuseService;
    @Resource
    private DailyequipmentpoweruseService dailyequipmentpoweruseService;
    @Resource
    private YearpoweruseService yearpoweruseService;
    @Resource
    private Variation variation;
    @Resource
    private SnowFlake snowFlake;
    @Resource
    private AlarmService alarmService;

    //每小时执行一次
//    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Scheduled(cron = "0 0/30 * * * ? ")
    @Async
    @Transactional
    @Scheduled(cron = "0 0/1 * * * ? ")//没1分钟
    public void 耗电量小时统计() {
        //获取开着的设备信息包含power所以用resp接
        List<DeviceusePowerResp> lists = deviceuseService.findall();
        //获取今天的日期毫秒型
        long startTime = System.currentTimeMillis();

        if (!lists.isEmpty()) {
            for (DeviceusePowerResp list : lists) {

                //对于每个开着的设备  每小时计算一次耗电量
//                System.out.println(list.getDate());
                long opendeTime = startTime - list.getDate();
//                System.out.println("opendeTime" + opendeTime + "ms");
//                System.out.println("opendeTime" + opendeTime / 1000 + "s");
//                System.out.println("opendeTime" + opendeTime / 1000 / 60 + "min");
                float opendeTimemin = opendeTime / 1000 / 60;
                float opendeTimeHour = opendeTimemin / 60;
                float opendeDayTimeHour = opendeTimeHour % 24;//取余数  过了24小时的话就从0开始
//                System.out.println(opendeTimeHour);
                //给小时耗电量统计表赋值
                variation.setEquipmentid(list.getEquipmentid());
//                System.out.println("list.getPower()"+list.getPower() );
//                System.out.println("opendeTimeHour"+opendeTimeHour);
//                System.out.println("chengji :"+list.getPower() * opendeDayTimeHour);

//                Float beforepower= variationService.selectAlltodayByIdd(list.getEquipmentid());
                //查询今天开启过的现在关闭了的耗电量
                Float beforepower = dailyequipmentpoweruseService.selectAlltodayByIdd(list.getEquipmentid());
                //查日志表的  有没有今天的数据 有的化 加上去 没有的话就是0

//                System.out.println("beforepower"+beforepower);
                variation.setData(list.getPower() * opendeDayTimeHour + beforepower);
                boolean insertonedata = variationService.insertonedata(variation);
                System.out.println("小时记录：" + insertonedata);


                //——————————————————————————————————————————————定时更新目前耗电总量————————————————————————————
                Variation variation1 = variationService.findsuminfo();
                yearpoweruseService.InsertOrUpdatePoweruse(variation1.getData() / 1000);//wh=>kwh

//__________________________________________________是否有长时间开着的设备=》告警_____________________________________________________
                //如果开着的时间超过了24小时就报警
                if (opendeTimeHour > 8) {
                    //报警
                    Alarm alarm = new Alarm();

                    alarm.setId(snowFlake.nextId());
                    alarm.setDescription("设备id" + list.getEquipmentid() + "已经连续使用了超过" + opendeTimeHour + "小时.请检查设备是否正常,并及时关闭设备");
                    alarm.setEquipmentid(list.getEquipmentid());
                    alarm.setRead(false);
                    alarm.setLevel(1);
                    alarmService.addalarm(alarm);
                }
            }
        }

    }

    @Scheduled(cron = "0 59 23 * * ? ")//每天23点59分执行一次
    @Async
    @Transactional
    public void 耗电量天统计() {
        Variation variation1 = variationService.findsuminfo();
        yearpoweruseService.InsertOrUpdatePoweruse(variation1.getData() / 1000);//wh=>kwh

    }


}
