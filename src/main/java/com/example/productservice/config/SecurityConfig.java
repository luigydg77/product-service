package com.example.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar anotaciones @PreAuthorize en Controllers/Services
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar CSRF (Típico para arquitecturas REST basadas en token o microservicios)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Permitir el uso de H2 Console (requiere deshabilitar x-frame-options en la cabecera)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            
            // 3. Configurar reglas de autorización de las peticiones
            .authorizeHttpRequests(auth -> auth
                // Acceso libre a la consola de H2 para desarrollo
                .requestMatchers("/h2-console/**").permitAll()
                
                // Permitar ver Swagger/OpenAPI o estados de salud (si existieran)
                .requestMatchers("/actuator/health", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                
                // Endpoints REST de Productos regulados por anotaciones @PreAuthorize fina,
                // Pero podemos poner una regla general de autenticación base aquí:
                .requestMatchers("/api/products/**").authenticated()
                
                // Cualquier otra solicitud requiere estar autenticado
                .anyRequest().authenticated()
            )
            
            // 4. Configurar política de sesión sin estado (Stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. Utilizar autenticación básica HTTP para propósitos del microservicio / desarrollo rápido
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Configuramos dos usuarios en memoria para probar las autorizaciones
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("user123"))
                .roles("USER") // Rol: ROLE_USER
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN", "USER") // Rol: ROLE_ADMIN, ROLE_USER
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
