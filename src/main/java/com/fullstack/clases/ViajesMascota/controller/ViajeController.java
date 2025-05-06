package com.fullstack.clases.ViajesMascota.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public ResponseEntity<EntityModel<Viaje>> createViaje(@RequestBody ViajesMascotaCreateRequest viajeRequest) {
        logger.info("Creating a new viaje with request: {}", viajeRequest);
    
        Viaje savedViaje = viajeService.saveViaje(viajeRequest);
        
        EntityModel<Viaje> viajeResource = EntityModel.of(savedViaje,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ViajeController.class).getViajeById(savedViaje.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ViajeController.class).getAllViajes()).withRel("all-viajes")
        );
        
        if (viajeResource.getContent() != null) {
            logger.info("Viaje created successfully. Viaje ID: {}", viajeResource.getContent().getId());
        } else {
            logger.error("Failed to create viaje. Content is null.");
        }
        
        return new ResponseEntity<>(viajeResource, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Viaje>> getViajeById(@PathVariable Long id) {
        logger.info("Getting viaje by id: {}", id);
          Optional<Viaje> viaje = viajeService.findViajeById(id);
        if (viaje.isPresent()) {
            logger.info("Viaje found: id={}", viaje.get().getId());
            EntityModel<Viaje> viajeResource = EntityModel.of(viaje.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ViajeController.class).getViajeById(viaje.get().getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ViajeController.class).getAllViajes()).withRel("all-viajes")
            );
            return new ResponseEntity<>(viajeResource, HttpStatus.OK);
        } else {
            logger.info("Viaje not found by id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EntityModel<Viaje>>> getAllViajes() {
        logger.info("Getting all viajes");
        List<Viaje> viajes = viajeService.findAllViajes();
        logger.info("Viajes found: {}", viajes.size());

        List<EntityModel<Viaje>> viajeResources = viajes.stream()
            .map(viaje -> EntityModel.of(viaje,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ViajeController.class).getViajeById(viaje.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ViajeController.class).getAllViajes()).withRel("all-viajes")
            ))
            .collect(Collectors.toList());

        return new ResponseEntity<>(viajeResources, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        logger.info("Deleting viaje by id: {}", id);
        viajeService.deleteViajeById(id);
        logger.info("Viaje deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
