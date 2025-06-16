package org.data.springbootgrpcclient.config;

import org.data.springbootgrpcclient.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler("JSESSIONID", "remember-me");
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/login","/login/**", "/error**","/login/oauth2/code/**").permitAll()
//                        .requestMatchers("/login/logout").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/login")
                                .defaultSuccessUrl("/index",true)

                )
                .logout(logout -> logout
                      .logoutRequestMatcher(request ->
                                        "GET".equals(request.getMethod()) &&
                                                "/logout".equals(request.getServletPath())
                                )
                        .logoutSuccessUrl("/login")
                        .addLogoutHandler(cookieClearingLogoutHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .httpBasic(withDefaults());
        ;

        return httpSecurity.build();
    }


    /**
     *  form表单自定义用户登陆处理，从sqlite 获取用户相关信息
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
