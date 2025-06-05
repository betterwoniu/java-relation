package org.data.springbootgrpcserver.config;

import org.data.springbootgrpcserver.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login","/login/**", "/error**","/login/oauth2/code/**","/logout").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo->userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/user", true)
                        .loginPage("/login/oauth2")
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization")  // 设置客户端发起OAuth2.0授权的的基础路径 GET /oauth2/authorization/github、GET /oauth2/authorization/google
                        )
                )
                .oauth2Client(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                )
        ;

        return httpSecurity.build();
    }


      // 通过bean 注册一个自定义的github配置（放在缓存中）
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(){
//
//        return  new InMemoryClientRegistrationRepository(this.githubClientRegistration());
//    }

    /**
     * 自定义的一个github配置 详细信息
     * @return
     */
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


    /**
     * userInfoEndpoint 端点的 映射用户权限 使用GrantedAuthoritiesMapper 实现
     * @return
     */

    @Bean
    public  GrantedAuthoritiesMapper userAuthoritiesMapper(){

        return (authorities)-> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority-> {

                if (OidcUserAuthority.class.isInstance(authority)) {

                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;
                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();


                }else if(OAuth2UserAuthority.class.isInstance(authority)) {

                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;
                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();



                }

            });

            return  mappedAuthorities;
        };

    }


    /**
     * 注销处理
     * @return
     */
    private LogoutSuccessHandler oidcLogoutSuccessHandler(){

        System.out.println("退出登录");
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        return oidcLogoutSuccessHandler;

    }
}
