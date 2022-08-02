package org.telebot.infrastructure;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
  private static final String propertiesFile = "/bot.properties";
  
  public Properties loadPropeties() {
    Properties creds = new Properties();
    try {
      creds.load(getClass().getResourceAsStream("/bot.properties"));
    } catch (IOException iOException) {}
    return creds;
  }
}
