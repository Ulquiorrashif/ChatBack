package com.example.chatback.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final UserAuthProvider userAuthProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header= request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(request.getParameter("token"));
        System.out.println("Отрабатывает ?");
        String token = request.getParameter("token");
        if (header != null) {
            String[] authElements = header.split(" ");
            System.out.println(Arrays.toString(authElements));
            if (authElements.length == 2
                    && "Bearer".equals(authElements[0])) {
                try {
                    if ("GET".equals(request.getMethod())) {
                        SecurityContextHolder.getContext().setAuthentication(
                                userAuthProvider.validateToken(authElements[1]));
                        System.out.println("Отрабатывает GET");
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(
                                userAuthProvider.validateTokenStrongly(authElements[1]));
                        System.out.println("Отрабатывает lheujq");
                    }
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }else if (token != null && !token.isEmpty()) {
            try {
                if ("GET".equals(request.getMethod())) {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthProvider.validateToken(token));
                    System.out.println("Отрабатывает GET 2 ");
                } else {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthProvider.validateTokenStrongly(token));
                    System.out.println("Отрабатывает lheujq 2");
                }
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }
            filterChain.doFilter(request,response);
    }

}
