package org.telebot.apiclient;

import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class HttpBotApiClient implements BotApiClient {
  private static final Logger logger = Logger.getRootLogger();
  
  private HttpClient httpClient;
  
  private String apiURL = "https://api.telegram.org/bot";
  
  public HttpBotApiClient() {
    this.httpClient = (HttpClient)HttpClients.createDefault();
  }
  
  public void post(String query) {
    HttpPost post = new HttpPost(this.apiURL + query);
    try {
      this.httpClient.execute((HttpUriRequest)post);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void setWebhook(String token, String url) {
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    builder.addTextBody("url", url);
    HttpPost post = new HttpPost(this.apiURL + token + "/setWebhook");
    post.setEntity(builder.build());
    try {
      this.httpClient.execute((HttpUriRequest)post);
    } catch (IOException e) {
      logger.error("IOException while setting webhook");
    } 
  }
  
  public void sendMessage(String token, String message, Integer id) {
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    builder.addTextBody("chat_id", id.toString());
    builder.addTextBody("text", message);
    HttpPost post = new HttpPost(this.apiURL + token + "/sendMessage");
    post.setEntity(builder.build());
    try {
      this.httpClient.execute((HttpUriRequest)post);
    } catch (IOException e) {
      logger.error("IOException while sending message");
    } 
  }
}
