package com.fullstack.clases.Envios.service;

import java.util.List;
import java.util.Optional;

import com.fullstack.clases.Envios.model.Envio;
import com.fullstack.clases.Envios.api.request.EnvioUpdateRequest;

public interface EnvioService {

    Envio saveEnvio(Envio envio);
    Envio updateEnvio(Long id, EnvioUpdateRequest updateRequest);
    Optional<Envio> findEnvioById(Long id);
    List<Envio> findAllEnvios();
    void deleteEnvioById(Long id);

}