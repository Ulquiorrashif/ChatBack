package com.example.chatback.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConf {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    //    @Bean
//    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(e->e.disable())
//                .authorizeHttpRequests(request->request.requestMatchers(antMatcher("/static/style.css"),
//                        antMatcher("/api/users/get"),antMatcher("/api/users/create"),antMatcher("/api/users/create/{id}"),
//                        antMatcher("/regPage")).permitAll().anyRequest().authenticated())
//                .formLogin((form) -> form
//                        .loginPage("/loginPage")
//                        .defaultSuccessUrl("/home",true)
//                        .passwordParameter("password")
//                        .usernameParameter("email")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//        return httpSecurity.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
//                .cors(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {request.requestMatchers(HttpMethod.POST,"/static/style.css",
                        "/api/users/get", "/api/users/create", "/api/users/create/{id}","/api/users/login").permitAll();
                        request.requestMatchers("/webs/**","/webs/info/","/topic/99","/**").permitAll()
                        .anyRequest().authenticated();
                })
                ;
//        ТЫ УБРАД ПРУТЬ ЛОГИНА !!!!!!
        return httpSecurity.build();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService(customUserDetailsService);
//        return authenticationProvider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }



}
