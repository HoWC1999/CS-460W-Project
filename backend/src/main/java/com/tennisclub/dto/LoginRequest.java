package com.tennisclub.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class  LoginRequest {
  private String username;
  private String password; // raw password from the client

  public LoginRequest() {}

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

}
