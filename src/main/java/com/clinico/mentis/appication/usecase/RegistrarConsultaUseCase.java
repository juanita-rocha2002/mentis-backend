package com.clinico.mentis.appication.usecase;

import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;

public class RegistrarConsultaUseCase {

    private final ConsultaRepositoryPort repositoryPort;

    public RegistrarConsultaUseCase(ConsultaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Consulta ejecutar(Consulta consulta) {
        return repositoryPort.guardar(consulta);
    }
}
