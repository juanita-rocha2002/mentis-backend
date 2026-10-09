package com.clinico.mentis.appication.usecase;

import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.model.HistoriaClinicaPaciente;
import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;

import java.util.Comparator;
import java.util.List;

public class BuscarHistoriaClinicaUseCase {

    private final ConsultaRepositoryPort repositoryPort;

    public BuscarHistoriaClinicaUseCase(ConsultaRepositoryPort repositoryPort){
        this.repositoryPort = repositoryPort;
    }

    public HistoriaClinicaPaciente obtenerHistoriaClinica(String tipoDoc, String numDoc){
        List<Consulta> atenciones = repositoryPort.buscarPorDocumento(tipoDoc, numDoc)
                .stream()
                .sorted(Comparator.comparing(Consulta::fechaAtencion).reversed())
                .toList();

        if (atenciones.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron registros para el paciente especificado.");
        }

        Consulta ultimaConsulta = atenciones.get(0);

        return new HistoriaClinicaPaciente(
            ultimaConsulta.usuarioDocumentoTipo(),
            ultimaConsulta.usuarioDocumentoNumero(),
            ultimaConsulta.usuarioNombre(),
            atenciones.size(),
            atenciones
        );
    }

    public List<Consulta> buscarSugerencias(String query) {
        return repositoryPort.buscarPorNombreCoincidencia(query);
    }
}
