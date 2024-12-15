package com.example.telexpress.config;
import com.example.telexpress.entity.Usuario;
import com.example.telexpress.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;


import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityWeb {

    final DataSource dataSource;
    public SecurityWeb(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Autowired
    UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // Configuración para el login de los agentes
        /*http.formLogin(form -> form
                .loginPage("/loginAgente") // Página de login para Agentes
                .loginProcessingUrl("/procesologueoAgente") // Procesamiento del login para agentes
                .usernameParameter("codigodespachador") // Usa el código de despachador como username
                .passwordParameter("password") // Sigue usando la contraseña estándar
                .successHandler(authenticationSuccessHandler()) // Llama a un método separado
                .permitAll()
        );*/
        //http.formLogin();
       //http.csrf(AbstractHttpConfigurer::disable)
                http
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/procesologueo")
                        .failureUrl("/login?error=invalidCredentials") // En caso las credenciales ingresadas sean erróneas
                        .usernameParameter("email")
                        .successHandler(authenticationSuccessHandler()) // Llama a un método separado
                        .permitAll() // Usar el formulario de login por defecto de Spring Security
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if (authentication != null && authentication.getName() != null) {
                                // Obtener el usuario por correo
                                Usuario usuario = usuarioRepository.findByCorreo(authentication.getName());
                                if (usuario != null) {
                                    usuario.setLastLogin(LocalDateTime.now()); // Actualiza el último login
                                    usuario.setEstadoUsuario("Inactivo"); // Cambia el estado a "Inactivo"
                                    usuarioRepository.save(usuario); // Guarda los cambios
                                    System.out.println("Usuario " + usuario.getCorreo() + " cerró sesión. Último login registrado.");
                                }
                            }
                            // Redirigir a la página de login después del logout
                            response.sendRedirect("/login?logout");
                        })
                        .invalidateHttpSession(true) // Invalida la sesión actual
                        .deleteCookies("JSESSIONID") // Elimina la cookie de sesión JSESSIONID
                        .permitAll() // Permitir a todos realizar logout
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Solo permite una sesión activa por usuario
                        .maxSessionsPreventsLogin(false) // Si se intenta iniciar sesión nuevamente, invalida la sesión anterior
                        .expiredUrl("/login?expired") // Redirige si la sesión expira
                ).authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/usuarios/dni/**").permitAll()
                        .requestMatchers("/login","/").permitAll()
                        .requestMatchers ("/agente","/agente/**").hasAuthority("Agente") //acceso solo para agentes
                        .requestMatchers("/superadmin", "/superadmin/**", "/producto/**").hasAuthority("Superadmin")
                        .requestMatchers("/coordinador","/coordinador/**").hasAuthority("Coordinador")
                        .requestMatchers("/usuario","/usuario/**").hasAnyAuthority("Superadmin", "Usuario")
                        .requestMatchers("/api/**").hasAnyAuthority("Superadmin", "Usuario", "Agente")
                        .anyRequest().permitAll()
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->{
                            response.sendRedirect("/login?error=accessDenied");
                        })
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
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }



    @Bean
    public UserDetailsManager userDetailsManager(){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        //String sql2 = "select u.correo as username, r.rol as authority FROM usuario u INNER JOIN roles r ON u.idroles = r.idRoles WHERE u.correo = ? AND u.isBan = 0";
        //String sql1 = "select correo , contrasena , (CASE WHEN isBan = 0 THEN 1 ELSE 0 END) as activo FROM usuario WHERE correo = ?" ;
        //String sql2 = "select u.correo , r.rol  FROM usuario u INNER JOIN roles r ON u.idroles = r.idRoles WHERE u.correo = ? AND u.isBan = 0";
        String sql1 = "select correo , contrasena ,  (CASE WHEN isBan = 0 THEN 1 ELSE 0 END) as enabled FROM usuario WHERE correo = ?" ;
        String sql2 = "select u.correo , r.rol  FROM usuario u INNER JOIN roles r ON (u.idroles = r.idRoles) WHERE u.correo = ? AND u.isBan = 0 ";


        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);

        return users;

    }

    // Implementación de AuthenticationSuccessHandler dentro del mismo archivo
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
                // Obtener el usuario autenticado por su correo
                String correo = authentication.getName();
                Usuario usuario = usuarioRepository.findByCorreo(correo);

                if (usuario != null) {
                    // Actualizar lastLogin y estadoUsuario
                    usuario.setLastLogin(LocalDateTime.now());
                    usuario.setEstadoUsuario("Activo");
                    usuarioRepository.save(usuario); // Guardar los cambios en la base de datos
                }

                // Guardar el usuario en la sesión (ya estaba en tu código original)
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // Verificar si existe una URL guardada en la sesión (DefaultSavedRequest)
                DefaultSavedRequest savedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                if (savedRequest != null) {
                    String targetURL = savedRequest.getRedirectUrl();
                    redirectStrategy.sendRedirect(request, response, targetURL);
                } else {
                    // Obtener los roles del usuario autenticado
                    Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();

                    // Redirigir de acuerdo al rol
                    if (authorities.stream().anyMatch(role -> role.getAuthority().equals("Superadmin"))) {
                        response.sendRedirect("/superadmin/inicio_superadmin");
                    } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("Coordinador"))) {
                        response.sendRedirect("/coordinador/inicio_coordinador_zonal");
                    } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("Agente"))) {
                        response.sendRedirect("/agente/inicio");
                    } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("Usuario"))) {
                        response.sendRedirect("/usuario/inicio_usuariofinal");
                    } else {
                        // Si no tiene ningún rol específico, redirige a una página por defecto
                        response.sendRedirect("/usuario/resenia");
                    }
                }
            }

        };
    }
}
