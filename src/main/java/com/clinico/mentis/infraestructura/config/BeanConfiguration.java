package com.clinico.mentis.infraestructura.config;

import com.clinico.mentis.appication.usecase.AgregarNotaManejoUseCase;
import com.clinico.mentis.appication.usecase.BuscarHistoriaClinicaUseCase;
import com.clinico.mentis.appication.usecase.RegistrarConsultaUseCase;
import com.clinico.mentis.domain.repository.ConsultaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RegistrarConsultaUseCase registrarConsultaUseCase(ConsultaRepositoryPort port) {
        return new RegistrarConsultaUseCase(port);
    }

    @Bean
    public BuscarHistoriaClinicaUseCase buscarHistoriaClinicaUseCase(ConsultaRepositoryPort port) {
        return new BuscarHistoriaClinicaUseCase(port);
    }

    @Bean
    public AgregarNotaManejoUseCase actualizarNotaManejoUseCase(ConsultaRepositoryPort port) {
        return new AgregarNotaManejoUseCase(port);
    }
}
