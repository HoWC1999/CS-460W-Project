package com.tennisclub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
      .anyRequest().authenticated();
  }
}
