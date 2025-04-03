package com.tennisclub.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

  // Generate a secure key for HS512 using the new key builder available in JJWT 0.12.0.
  // This replaces the deprecated secretKeyFor() helper method.
  private static final SecretKey key = Jwts.SIG.HS512.key().build();

  // Token expiration time: 1 hour (3600000 milliseconds)
  private final long EXPIRATION_TIME = 3600000;

  /**
   * Generates a JWT token for the given username.
   *
   * @param username the username to set as the subject in the token
   * @return a signed JWT token as a String
   */
  public String generateToken(String username, String role) {
    return Jwts.builder()
      .subject(username)
      .claim("role", role)// add role as a claim
      .signWith(key, SignatureAlgorithm.HS512)
      .compact();
  }


  /**
   * Validates the provided JWT token and extracts the subject (username).
   *
   * @param token the JWT token to validate
   * @return the subject (username) if the token is valid
   * @throws io.jsonwebtoken.JwtException if the token is invalid or expired
   */
  public static String validateToken(String token) {
    // Use parserBuilder() which is available in JJWT 0.12.0 to parse the token.
    return Jwts.parser()
      .verifyWith(key) // Set the signing key for verifying the token
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject(); // Extract the subject (username) from the token claims

  }
}
