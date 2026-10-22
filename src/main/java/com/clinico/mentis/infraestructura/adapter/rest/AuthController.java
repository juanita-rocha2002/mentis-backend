package com.clinico.mentis.infraestructura.adapter.rest;

import com.clinico.mentis.infraestructura.adapter.persistence.SpringDataUsuarioRepository;
import com.clinico.mentis.infraestructura.adapter.persistence.entity.UsuarioEntity;
import com.clinico.mentis.infraestructura.config.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SpringDataUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(SpringDataUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("passwordHash");

        var usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(password, usuarioOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
        }

        UsuarioEntity usuario = usuarioOpt.get();
        String token = jwtService.generarToken(usuario.getUsername(), usuario.getNombreProfesional(), usuario.getRegistroProfesional());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", usuario.getUsername(),
                "nombreProfesional", usuario.getNombreProfesional(),
                "registroProfesional", usuario.getRegistroProfesional()
        ));
    }

    // Endpoint auxiliar para crear el usuario inicial
    @PostMapping("/crear-usuario-inicial")
    public ResponseEntity<?> crearUsuarioInicial(@RequestBody UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }
}