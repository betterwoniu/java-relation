package com.example.grpcserver.config;


import com.example.grpcserver.service.SecurityCustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/login/**", "/error**","/login/oauth2/code/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form.
                        loginPage("/login")
                        .defaultSuccessUrl("/index",true)
                );
    }


    @Bean
    SecurityCustomUserDetailsService securityCustomUserDetailsService () {
        return  new SecurityCustomUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
