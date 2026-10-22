package com.clinico.mentis.infraestructura.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Clave secreta (mínimo 256 bits)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000L; // 24 Horas en milisegundos

    public String generarToken(String username, String nombre, String registro) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombreProfesional", nombre);
        claims.put("registroProfesional", registro);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String obtenerUsername(String token) {
        return obtenerClaims(token).getSubject();
    }

    public boolean esTokenValido(String token, String username) {
        final String user = obtenerUsername(token);
        return (user.equals(username) && !esTokenExpirado(token));
    }

    private boolean esTokenExpirado(String token) {
        return obtenerClaims(token).getExpiration().before(new Date());
    }

    private Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
