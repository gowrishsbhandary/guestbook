package com.bt.guestbook.security;

import com.bt.guestbook.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String[] AUTH_WHITELIST = {
    "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/index.html"
  };

  private final LoginService loginService;

  public SecurityConfiguration(LoginService loginService) {
    this.loginService = loginService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(AUTH_WHITELIST)
        .permitAll()
        .antMatchers("/profile/**")
        .authenticated()
        .antMatchers("/admin/**")
        .hasRole("ADMIN")
        .antMatchers("/guest/**")
        .hasRole("GUEST")
        .and()
        .formLogin()
        .loginProcessingUrl("/signin")
        .loginPage("/login")
        .permitAll()
        .usernameParameter("txtUsername")
        .passwordParameter("txtPassword")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .and()
        .rememberMe()
        .tokenValiditySeconds(2592000)
        .key("mySecret!")
        .rememberMeParameter("checkRememberMe");
  }

  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(this.loginService);

    return daoAuthenticationProvider;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
