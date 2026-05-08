package com.tmdtud.cuahang.common.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        // // ===== PUBLIC ENDPOINTS =====
                        // .requestMatchers("/login", "/register/customers", "/register/employers").permitAll()
                        // .requestMatchers("/api/auth/me").permitAll()
                        // .requestMatchers(org.springframework.http.HttpMethod.GET,
                        //         "/api/products/**", "/api/categories/**", "/api/brands/**").permitAll()

                        // // ===== CUSTOMER ONLY =====
                        // .requestMatchers("/api/cart/**").hasRole("CUSTOMER")
                        // .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/orders/my-orders/**").hasRole("CUSTOMER")
                        // .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/orders").hasRole("CUSTOMER")

                        // // ===== STAFF ONLY =====
                        // .requestMatchers("/api/employers/**").hasRole("STAFF")
                        // .requestMatchers("/api/purchase_orders/**").hasRole("STAFF")
                        // .requestMatchers("/api/supplier/**").hasRole("STAFF")
                        // .requestMatchers("/api/dashboard/**").hasRole("STAFF")
                        // .requestMatchers(org.springframework.http.HttpMethod.POST,
                        //         "/api/products/**", "/api/categories/**", "/api/brands/**").hasRole("STAFF")
                        // .requestMatchers(org.springframework.http.HttpMethod.PUT,
                        //         "/api/products/**", "/api/categories/**", "/api/brands/**").hasRole("STAFF")
                        // .requestMatchers(org.springframework.http.HttpMethod.DELETE,
                        //         "/api/products/**", "/api/categories/**", "/api/brands/**").hasRole("STAFF")

                        // // ===== CUSTOMER OR STAFF =====
                        // .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/customers/**").hasAnyRole("CUSTOMER", "STAFF")
                        // .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/orders/**").hasAnyRole("CUSTOMER", "STAFF")
                        // .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/orders/**").hasAnyRole("CUSTOMER", "STAFF")
                        // .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/orders/**").hasAnyRole("CUSTOMER", "STAFF")

                        // // ===== STAFF FOR CUSTOMERS MANAGEMENT =====
                        // .requestMatchers("/api/customers/**").hasRole("STAFF")

                        .anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Cấu hình CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // URL của FE
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}