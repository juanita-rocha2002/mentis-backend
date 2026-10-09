package com.clinico.mentis.infraestructura.adapter.persistence;

import com.clinico.mentis.domain.model.Consulta;
import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;
import com.clinico.mentis.infraestructura.adapter.persistence.entity.ConsultaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ConsultaJpaAdapter implements ConsultaRepositoryPort {

    private final SpringDataConsultaRepository repository;

    public ConsultaJpaAdapter(SpringDataConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Consulta guardar(Consulta consulta) {
        ConsultaEntity entity = ConsultaEntity.fromDomain(consulta);
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Consulta> obtenerPorId(Long id) {
        return repository.findById(id).map(ConsultaEntity::toDomain);
    }

    @Override
    public List<Consulta> buscarPorDocumento(String tipoDocumento, String numeroDocumento) {
        return repository.findByUsuarioDocumentoTipoAndUsuarioDocumentoNumero(tipoDocumento, numeroDocumento)
                .stream().map(ConsultaEntity::toDomain).toList();
    }

    @Override
    public List<Consulta> buscarPorNombreCoincidencia(String terminoBusqueda) {
        return repository.findByUsuarioNombreContainingIgnoreCaseOrUsuarioDocumentoNumeroContaining(terminoBusqueda, terminoBusqueda)
                .stream().map(ConsultaEntity::toDomain).toList();
    }
}