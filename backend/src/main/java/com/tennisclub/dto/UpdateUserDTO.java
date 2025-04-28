package com.tennisclub.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserDTO {
  private String username;
  private String email;
  private String password; // raw password, if being updated

}
