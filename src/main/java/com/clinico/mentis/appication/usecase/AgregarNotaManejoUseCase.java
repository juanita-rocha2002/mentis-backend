package com.clinico.mentis.appication.usecase;

import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;

public class AgregarNotaManejoUseCase {

    private final ConsultaRepositoryPort repositoryPort;

    public AgregarNotaManejoUseCase(ConsultaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Consulta ejecutar(Long idConsulta, String profesional, String nuevoTextoNota) {
        Consulta consultaExistente = repositoryPort.obtenerPorId(idConsulta)
                .orElseThrow(() -> new IllegalArgumentException("Consulta no encontrada con ID: " + idConsulta));

        // Agrega la nueva nota preservando todo el historial anterior
        Consulta consultaActualizada = consultaExistente.agregarNuevaNotaManejo(profesional, nuevoTextoNota);

        return repositoryPort.guardar(consultaActualizada);
    }
}
