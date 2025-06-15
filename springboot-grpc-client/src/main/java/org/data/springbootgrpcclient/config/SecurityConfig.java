package org.data.springbootgrpcclient.config;

import org.data.springbootgrpcclient.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/login","/login/**", "/error**","/login/oauth2/code/**").permitAll()
//                        .requestMatchers("/login/logout").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/login")
                                .defaultSuccessUrl("/index",true)

                )
                .logout((logout) -> logout
                        .logoutUrl("/logout") // 自定义退出URL
                        .logoutSuccessUrl("/login?logout") // 退出成功后跳转
                        .invalidateHttpSession(true) // 使会话无效
                        .deleteCookies("JSESSIONID") // 删除指定Cookie
                        .permitAll()

                )

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
