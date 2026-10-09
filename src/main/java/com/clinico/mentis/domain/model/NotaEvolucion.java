package com.clinico.mentis.domain.model;

import java.time.LocalDateTime;

public record NotaEvolucion(
        Long id,
        LocalDateTime fechaRegistro,
        String profesionalNombre,
        String contenido
) {}
