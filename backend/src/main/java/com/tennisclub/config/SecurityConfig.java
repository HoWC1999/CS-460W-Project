package com.tennisclub.config;

import com.tennisclub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()  // Enable CORS
      .and()
      .csrf().disable() // Disable CSRF for testing

      // Allow unauthenticated access to login and register endpoints

      .headers()
        .frameOptions().disable()
        .and()
      .authorizeRequests()
      .antMatchers("/api/auth/**", "/api/users/register").permitAll()
      // Optionally, if you want to allow /api/users/me without authentication, use .permitAll()
      // Otherwise, require authentication for /api/users/me:
      .antMatchers("/api/users/me").authenticated()
      .antMatchers("/h2-console/**").permitAll()
      .anyRequest().authenticated()
      .and()
      // Add the JWT filter before the standard authentication filter
      .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public OncePerRequestFilter jwtAuthenticationFilter() {
    return new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          String token = authHeader.substring(7);
          try {
            // Validate token and extract the username
            String username = JwtUtil.validateToken(token);
            // Create an authentication token; for simplicity, no authorities are set.
            UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
          } catch (Exception e) {
            // Invalid token; clear any authentication in context.
            SecurityContextHolder.clearContext();
          }
        }
        filterChain.doFilter(request, response);
      }
    };
  }
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return new CorsFilter(source);
  }
}
