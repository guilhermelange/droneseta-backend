package com.udesc.droneseta.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApplicationSecurity {

    @Autowired
    private JwtRequestFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests()
            // Define as rotas que nao precisam de autenticacao
            .requestMatchers(
                    "/product/**",
                    "/static/**",
                    "/auth/**",
                    "/signup")
                .permitAll()
            .requestMatchers(HttpMethod.POST, "/customer").permitAll()
            .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex)
                -> {
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage()
                    );
                });

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
