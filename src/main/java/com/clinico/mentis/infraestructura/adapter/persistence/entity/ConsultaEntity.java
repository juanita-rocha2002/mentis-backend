package com.clinico.mentis.infraestructura.adapter.persistence.entity;

import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.model.NotaEvolucion;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "consultas_psicologicas")
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profesional_nombre", nullable = false)
    private String profesionalNombre;

    @Column(name = "profesional_registro", nullable = false)
    private String profesionalRegistro;

    @Column(name = "usuario_nombre", nullable = false)
    private String usuarioNombre;

    @Column(name = "usuario_documento_tipo", nullable = false)
    private String usuarioDocumentoTipo;

    @Column(name = "usuario_documento_numero", nullable = false)
    private String usuarioDocumentoNumero;

    @Column(name = "fecha_atencion", nullable = false)
    private LocalDate fechaAtencion;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    // Secciones extensas del formulario mapeadas como JSONB
    @Type(JsonType.class)
    @Column(name = "datos_formulario", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> datosFormulario;

    // Historial inmutable de evoluciones / notas de manejo mapeado como JSONB
    @Type(JsonType.class)
    @Column(name = "evoluciones", columnDefinition = "jsonb", nullable = false)
    private List<NotaEvolucion> evoluciones = new ArrayList<>();

    @Column(name = "firma_profesional")
    private String firmaProfesional;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }

    // ==========================================
    // MÉTODOS DE CONVERSIÓN (DOMINIO <-> ENTITY)
    // ==========================================

    public static ConsultaEntity fromDomain(Consulta model) {
        ConsultaEntity entity = new ConsultaEntity();
        entity.setId(model.id());
        entity.setProfesionalNombre(model.profesionalNombre());
        entity.setProfesionalRegistro(model.profesionalRegistro());
        entity.setUsuarioNombre(model.usuarioNombre());
        entity.setUsuarioDocumentoTipo(model.usuarioDocumentoTipo());
        entity.setUsuarioDocumentoNumero(model.usuarioDocumentoNumero());
        entity.setFechaAtencion(model.fechaAtencion());
        entity.setHoraInicio(model.horaInicio());
        entity.setHoraFin(model.horaFin());
        entity.setDatosFormulario(model.datosFormulario());
        entity.setEvoluciones(model.evoluciones() != null ? model.evoluciones() : new ArrayList<>());
        entity.setFirmaProfesional(model.firmaProfesional());
        return entity;
    }

    public Consulta toDomain() {
        return new Consulta(
                this.id,
                this.profesionalNombre,
                this.profesionalRegistro,
                this.usuarioNombre,
                this.usuarioDocumentoTipo,
                this.usuarioDocumentoNumero,
                this.fechaAtencion,
                this.horaInicio,
                this.horaFin,
                this.datosFormulario,
                this.evoluciones != null ? this.evoluciones : new ArrayList<>(),
                this.firmaProfesional,
                this.fechaRegistro,
                this.ultimaActualizacion
        );
    }
}