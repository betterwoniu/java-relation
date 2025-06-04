package org.data.springbootgrpcserver.config;

import org.data.springbootgrpcserver.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login**", "/error**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo->userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/user", true)
                );

        return httpSecurity.build();
    }



    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){

        return  new InMemoryClientRegistrationRepository(this.githubClientRegistration());
    }

    private ClientRegistration githubClientRegistration(){

        return ClientRegistration.withRegistrationId("github")
                .clientId("Ov23liS4x1Q75O4lXDwq")
                .clientSecret("857f64a782c81a6ef48a83dca287589a06d48e9c")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .scope("user:email,read:user")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("login")                    // GitHub 返回的用户名字段
                .clientName("GitHub")
                .build();
    }
}
