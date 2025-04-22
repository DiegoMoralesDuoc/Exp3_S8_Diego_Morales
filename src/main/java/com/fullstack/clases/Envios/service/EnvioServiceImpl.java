package com.fullstack.clases.Envios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fullstack.clases.Envios.api.request.EnvioUpdateRequest;
import com.fullstack.clases.Envios.model.Envio;
import com.fullstack.clases.Envios.repository.EnvioRepository;
import com.fullstack.clases.Envios.exceptionhandler.DatabaseTransactionException;
import com.fullstack.clases.Envios.exceptionhandler.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class EnvioServiceImpl implements EnvioService{

    private static final Logger logger = LoggerFactory.getLogger(EnvioServiceImpl.class);
    private final EnvioRepository envioRepository;

    @Override
        public Envio saveEnvio(Envio envioToSave) {
        logger.info("Creando envio con request: {} - metodo saveEnvio",envioToSave);
        try {
            Envio savedEnvio = envioRepository.save(envioToSave);
            logger.info("Envio creado satisfactoriamente. Envio ID: {} - metodo saveEnvio",savedEnvio.getId());
            return savedEnvio;
        } catch (Exception e) {
            logger.error("Error creando envio - metodo saveEnvio",e);
            throw new DatabaseTransactionException("Error creando envio",e);
        }
    }

    @Override
    public Envio updateEnvio(Long id, EnvioUpdateRequest updateRequest) {
        logger.info("Updating envio with ID: {} and request: {} - method updateEnvio", id, updateRequest);
        Envio envio  = envioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envio not found"));

        if (updateRequest.getEstado() != null) {
            logger.info("Updating estado to: {} - method updateEnvio", updateRequest.getEstado());
            envio.setEstado(updateRequest.getEstado());
        }
        if (updateRequest.getUbicacion_actual() != null) {
            logger.info("Updating ubicacion_actual to: {} - method updateEnvio", updateRequest.getUbicacion_actual());
            envio.setUbicacion_actual(updateRequest.getUbicacion_actual());
        }

        if (updateRequest.getUltima_actualizacion() != null) {
            logger.info("Updating Ultima Actualizacion to: {} - method updateEnvio", updateRequest.getUltima_actualizacion());
            envio.setUltima_actualizacion(LocalDateTime.now());
        }

        logger.info("Saving envio - method updateEnvio");
        Envio updatedEnvio = envioRepository.save(envio);
        logger.info("Envio updated successfully. Envio ID: {}", updatedEnvio.getId());
        return updatedEnvio;

    }

    @Override
    public Optional<Envio> findEnvioById(Long id) {
        logger.debug("Getting envio by id: {} - method findEnvioById", id);
        return envioRepository.findById(id);
    }

    @Override
    public List<Envio> findAllEnvios() {
        logger.debug("Finding all envios - method findAllEnvios");
        return envioRepository.findAll();
    }

    @Override
    public void deleteEnvioById(Long id) {
        logger.info("Deleting envio by ID: {} - method deleteEnvioById", id);
        Optional<Envio> envio = envioRepository.findById(id);
        if (envio.isEmpty()) {
            logger.info("Envio not found - method deleteEnvioById");
            throw new ResourceNotFoundException("Envio not found");
        }
        logger.info("Deleting envio - method deleteEnvioById");
        envioRepository.deleteById(id);
        logger.info("Envio deleted successfully. Envio ID: {} - method deleteEnvioById", id);
    }

}
    

