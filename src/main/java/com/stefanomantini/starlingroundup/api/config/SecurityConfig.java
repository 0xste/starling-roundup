package com.stefanomantini.starlingroundup.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final String defaultRole = "ROLE_USER";
  private final String defaultUser;
  private final String defaultPassword;
  private final Boolean basicAuthEnabled;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public SecurityConfig(
      @Value("${roundup.defaultUser}") final String defaultUser,
      @Value("${roundup.defaultPassword}") final String defaultPassword,
      @Value("${roundup.basicAuthEnabled}") final Boolean basicAuthEnabled) {
    this.defaultUser = defaultUser;
    this.defaultPassword = defaultPassword;
    this.basicAuthEnabled = basicAuthEnabled;
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    if (basicAuthEnabled) {
      http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    if (basicAuthEnabled) {
      auth.inMemoryAuthentication()
          .withUser(defaultUser)
          .password(passwordEncoder().encode(defaultPassword))
          .authorities(defaultRole);
    }
  }
}
