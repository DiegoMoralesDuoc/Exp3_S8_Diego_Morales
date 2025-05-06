package com.fullstack.clases.ViajesMascota.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.fullstack.clases.ViajesMascota.model.Mascota;
import com.fullstack.clases.ViajesMascota.service.Mascota.MascotaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MascotaControllerUnitTest {

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaController mascotaController;

    private Mascota testMascota;

    @BeforeEach
    void setup() {
        testMascota = new Mascota();
        testMascota.setId(1L);
        testMascota.setName("Firulais");
    }

    @Test
    void testGetMascotaById_MascotaFound() {
        when(mascotaService.findMascotaById(1L)).thenReturn(Optional.of(testMascota));

        ResponseEntity<EntityModel<Mascota>> response = mascotaController.getMascotaById(1L);

        assertNotNull(response, "Response should not be null");

        EntityModel<Mascota> entityModel = response.getBody();
        assertNotNull(entityModel, "The response body should not be null");

        Mascota mascota = entityModel.getContent();
        assertNotNull(mascota, "The mascota content should not be null");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, mascota.getId());
        assertEquals("Firulais", mascota.getName());

        assertTrue(entityModel.getLinks().hasLink("self"));
        assertTrue(entityModel.getLinks().hasLink("delete"));
        assertTrue(entityModel.getLinks().hasLink("update"));
        assertTrue(entityModel.getLinks().hasLink("all-mascotas"));
    }

    @Test
    void testGetMascotaById_MascotaNotFound() {
        when(mascotaService.findMascotaById(99L)).thenReturn(Optional.empty());

        ResponseEntity<EntityModel<Mascota>> response = mascotaController.getMascotaById(99L);

        assertNull(response.getBody(), "The response body should be null when mascota is not found");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}