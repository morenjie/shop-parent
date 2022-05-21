package com.qf.config;

import com.qf.job.OrderJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 在springboot里面整合quartz
 */
@Configuration
@SuppressWarnings("all")
public class QuartzConfig {
    /**
     * 在容器中注入methodInvokingJobDetailFactoryBean
     * @param orderJob  quartz需要被调度的方法所属的类
     *                  closeTimeoutOrder  quartz组件需要被调度的任务方法
     * @return
     */
    @Bean//job
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean(
            OrderJob orderJob){
        MethodInvokingJobDetailFactoryBean JobDetailFactoryBean = new
                MethodInvokingJobDetailFactoryBean();
        JobDetailFactoryBean.setTargetObject(orderJob);
        JobDetailFactoryBean.setTargetMethod("closeTimeoutOrder");
        return JobDetailFactoryBean;
    }

    /**
     * 在容器中注入cronTriggerFactoryBean  触发器
     * @param JobDetailFactoryBean
     * @return
     */
    @Bean//trigger（job）
    public CronTriggerFactoryBean cronTriggerFactoryBean(
            MethodInvokingJobDetailFactoryBean JobDetailFactoryBean){
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        //corn表达式 描述了触发器调度任务的方法时间规则
        triggerFactoryBean.setCronExpression("0/10 * * * * ?");
        triggerFactoryBean.setJobDetail(JobDetailFactoryBean.getObject());
        return triggerFactoryBean;
    }

    /**
     * 在容器中注入SchedulerFactoryBean注入调度工厂
     * @param triggerFactoryBean
     * @return
     */
    @Bean//scheduler
    public SchedulerFactoryBean schedulerFactoryBean(
            CronTriggerFactoryBean triggerFactoryBean){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(triggerFactoryBean.getObject());
        return schedulerFactoryBean;
    }
}
