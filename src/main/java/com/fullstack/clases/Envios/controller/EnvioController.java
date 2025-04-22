package com.fullstack.clases.Envios.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.clases.Envios.model.Envio;
import com.fullstack.clases.Envios.service.EnvioService;
import com.fullstack.clases.Envios.api.request.EnvioUpdateRequest;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/envio")
@RequiredArgsConstructor

public class EnvioController { 

    private static final Logger logger = LoggerFactory.getLogger(EnvioController.class);

    private final EnvioService envioService;


    @PostMapping()
    public ResponseEntity<Envio> createEnvio(@RequestBody Envio envio) {
        logger.info("Creating a new envio with request: {}", envio);
        Envio savedEnvio = envioService.saveEnvio(envio);
        logger.info("Envio created successfully. User ID: {}", savedEnvio.getId());
        return new ResponseEntity<>(savedEnvio, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> getEnvioById(@PathVariable Long id) {
        logger.info("Getting envio by id: {}", id);
        Optional<Envio> envio = envioService.findEnvioById(id);
        return envio.map(value -> {
            logger.info("Envio found by ID: {}", id);
            return new ResponseEntity<>(value, HttpStatus.OK);
        })
                .orElseGet(() -> {
                    logger.info("Envio not found by ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping("/all")
    public ResponseEntity<List<Envio>> getAllEnvios() {
        logger.info("Getting all envios");
        List<Envio> envios = envioService.findAllEnvios();
        logger.info("Envios found: {}", envios.size());
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Envio> patchEnvio(@PathVariable Long id, @RequestBody EnvioUpdateRequest updateRequest) {
        logger.info("Updating a envio with ID: {} and request: {}", id, updateRequest);
        Envio updatedEnvio = envioService.updateEnvio(id, updateRequest);
        logger.info("Envio updated successfully. Envio ID: {}", updatedEnvio.getId());
        return new ResponseEntity<>(updatedEnvio, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        logger.info("Deleting envio by id: {}", id);
        envioService.deleteEnvioById(id);
        logger.info("Envio deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 

}