package org.telebot.telegrambot;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityProcessor {
  private Long userid;
  
  public SecurityProcessor(Long userid) {
    this.userid = userid;
  }
  
  public boolean isAuthorized() {
    String status = responseForStatus();
    if (status != null)
      return status.equals("active"); 
    return false;
  }
  
  public boolean userExists() {
    PostgreSQLConnector conn = new PostgreSQLConnector();
    try {
      ResultSet set = conn.getResultSet("SELECT * FROM telegram where userid=" + this.userid + ";");
      return set.next();
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    } 
  }
  
  public String responseForStatus() {
    PostgreSQLConnector conn = new PostgreSQLConnector();
    try {
      ResultSet set = conn.getResultSet("SELECT userstatus FROM telegram where userid=" + this.userid + ";");
      set.next();
      return set.getString("userstatus");
    } catch (SQLException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public void createNewUser(Long userid) {
    String query = "INSERT INTO telegram (userid,userstatus) VALUES (" + userid + ",'pending');";
    PostgreSQLConnector connector = new PostgreSQLConnector();
    connector.insertQuery(query);
  }
  
  public void setUserStatus(String status) {
    String query = "UPDATE telegram SET userstatus='" + status + "' WHERE userid=" + this.userid + ";";
    PostgreSQLConnector connector = new PostgreSQLConnector();
    connector.insertQuery(query);
  }
  
  public void setUserPhone(String phone) {
    String query = "UPDATE telegram SET phone='" + phone + "' WHERE userid=" + this.userid + ";";
    PostgreSQLConnector connector = new PostgreSQLConnector();
    connector.insertQuery(query);
  }
  
  public String getPhone(Long userid) {
    String query = "SELECT * from telegram where userid=" + userid + ";";
    PostgreSQLConnector connector = new PostgreSQLConnector();
    try {
      ResultSet set = connector.getResultSet(query);
      set.next();
      return set.getString("phone");
    } catch (SQLException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean checkPhone(String phone) {
    PostgreSQLConnector conn = new PostgreSQLConnector();
    try {
      char[] buff = phone.toCharArray();
      int i = phone.indexOf("0");
      StringBuilder phonequery = new StringBuilder();
      for (; i < buff.length; i++) {
        if (Character.isDigit(buff[i]))
          phonequery.append(buff[i]); 
      } 
      ResultSet set = conn.getResultSet("SELECT * FROM phonesallowed where phone='" + phonequery + "';");
      return set.next();
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    } 
  }
}
