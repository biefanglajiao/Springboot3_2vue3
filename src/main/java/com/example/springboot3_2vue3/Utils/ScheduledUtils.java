package com.example.springboot3_2vue3.Utils;

import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description: 定义枚举类  对应任务操作的路径名
 * @DateTime: 2023/7/9 10:09
 **/
@Component
public class ScheduledUtils {

    //测试类类路径
    public static final String TEST_PACKAGE = "com.example.springboot3_2vue3.service.scheduled.Test";

    //开启定时任务类类路径
    public static final String START_PACKAGE = "com.example.springboot3_2vue3.service.scheduled.Openequip";

    //关闭定时任务类类路径
    public static final String STOP_PACKAGE = "com.example.springboot3_2vue3.service.scheduled.Closeequip";

    //对返回的结果集封装---不要显示代码的包名
    public List<Scheduledcron> codeToWord(List<Scheduledcron> scheduledcrons){
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

}
