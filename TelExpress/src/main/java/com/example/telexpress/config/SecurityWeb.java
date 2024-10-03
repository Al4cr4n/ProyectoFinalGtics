package com.example.telexpress.config;
import org.springframework.context.annotation.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authorization.AuthenticatedAuthorizationManager.authenticated;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityWeb {

    final DataSource dataSource;
    public SecurityWeb(DataSource dataSource){
        this.dataSource = dataSource;
    }

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
                .requestMatchers ("/agente","/agente/**").hasAuthority("Agente") //acceso solo para agentes
                .requestMatchers("/superadmin","/superadmin/**").hasAuthority("Superadmin")  //acceso solo para superadmin
                .anyRequest().permitAll()
        );
        return http.build();
    }

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/

    public UserDetailsManager userDetailsManager(){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        String sql1 = "select correo, contrasena, (CASE WHEN isBan = 0 THEN 1  ESLSE 0 END) as activo FROM usuario WHERE correo = ?" ;
        String sql2 = "select u.correo, r.roles FROM usuario u INNER JOIN roles r ON (u.idroles = r.idRoles)" +
                "WHERE u.correo = ? AND us.isBan = 0";

        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);

        return users;

    }

}
