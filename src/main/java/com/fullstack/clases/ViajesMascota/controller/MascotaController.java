package com.fullstack.clases.ViajesMascota.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<EntityModel<Mascota>> createMascota(@RequestBody Mascota mascota) {
        logger.info("Creating a new mascota with request: {}", mascota);
        Mascota savedMascota = mascotaService.saveMascota(mascota);
        logger.info("Mascota created successfully. Mascota ID: {}", savedMascota.getId());

        EntityModel<Mascota> mascotaResource = EntityModel.of(savedMascota,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getMascotaById(savedMascota.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getAllMascotas()).withRel("all-mascotas")
        );

        return new ResponseEntity<>(mascotaResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Mascota>> getMascotaById(@PathVariable Long id) {
        logger.info("Getting a mascota by ID: {}", id);
        Optional<Mascota> mascota = mascotaService.findMascotaById(id);

        return mascota.map(value -> {
            EntityModel<Mascota> mascotaResource = EntityModel.of(value,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getMascotaById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getAllMascotas()).withRel("all-mascotas"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).deleteMascota(id)).withRel("delete"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).patchMascota(id, null)).withRel("update")
            );
            return new ResponseEntity<>(mascotaResource, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<Mascota>>> getAllMascotas() {
        logger.info("Getting all mascotas");
        List<Mascota> mascotas = mascotaService.findAllMascotas();
        List<EntityModel<Mascota>> mascotaResources = mascotas.stream().map(mascota ->
            EntityModel.of(mascota,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getMascotaById(mascota.getId())).withSelfRel()
            )
        ).collect(Collectors.toList());

        CollectionModel<EntityModel<Mascota>> collectionModel = CollectionModel.of(
            mascotaResources,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getAllMascotas()).withSelfRel()
        );

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Mascota>> patchMascota(@PathVariable Long id, @RequestBody MascotaUpdateRequest updateRequest) {
        logger.info("Updating a mascota with ID: {} and request: {}", id, updateRequest);
        Mascota updatedMascota = mascotaService.updateMascota(id, updateRequest);
        logger.info("Mascota updated successfully. Mascota ID: {}", updatedMascota.getId());

        EntityModel<Mascota> mascotaResource = EntityModel.of(updatedMascota,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getMascotaById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MascotaController.class).getAllMascotas()).withRel("all-mascotas")
        );

        return new ResponseEntity<>(mascotaResource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        logger.info("Deleting a mascota with ID: {}", id);
        mascotaService.deleteMascotaById(id);
        logger.info("Mascota deleted successfully. Mascota ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}