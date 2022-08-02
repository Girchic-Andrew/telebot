package org.telebot.telegrambot;

public class Message {
  private Long chat_id;
  
  private String text;
  
  private String phone;
  
  public Message(Long chat_id, String text) {
    this.chat_id = chat_id;
    this.text = text;
  }
  
  public Long getChat_id() {
    return this.chat_id;
  }
  
  public void setChat_id(Long chat_id) {
    this.chat_id = chat_id;
  }
  
  public String getText() {
    return this.text;
  }
  
  public void setText(String text) {
    this.text = text;
  }
  
  public String getPhone() {
    return this.phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
}
