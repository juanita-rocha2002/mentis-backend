package com.clinico.mentis.infraestructura.adapter.rest;

import com.clinico.mentis.appication.usecase.AgregarNotaManejoUseCase;
import com.clinico.mentis.appication.usecase.BuscarHistoriaClinicaUseCase;
import com.clinico.mentis.appication.usecase.EliminarPacienteUseCase;
import com.clinico.mentis.appication.usecase.RegistrarConsultaUseCase;
import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.model.HistoriaClinicaPaciente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/consultas")
public class ConsultaController {

    private final RegistrarConsultaUseCase registrarConsultaUseCase;
    private final BuscarHistoriaClinicaUseCase buscarHistoriaClinicaUseCase;
    private final AgregarNotaManejoUseCase actualizarNotaManejoUseCase;
    private final EliminarPacienteUseCase eliminarPacienteUseCase;

    public ConsultaController(
            RegistrarConsultaUseCase registrarConsultaUseCase,
            BuscarHistoriaClinicaUseCase buscarHistoriaClinicaUseCase,
            AgregarNotaManejoUseCase actualizarNotaManejoUseCase,
            EliminarPacienteUseCase eliminarPacienteUseCase) {
        this.registrarConsultaUseCase = registrarConsultaUseCase;
        this.buscarHistoriaClinicaUseCase = buscarHistoriaClinicaUseCase;
        this.actualizarNotaManejoUseCase = actualizarNotaManejoUseCase;
        this.eliminarPacienteUseCase = eliminarPacienteUseCase;
    }

    @PostMapping
    public ResponseEntity<Consulta> crearConsulta(@RequestBody Consulta consulta) {
        return ResponseEntity.ok(registrarConsultaUseCase.ejecutar(consulta));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Consulta>> buscar(@RequestParam("q") String query) {
        return ResponseEntity.ok(buscarHistoriaClinicaUseCase.buscarSugerencias(query));
    }

    @GetMapping("/historia-clinica")
    public ResponseEntity<HistoriaClinicaPaciente> obtenerHistoria(
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("numDoc") String numDoc) {
        return ResponseEntity.ok(buscarHistoriaClinicaUseCase.obtenerHistoriaClinica(tipoDoc, numDoc));
    }

    @PatchMapping("/{id}/nota-manejo")
    public ResponseEntity<Consulta> actualizarNota(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {

        String profesional = payload.getOrDefault("profesional", "Profesional por defecto");
        String nuevoTextoNota = payload.get("notaManejo");

        return ResponseEntity.ok(actualizarNotaManejoUseCase.ejecutar(id, profesional, nuevoTextoNota));
    }

    @DeleteMapping("/paciente")
    public ResponseEntity<Void> eliminarPaciente(
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("numDoc") String numDoc) {
        eliminarPacienteUseCase.ejecutar(tipoDoc, numDoc);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

}
