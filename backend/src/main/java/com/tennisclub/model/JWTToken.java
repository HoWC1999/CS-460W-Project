package com.tennisclub.model;

import java.util.Date;

public class JWTToken {
  private String token;
  private Date expiry;

  public JWTToken() {
  }

  public JWTToken(String token, Date expiry) {
    this.token = token;
    this.expiry = expiry;
  }

  // Getters and setters
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getExpiry() {
    return expiry;
  }

  public void setExpiry(Date expiry) {
    this.expiry = expiry;
  }

  public boolean isValid() {
    return new Date().before(expiry);
  }
}
