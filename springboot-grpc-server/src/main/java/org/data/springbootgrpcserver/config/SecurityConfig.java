package org.data.springbootgrpcserver.config;

import jakarta.servlet.Filter;
//import org.data.springbootgrpcserver.service.CustomOAuth2UserService;
import org.data.springbootgrpcserver.service.CustomSecurityContextRepository;
import org.data.springbootgrpcserver.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private CustomSecurityContextRepository customSecurityContextRepository;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

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
                                .authorizationRequestResolver(authorizationRequestResolver(this.clientRegistrationRepository) )
                                .baseUri("/oauth2/authorization")  // 设置客户端发起OAuth2.0授权的的基础路径 GET /oauth2/authorization/github、GET /oauth2/authorization/google

                        )
                )
                .oauth2Client(
                        oauth2Client -> oauth2Client
                                .authorizationCodeGrant(codeGrant -> codeGrant
                                        .accessTokenResponseClient(this.accessTokenResponseClient())
                                )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                )
                .addFilterBefore(new TenantFilter(), HeaderWriterFilter.class)  // 添加filter
                .formLogin(form -> form
                        .loginPage("/login/userAndPass")
                        .defaultSuccessUrl("/user",true)
                        )

                .securityContext(context-> context.securityContextRepository(customSecurityContextRepository))


        ;
        SecurityFilterChain  securityFilterChain = httpSecurity.build();
        System.out.println("加载了"+ securityFilterChain.getFilters().size()+"个 Filter" );
        for (Filter filter: securityFilterChain.getFilters()) {
            System.out.println(filter);
        }

        return securityFilterChain;
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


    /**
     * 自定义授权请求
     * @param clientRegistrationRepository
     * @return
     */
    private OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {

        DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/oauth2/authorization");
        authorizationRequestResolver.setAuthorizationRequestCustomizer(
                authorizationRequestCustomizer());

        return  authorizationRequestResolver;
    }

    private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
        return customizer -> customizer
                .additionalParameters(params -> params.put("prompt", "consent"));
    }


    /**
     * 自定义响应
     * @return
     */
    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {

        OAuth2AccessTokenResponseHttpMessageConverter accessTokenResponseMessageConverter =
                new OAuth2AccessTokenResponseHttpMessageConverter();
        accessTokenResponseMessageConverter.setAccessTokenResponseConverter(parameters -> {
            // ...
            return OAuth2AccessTokenResponse.withToken("custom-token")
                    // ...
                    .build();
        });

        RestClientAuthorizationCodeTokenResponseClient accessTokenResponseClient =
                new RestClientAuthorizationCodeTokenResponseClient();
        accessTokenResponseClient.addHeadersConverter(grantRequest -> {
            ClientRegistration clientRegistration = grantRequest.getClientRegistration();
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.USER_AGENT, "my-user-agent");
            return headers;
        });

        return accessTokenResponseClient;
    }




    @Bean
    CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

}


