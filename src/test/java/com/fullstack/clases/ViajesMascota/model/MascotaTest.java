package com.fullstack.clases.ViajesMascota.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.springframework.hateoas.Link;

public class MascotaTest {

    private Mascota mascota;

    @BeforeEach
    public void setUp() {
        mascota = new Mascota();
        mascota.setName("Firulais");
        mascota.setRaza("Labrador");
    }

    @Test
    public void testNombreYraza() {
        assertEquals("Firulais", mascota.getName());
        assertEquals("Labrador", mascota.getRaza());
    }

    @Test
    public void testPrePersistAsignaCreatedAtSiEsNull() {
        assertNull(mascota.getCreated_at());
        mascota.prePersist();
        assertNotNull(mascota.getCreated_at());
    }

    @Test
    public void testPrePersistNoSobrescribeCreatedAtSiYaTieneValor() {
        LocalDateTime fechaCreacion = LocalDateTime.of(2024, 5, 5, 12, 0);
        mascota.setCreated_at(fechaCreacion);
        mascota.prePersist();
        assertEquals(fechaCreacion, mascota.getCreated_at());
    }

    @Test
    public void testHateoasLink() {
        Link selfLink = Link.of("http://localhost:8080/mascotas/1").withSelfRel();
        mascota.add(selfLink);

        Link retrievedLink = mascota.getLink("self").orElse(null);

        assertNotNull(retrievedLink);
        assertEquals("http://localhost:8080/mascotas/1", retrievedLink.getHref());
        assertEquals("self", retrievedLink.getRel().value());
    }
}