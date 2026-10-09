package com.clinico.mentis.infraestructura.adapter.persistence;

import com.clinico.mentis.infraestructura.adapter.persistence.entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    List<ConsultaEntity> findByUsuarioDocumentoTipoAndUsuarioDocumentoNumero(String tipo, String numero);
    List<ConsultaEntity> findByUsuarioNombreContainingIgnoreCaseOrUsuarioDocumentoNumeroContaining(String nombre, String doc);
}
