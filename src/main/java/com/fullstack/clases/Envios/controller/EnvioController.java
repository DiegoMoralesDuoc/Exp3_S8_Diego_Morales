package com.fullstack.clases.Envios.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<EntityModel<Envio>> createEnvio(@RequestBody Envio envio) {
        logger.info("Creating a new envio with request: {}", envio);
        Envio savedEnvio = envioService.saveEnvio(envio);

        EntityModel<Envio> envioModel = EntityModel.of(savedEnvio,
            linkTo(methodOn(EnvioController.class).getEnvioById(savedEnvio.getId())).withSelfRel(),
            linkTo(methodOn(EnvioController.class).getAllEnvios()).withRel("all-envios")
        );

        return ResponseEntity
            .created(linkTo(methodOn(EnvioController.class).getEnvioById(savedEnvio.getId())).toUri())
            .body(envioModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> getEnvioById(@PathVariable Long id) {
        logger.info("Getting envio by id: {}", id);
        Optional<Envio> envio = envioService.findEnvioById(id);

        return envio.map(e -> {
            EntityModel<Envio> envioModel = EntityModel.of(e,
                linkTo(methodOn(EnvioController.class).getEnvioById(id)).withSelfRel(),
                linkTo(methodOn(EnvioController.class).getAllEnvios()).withRel("all-envios")
            );
            return ResponseEntity.ok(envioModel);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> getAllEnvios() {
        logger.info("Getting all envios");
        List<EntityModel<Envio>> envioModels = envioService.findAllEnvios().stream()
            .map(envio -> EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).getEnvioById(envio.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        CollectionModel<EntityModel<Envio>> collectionModel = CollectionModel.of(envioModels,
            linkTo(methodOn(EnvioController.class).getAllEnvios()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> patchEnvio(@PathVariable Long id, @RequestBody EnvioUpdateRequest updateRequest) {
        logger.info("Updating envio with ID: {} and request: {}", id, updateRequest);
        Envio updatedEnvio = envioService.updateEnvio(id, updateRequest);

        EntityModel<Envio> envioModel = EntityModel.of(updatedEnvio,
            linkTo(methodOn(EnvioController.class).getEnvioById(id)).withSelfRel(),
            linkTo(methodOn(EnvioController.class).getAllEnvios()).withRel("all-envios")
        );

        return ResponseEntity.ok(envioModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        logger.info("Deleting envio by id: {}", id);
        envioService.deleteEnvioById(id);
        return ResponseEntity.noContent().build();
    }
}
