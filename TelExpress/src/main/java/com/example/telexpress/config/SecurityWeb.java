package com.example.telexpress.config;
import org.springframework.context.annotation.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authorization.AuthenticatedAuthorizationManager.authenticated;


import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityWeb {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //http.formLogin();
        http.formLogin(form -> form
                .permitAll()  // Usar el formulario de login por defecto de Spring Security
        );
                http.logout(logout -> logout
                        .permitAll()  // Permitir a todos realizar logout
                );

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers ("/agente","/agente/**").authenticated()
                .requestMatchers("/superadmin","/superadmin/**").authenticated()
                .anyRequest().permitAll()
        );
        return http.build();
    }

}
