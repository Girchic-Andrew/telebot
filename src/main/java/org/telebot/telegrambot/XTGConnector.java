package org.telebot.telegrambot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class XTGConnector {
  private String url = "http://localhost:8080/rest/connection";
  
  public int requestForCode(String phone) {
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    HttpGet get = new HttpGet(this.url + "/connect/" + phone);
    try {
      HttpResponse response = closeableHttpClient.execute((HttpUriRequest)get);
      if (response.getStatusLine().getStatusCode() == 200)
        return 1; 
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return 0;
  }
  
  public String checkState(String phone) {
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    HttpGet get = new HttpGet(this.url + "/checkstate/" + phone);
    try {
      HttpResponse response = closeableHttpClient.execute((HttpUriRequest)get);
      HttpEntity entity = response.getEntity();
      String stateDto = IOUtils.toString(entity.getContent(), "utf-8");
      JSONObject object = new JSONObject(stateDto);
      String state = object.getString("connectionState");
      return state;
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public int postCode(String phone, String code) {
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    HttpPost post = new HttpPost(this.url + "/" + phone + "/code");
    JSONObject jsonEnt = new JSONObject();
    jsonEnt.put("code", code);
    try {
      StringEntity entity = new StringEntity(jsonEnt.toString());
      post.setEntity((HttpEntity)entity);
      post.setHeader("Content-Type", "application/json");
      System.out.println(IOUtils.toString(entity.getContent()));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      HttpResponse response = closeableHttpClient.execute((HttpUriRequest)post);
      if (response.getStatusLine().getStatusCode() == 200)
        return 1; 
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return 0;
  }
  
  public int postPassword(String phone, String code) {
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    HttpPost post = new HttpPost(this.url + "/" + phone + "/pass");
    JSONObject jsonEnt = new JSONObject();
    jsonEnt.put("pass", code);
    try {
      StringEntity entity = new StringEntity(jsonEnt.toString());
      post.setEntity((HttpEntity)entity);
      post.setHeader("Content-Type", "application/json");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    try {
      HttpResponse response = closeableHttpClient.execute((HttpUriRequest)post);
      if (response.getStatusLine().getStatusCode() == 200)
        return 1; 
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return 0;
  }
}
