package com.clinico.mentis.domain.repository;

import com.clinico.mentis.domain.model.Consulta;

import java.util.List;
import java.util.Optional;

public interface ConsultaRepositoryPort {
    Consulta guardar(Consulta consulta);
    Optional<Consulta> obtenerPorId(Long id);
    List<Consulta> buscarPorDocumento(String tipoDocumento, String numeroDocumento);
    List<Consulta> buscarPorNombreCoincidencia(String terminoBusqueda);
}
