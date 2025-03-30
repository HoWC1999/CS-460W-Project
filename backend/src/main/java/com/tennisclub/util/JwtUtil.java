package com.tennisclub.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
  private final String SECRET_KEY = "YourSecretKey"; // Move to properties in production
  private final long EXPIRATION_TIME = 3600000; // 1 hour

  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .compact();
  }

  public String validateToken(String token) {
    // Validate and parse token, return username if valid
    return Jwts.parser()
      .setSigningKey(SECRET_KEY)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }
}
