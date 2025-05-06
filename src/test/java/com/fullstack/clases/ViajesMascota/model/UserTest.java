package com.fullstack.clases.ViajesMascota.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.springframework.hateoas.Link;

public class UserTest {

    private User user;
    private Mascota mascota;

    @BeforeEach
    public void setUp() {
        mascota = new Mascota();
        mascota.setId(1L);
        mascota.setName("Nina");
        mascota.setRaza("Caniche");

        user = new User();
        user.setRol("Cliente");
        user.setCargo(null); // cliente no necesita cargo
        user.setNombre("Ana");
        user.setApellido("Pérez");
        user.setUsername("ana.perez");
        user.setPassword("secret123");
        user.setEmail("ana.perez@example.com");
        user.setMascota(mascota);
    }

    @Test
    public void testDatosUsuario() {
        assertEquals("Ana", user.getNombre());
        assertEquals("Pérez", user.getApellido());
        assertEquals("ana.perez", user.getUsername());
        assertEquals("secret123", user.getPassword());
        assertEquals("ana.perez@example.com", user.getEmail());
    }

    @Test
    public void testMascotaAsignada() {
        assertNotNull(user.getMascota());
        assertEquals("Nina", user.getMascota().getName());
    }

    @Test
    public void testPrePersistAsignaCreatedAt() {
        assertNull(user.getCreated_at());
        user.prePersist();
        assertNotNull(user.getCreated_at());
    }

    @Test
    public void testPrePersistNoSobrescribeCreatedAt() {
        LocalDateTime fecha = LocalDateTime.of(2023, 8, 10, 12, 0);
        user.setCreated_at(fecha);
        user.prePersist();
        assertEquals(fecha, user.getCreated_at());
    }

    @Test
    public void testHateoasLink() {
        Link selfLink = Link.of("http://localhost:8080/users/1").withSelfRel();
        user.add(selfLink);
        Link retrieved = user.getLink("self").orElse(null);
        assertNotNull(retrieved);
        assertEquals("http://localhost:8080/users/1", retrieved.getHref());
    }
}
