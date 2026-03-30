package org.beginsecure.apitermi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Configuramos el encriptador de contraseñas (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Configuramos las reglas de acceso (Quién puede entrar a dónde)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas que requieren estar autenticado (iniciar sesión)
                        .requestMatchers("/tiendas/nueva", "/tiendas/editar/**", "/tiendas/borrar/**", "/tiendas/guardar").authenticated()

                        // Rutas públicas (ver la lista de tiendas es público)
                        .requestMatchers("/tiendas", "/").permitAll()

                        // Cualquier otra ruta extraña, por si acaso, que sea pública o la bloqueas
                        .anyRequest().permitAll()
                )
                // Usamos el formulario de login por defecto que trae Spring Boot
                .formLogin(form -> form
                        .defaultSuccessUrl("/tiendas", true) // Si el login es un éxito, llévalo a la lista
                        .permitAll()
                )
                // Configuramos el cierre de sesión
                .logout(logout -> logout
                        .logoutSuccessUrl("/tiendas") // Si cierra sesión, devuélvelo a la lista
                        .permitAll()
                );

        return http.build();
    }
}
