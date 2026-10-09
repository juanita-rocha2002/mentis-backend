package com.clinico.mentis.domain.model;

import java.util.List;

public record HistoriaClinicaPaciente(
        String usuarioDocumentoTipo,
        String usuarioDocumentoNumero,
        String usuarioNombre,
        int totalAtenciones,
        List<Consulta> atenciones
){
}
