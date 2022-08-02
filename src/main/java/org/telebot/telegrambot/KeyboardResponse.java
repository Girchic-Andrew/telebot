package org.telebot.telegrambot;

import java.io.IOException;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class KeyboardResponse implements Response {
  private Properties props;
  
  private Long chatid;
  
  private static final String propertiesFile = "/bot.properties";
  
  public KeyboardResponse(Long chatid) {
    this.chatid = chatid;
    this.props = loadProperties();
  }
  
  public HttpPost getPost() {
    HttpPost post = new HttpPost(this.props.getProperty("api") + this.props.getProperty("token") + "/sendMessage");
    post.addHeader("Content-type", "application/json");
    post.addHeader("charset", "UTF-8");
    JSONObject message = new JSONObject();
    message.put("chat_id", this.chatid);
    message.put("text", "Подтвердите Ваш аккаунт, нажав на кнопку ниже.");
    JSONObject inlineKeybord = new JSONObject();
    JSONArray rows = new JSONArray();
    JSONArray buttons = new JSONArray();
    JSONObject phoneButton = new JSONObject();
    phoneButton.put("text", "Подтвердить");
    phoneButton.put("request_contact", true);
    buttons.put(phoneButton);
    rows.put(buttons);
    inlineKeybord.put("keyboard", rows);
    inlineKeybord.put("one_time_keyboard", true);
    message.put("reply_markup", inlineKeybord);
    System.out.println(message.toString());
    StringEntity stringEntity = new StringEntity(message.toString(), ContentType.APPLICATION_JSON);
    post.setEntity((HttpEntity)stringEntity);
    return post;
  }
  
  private Properties loadProperties() {
    Properties creds = new Properties();
    try {
      creds.load(getClass().getResourceAsStream("/bot.properties"));
    } catch (IOException iOException) {}
    return creds;
  }
}
