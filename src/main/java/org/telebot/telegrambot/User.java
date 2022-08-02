package org.telebot.telegrambot;

public class User {
  private Integer id;
  
  private String status;
  
  public User(Integer id, String status) {
    this.id = id;
    this.status = status;
  }
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
}
