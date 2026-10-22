package com.clinico.mentis.infraestructura.adapter.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Correo o Usuario

    @Column(nullable = false)
    private String password; // Hash BCrypt

    @Column(name = "nombre_profesional", nullable = false)
    private String nombreProfesional;

    @Column(name = "registro_profesional", nullable = false)
    private String registroProfesional;

    private String rol = "ROLE_PROFESIONAL";
}
