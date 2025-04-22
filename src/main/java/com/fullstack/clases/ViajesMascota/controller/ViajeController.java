package com.fullstack.clases.ViajesMascota.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.fullstack.clases.ViajesMascota.model.Viaje;
import com.fullstack.clases.ViajesMascota.service.Viaje.ViajeService;
import com.fullstack.clases.ViajesMascota.api.request.ViajesMascotaCreateRequest;

@RestController
@RequestMapping("/api/viaje")
@RequiredArgsConstructor
public class ViajeController {

    private static final Logger logger = LoggerFactory.getLogger(ViajeController.class);

    private final ViajeService viajeService;

    @PostMapping()
    public ResponseEntity<Viaje> createViaje(@RequestBody ViajesMascotaCreateRequest viajeRequest) {
        logger.info("Creating a new viaje with request: {}", viajeRequest);
        Viaje savedViaje = viajeService.saveViaje(viajeRequest);
        logger.info("Viaje created successfully. Viaje ID: {}", savedViaje.getId());
        return new ResponseEntity<>(savedViaje, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViajeById(@PathVariable Long id) {
        logger.info("Getting viaje by id: {}", id);
        Optional<Viaje> viaje = viajeService.findViajeById(id);
        if (viaje.isPresent()) {
            logger.info("Viaje found: id={}", viaje.get().getId());
            return new ResponseEntity<>(viaje.get(), HttpStatus.OK);
        } else {
            logger.info("Viaje not found by id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Viaje>> getAllViajes() {
        logger.info("Getting all viajes");
        List<Viaje> viajes = viajeService.findAllViajes();
        logger.info("Viajes found: {}", viajes.size());
        return new ResponseEntity<>(viajes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        logger.info("Deleting viaje by id: {}", id);
        viajeService.deleteViajeById(id);
        logger.info("Viaje deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
