package com.example.demo_java_spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        private final CustomAuthencationEntryPoint customAuthencationEntryPoint;

        public SecurityConfig(CustomAuthencationEntryPoint customAuthencationEntryPoint) {
                this.customAuthencationEntryPoint = customAuthencationEntryPoint;
        }

        @Autowired
        private UserDetailServiceImp userDetailServiceImp;

        @Bean
        public UserDetailsService userDetailService() {
                return new UserDetailServiceImp();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                return http
                                .exceptionHandling((exceptionHandling) -> exceptionHandling
                                                .authenticationEntryPoint(customAuthencationEntryPoint))
                                .csrf(s -> s.disable())
                                .authorizeHttpRequests((authz) -> authz
                                                .requestMatchers("/api/v1/login")
                                                .permitAll())
                                .authorizeHttpRequests((authz) -> authz.requestMatchers("/api/v1/**")
                                                .authenticated())
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();

        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean

        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(userDetailServiceImp);
                authenticationProvider.setPasswordEncoder(passwordEncoder());
                return authenticationProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
                        throws Exception {
                return config.getAuthenticationManager();
        }
}
