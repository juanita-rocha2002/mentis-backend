package com.clinico.mentis.appication.usecase;

import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;

public class EliminarPacienteUseCase {

    private final ConsultaRepositoryPort repositoryPort;

    public EliminarPacienteUseCase(ConsultaRepositoryPort repositoryPort){
        this.repositoryPort = repositoryPort;
    }

    public void ejecutar(String tipoDoc, String numDoc){
        boolean existe = repositoryPort.existePorDocumento(tipoDoc, numDoc);
        if(!existe) {
            throw new IllegalArgumentException("No se encontro ningun paciente registrado con ese documento.");
        }
        repositoryPort.eliminarPorDocumento(tipoDoc, numDoc);
    }
}
