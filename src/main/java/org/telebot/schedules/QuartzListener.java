package org.telebot.schedules;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzListener implements ServletContextListener {
  Scheduler scheduler = null;
  
  public void contextInitialized(ServletContextEvent servletContext) {
    System.out.println("Context Initialized");
    try {
      JobDetail job1 = JobBuilder.newJob(ClientsJob.class).withIdentity("CronQuartzJob", "Group").build();
      Trigger trigger = TriggerBuilder.newTrigger().withIdentity("TriggerName", "Group").withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule("0 0 0 * * ?")).build();
      this.scheduler = (new StdSchedulerFactory()).getScheduler();
      this.scheduler.start();
      this.scheduler.scheduleJob(job1, trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
    } 
  }
  
  public void contextDestroyed(ServletContextEvent servletContext) {
    System.out.println("Context Destroyed");
    try {
      this.scheduler.shutdown();
    } catch (SchedulerException e) {
      e.printStackTrace();
    } 
  }
}
