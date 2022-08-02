package org.telebot.telegrambot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class MessageResponse implements Response {
  private Message message;
  
  private Properties props;
  
  private static final String propertiesFile = "/bot.properties";
  
  public MessageResponse(Message message) {
    this.message = message;
    this.props = loadProperties();
  }
  
  public HttpPost getPost() {
    HttpPost post = new HttpPost(this.props.getProperty("api") + this.props.getProperty("token") + "/sendMessage");
    post.addHeader("Content-type", "application/x-www-form-urlencoded");
    post.addHeader("charset", "UTF-8");
    List<BasicNameValuePair> list = new ArrayList<>();
    list.add(new BasicNameValuePair("chat_id", this.message.getChat_id().toString()));
    list.add(new BasicNameValuePair("parse_mode", "Markdown"));
    list.add(new BasicNameValuePair("text", this.message.getText()));
    try {
      UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
      post.setEntity((HttpEntity)urlEncodedFormEntity);
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
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
