package com.tennisclub.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A simple model to represent a JSON Web Token (JWT) along with its expiration time.
 */
@Setter
@Getter
public class JWTToken {

  /**
   * -- GETTER --
   *  Returns the JWT string.
   *
   *
   * -- SETTER --
   *  Sets the JWT string.
   *
   @return the token string
    * @param token the JWT string to set
   */
  // The JWT string
  private String token;

  /**
   * -- GETTER --
   *  Returns the expiration date of the token.
   *
   *
   * -- SETTER --
   *  Sets the expiration date of the token.
   *
   @return the expiry date
    * @param expiry the expiration date to set
   */
  // The expiration date of the token
  private Date expiry;

  /**
   * Default no-argument constructor.
   */
  public JWTToken() {
  }

  /**
   * Constructs a JWTToken with the specified token and expiration date.
   *
   * @param token  the JWT string
   * @param expiry the expiration date of the token
   */
  public JWTToken(String token, Date expiry) {
    this.token = token;
    this.expiry = expiry;
  }

  /**
   * Checks whether the token is still valid by comparing the current time with its expiration time.
   *
   * @return true if the token has not expired; false otherwise.
   */
  public boolean isValid() {
    return new Date().before(expiry);
  }

  @Override
  public String toString() {
    return "JWTToken{" +
      "token='" + token + '\'' +
      ", expiry=" + expiry +
      '}';
  }
}

