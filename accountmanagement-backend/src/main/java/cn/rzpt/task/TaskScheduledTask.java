package cn.rzpt.task;


import cn.rzpt.service.TaskService;
import cn.rzpt.util.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务
 */
@Component
@Slf4j
public class TaskScheduledTask {

    @Resource
    private TaskService taskService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledTask() {
        long startTime = System.currentTimeMillis();
        log.info("定时任务开始");
        boolean result = taskService.deleteDayTask();
        EmailUtils emailUtils = new EmailUtils();
        try {
            long endTime = System.currentTimeMillis();
            emailUtils.sendMail("waxjava04@163.com","Account System","Hi Wax,Delete is " + (result ? "success": "error") + "total time:" + (endTime - startTime) + "ms");
            log.info("定时任务结束耗时:{},是否成功:{}",endTime - startTime,result ? "成功" : "失败" + "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
