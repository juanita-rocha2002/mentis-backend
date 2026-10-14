package com.clinico.mentis.appication.usecase;

import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;

public class RegistrarConsultaUseCase {

    private final ConsultaRepositoryPort repositoryPort;

    public RegistrarConsultaUseCase(ConsultaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Consulta ejecutar(Consulta consulta) {
        boolean existe = repositoryPort.existePorDocumento(
                consulta.usuarioDocumentoTipo(),
                consulta.usuarioDocumentoNumero()
        );
        if(existe) {
          throw new IllegalArgumentException(
                  "ya existe un paciente registrado con el documento "+
                          consulta.usuarioDocumentoTipo() + " " + consulta.usuarioDocumentoNumero() +
                          ". Use el buscador para agregar evoluciones o revisar su historia clinica."
          );
        }
        return repositoryPort.guardar(consulta);
    }
}
