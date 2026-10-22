package com.clinico.mentis.infraestructura.config.security;

import com.clinico.mentis.infraestructura.adapter.persistence.SpringDataUsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SpringDataUsuarioRepository usuarioRepository;

    public JwtAuthenticationFilter(JwtService jwtService, SpringDataUsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String username = jwtService.obtenerUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var usuarioOpt = usuarioRepository.findByUsername(username);

            if (usuarioOpt.isPresent() && jwtService.esTokenValido(token, username)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
