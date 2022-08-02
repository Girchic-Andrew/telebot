package org.telebot.schedules;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ClientsJob implements Job {
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    System.out.println("Job fired");
  }
}
