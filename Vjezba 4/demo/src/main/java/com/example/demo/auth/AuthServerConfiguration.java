package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Qualifier("authenticationManagerBean")
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        System.out.println("endpoints");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("client")
                .secret("{noop}noonewilleverguess")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .scopes("read","write").authorities("CLIENT").accessTokenValiditySeconds(5000);
        System.out.println("clients");
    }

    @Configuration
    protected static class AuthenticationManagerConfiguration extends org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder authenticationMngr) throws Exception {
            authenticationMngr.inMemoryAuthentication().withUser("admin").password("{noop}admin")
                    .roles("ADMIN");
        }
    }
}