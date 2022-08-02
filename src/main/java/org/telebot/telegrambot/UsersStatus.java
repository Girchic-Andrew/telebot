package org.telebot.telegrambot;

public enum UsersStatus {
  STARTED("started"),
  PENDING("code"),
  PASSWORD("password"),
  ACTIVE("active");
  
  private final String value;
  
  UsersStatus(String s) {
    this.value = s;
  }
}
