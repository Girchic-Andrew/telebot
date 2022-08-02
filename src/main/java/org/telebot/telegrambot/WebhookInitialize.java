package org.telebot.telegrambot;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.telebot.apiclient.HttpBotApiClient;

public class WebhookInitialize {
  private static final Logger logger = Logger.getRootLogger();
  
  private Properties properties = loadProperties();
  
  private String url = this.properties.getProperty("url");
  
  private String token = this.properties.getProperty("token");
  
  private static final String propertiesFile = "/bot.properties";
  
  private Properties loadProperties() {
    Properties creds = new Properties();
    try {
      creds.load(getClass().getResourceAsStream("/bot.properties"));
    } catch (IOException iOException) {}
    return creds;
  }
  
  public String init() {
    HttpBotApiClient client = new HttpBotApiClient();
    try {
      client.setWebhook(this.token, this.url);
      return "Ok";
    } catch (Exception ex) {
      logger.error("Cert file not found");
      return ex.getMessage();
    } 
  }
}
