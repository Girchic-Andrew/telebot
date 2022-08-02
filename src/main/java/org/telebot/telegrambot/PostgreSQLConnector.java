package org.telebot.telegrambot;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.postgresql.Driver;

public class PostgreSQLConnector {
  private String url = "jdbc:postgresql://localhost:5432/postgres";
  
  private String user = "postgres";
  
  private String pass = "ghjkl1234";
  
  private Connection conn;
  
  public PostgreSQLConnector() {
    try {
  //    DriverManager.registerDriver((Driver)new Driver());
      this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public ResultSet getResultSet(String query) {
    try {
      Statement stmt = this.conn.createStatement();
      return stmt.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public int insertQuery(String query) {
    try {
      Statement stmt = this.conn.createStatement();
      stmt.executeUpdate(query);
      return 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    } 
  }
}
