package com.clinico.mentis.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Consulta(
        Long id,
        String profesionalNombre,
        String profesionalRegistro,
        String usuarioNombre,
        String usuarioDocumentoTipo,
        String usuarioDocumentoNumero,
        LocalDate fechaAtencion,
        LocalTime horaInicio,
        LocalTime horaFin,
        Map<String, Object> datosFormulario,
        List<NotaEvolucion> evoluciones, // <--- Ahora es un listado
        String firmaProfesional,
        LocalDateTime fechaRegistro,
        LocalDateTime ultimaActualizacion
) {
    // Método de dominio para AGREGAR una nueva nota con la fecha y hora actual
    public Consulta agregarNuevaNotaManejo(String profesional, String nuevoContenido) {
        List<NotaEvolucion> listaActualizada = new ArrayList<>(this.evoluciones != null ? this.evoluciones : List.of());

        NotaEvolucion nuevaNota = new NotaEvolucion(
                (long) (listaActualizada.size() + 1),
                LocalDateTime.now(), // Se registra con la fecha y hora exacta del momento
                profesional,
                nuevoContenido
        );

        listaActualizada.add(nuevaNota);

        return new Consulta(
                this.id, this.profesionalNombre, this.profesionalRegistro,
                this.usuarioNombre, this.usuarioDocumentoTipo, this.usuarioDocumentoNumero,
                this.fechaAtencion, this.horaInicio, this.horaFin,
                this.datosFormulario, listaActualizada, this.firmaProfesional,
                this.fechaRegistro, LocalDateTime.now()
        );
    }
}
