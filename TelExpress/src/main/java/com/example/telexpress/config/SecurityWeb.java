package com.example.telexpress.config;
import org.springframework.context.annotation.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authorization.AuthenticatedAuthorizationManager.authenticated;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;


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
                .loginPage("/login")
                .loginProcessingUrl("/procesologueo")
                .usernameParameter("email")
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

    @Bean
    public PasswordEncoder passwordEncoder(){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("pass333");
        System.out.println("hash de pass333:");
        System.out.println(hashedPassword);
        String hashedPassword1 = encoder.encode("pass222");
        System.out.println("hash de pas222:");
        System.out.println(hashedPassword1);
        String hashedPassword2 = encoder.encode("pass111");
        System.out.println("hash de pass111:");
        System.out.println(hashedPassword2);
        String hashedPassword3 = encoder.encode("pas444");
        System.out.println("hash de pass444:");
        System.out.println(hashedPassword3);
        String hashedPassword4 = encoder.encode("pass555");
        System.out.println("hash de pass555:");
        System.out.println(hashedPassword4);
        String hashedPassword5 = encoder.encode("pass666");
        System.out.println("hash de pass666:");
        System.out.println(hashedPassword5);
        String hashedPassword6 = encoder.encode("pass777");
        System.out.println("hash de pass777:");
        System.out.println(hashedPassword6);
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }



    @Bean
    public UserDetailsManager userDetailsManager(){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        //obtencion de datos y estado del usuarioisBan=0
        //String sql1 = "select correo as username, contrasena as password, (CASE WHEN isBan = 0 THEN 1  ELSE 0 END) as enabled FROM usuario WHERE correo = ?" ;
        //obtencion del rol y acceso cuando isBan es 0
        //String sql2 = "select u.correo as username, r.rol as authority FROM usuario u INNER JOIN roles r ON u.idroles = r.idRoles WHERE u.correo = ? AND u.isBan = 0";
        //String sql1 = "select correo , contrasena , (CASE WHEN isBan = 0 THEN 1 ELSE 0 END) as activo FROM usuario WHERE correo = ?" ;
        //String sql2 = "select u.correo , r.rol  FROM usuario u INNER JOIN roles r ON u.idroles = r.idRoles WHERE u.correo = ? AND u.isBan = 0";
        String sql1 = "select correo , contrasena ,  (CASE WHEN isBan = 0 THEN 1 ELSE 0 END) as enabled FROM usuario WHERE correo = ?" ;
        String sql2 = "select u.correo , r.rol  FROM usuario u INNER JOIN roles r ON (u.idroles = r.idRoles) WHERE u.correo = ? AND u.isBan = 0 ";


        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);

        return users;

    }

}

/*return new JdbcUserDetailsManager(dataSource) {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserDetails userDetails = super.loadUserByUsername(username);
                String plainPassword = userDetails.getPassword();

                // Verifica si la contraseña no está hasheada
                if (!plainPassword.startsWith("$2a$")) { // Contraseña no hasheada
                    String hashedPassword = passwordEncoder.encode(plainPassword);

                    // Actualiza la contraseña directamente en la base de datos
                    String updateSql = "UPDATE usuario SET contrasena = ? WHERE correo = ?";
                    try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {
                        ps.setString(1, hashedPassword);
                        ps.setString(2, username);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException("Error al actualizar la contraseña en la base de datos", e);
                    }
                }


                return userDetails;
            }
        };*/