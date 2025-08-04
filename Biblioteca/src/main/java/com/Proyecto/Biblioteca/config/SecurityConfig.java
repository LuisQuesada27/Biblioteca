package com.Proyecto.Biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configuración de autorizaciones
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers(
                    "/",
                    "/login",
                    "/registro",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                
                // Rutas de administración
                .requestMatchers(
                    "/admin/**",
                    "/libros/editar/**",
                    "/libros/eliminar/**",
                    "/usuarios/**"
                ).hasRole("ADMIN")
                
                // Rutas de usuario autenticado
                .requestMatchers(
                    "/prestamos/**",
                    "/perfil"
                ).hasAnyRole("USER", "ADMIN")
                
                // Todas las demás requieren autenticación
                .anyRequest().authenticated()
            )
            
            // Configuración del formulario de login
            .formLogin(form -> form
                .loginPage("/login") // Página personalizada de login
                .defaultSuccessUrl("/libros", true) // Redirección después de login exitoso
                .failureUrl("/login?error=true") // Redirección cuando falla el login
                .permitAll()
            )
            
            // Configuración de logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // Redirección después de logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Manejo de excepciones
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/acceso-denegado") // Página para errores 403
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}