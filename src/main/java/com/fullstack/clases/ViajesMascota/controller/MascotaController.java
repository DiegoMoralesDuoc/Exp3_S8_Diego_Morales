package com.fullstack.clases.ViajesMascota.controller;

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

import com.fullstack.clases.ViajesMascota.api.request.MascotaUpdateRequest;
import com.fullstack.clases.ViajesMascota.model.Mascota;
import com.fullstack.clases.ViajesMascota.service.Mascota.MascotaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mascota")
@RequiredArgsConstructor
public class MascotaController {
    
    private static final Logger logger = LoggerFactory.getLogger(MascotaController.class);

    private final MascotaService mascotaService;

    @PostMapping()
    public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota) {
        logger.info("Creating a new mascota with request: {}", mascota);
        Mascota savedMascota = mascotaService.saveMascota(mascota);
        logger.info("Mascota created successfully. Mascota ID: {}", savedMascota.getId());
        return new ResponseEntity<>(savedMascota, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        logger.info("Getting a mascota by ID: {}", id);
        Optional<Mascota> mascota = mascotaService.findMascotaById(id);
        return mascota.map(value -> {
            logger.info("Mascota found by ID: {}", id);
            return new ResponseEntity<>(value, HttpStatus.OK);
        })
                .orElseGet(() -> {
                    logger.info("Mascota not found by ID: {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }


    @GetMapping("/all")
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        logger.info("Getting all mascotas");
        List<Mascota> mascotas = mascotaService.findAllMascotas();
        logger.info("Found {} mascotas", mascotas.size());
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mascota> patchMascota(@PathVariable Long id, @RequestBody MascotaUpdateRequest updateRequest) {
        logger.info("Updating a mascota with ID: {} and request: {}", id, updateRequest);
        Mascota updatedMascota = mascotaService.updateMascota(id, updateRequest);
        logger.info("Mascota updated successfully. Mascota ID: {}", updatedMascota.getId());
        return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        logger.info("Deleting a mascota with ID: {}", id);
        mascotaService.deleteMascotaById(id);
        logger.info("Mascota deleted successfully. Mascota ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
