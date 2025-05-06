package com.fullstack.clases.ViajesMascota.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ViajeTest {

    private Viaje viaje;
    private User cliente;
    private User trabajador;
    private Mascota mascota;

    @BeforeEach
    public void setUp() {
        cliente = new User();
        cliente.setId(1L);
        cliente.setUsername("clienteTest");

        trabajador = new User();
        trabajador.setId(2L);
        trabajador.setUsername("trabajadorTest");

        mascota = new Mascota();
        mascota.setId(1L);
        mascota.setName("Pelusa");
        mascota.setRaza("Persa");

        viaje = Viaje.builder()
                .cliente(cliente)
                .trabajador(trabajador)
                .mascota(mascota)
                .origen("Ciudad A")
                .destino("Ciudad B")
                .tipo_transporte("Auto")
                .fecha_viaje(LocalDate.of(2025, 5, 20))
                .build();
    }

    @Test
    public void testViajeDatosBasicos() {
        assertEquals("Ciudad A", viaje.getOrigen());
        assertEquals("Ciudad B", viaje.getDestino());
        assertEquals("Auto", viaje.getTipo_transporte());
        assertEquals(LocalDate.of(2025, 5, 20), viaje.getFecha_viaje());
    }

    @Test
    public void testRelacionesConUsuarioYMascota() {
        assertEquals("clienteTest", viaje.getCliente().getUsername());
        assertEquals("trabajadorTest", viaje.getTrabajador().getUsername());
        assertEquals("Pelusa", viaje.getMascota().getName());
    }

    @Test
    public void testPrePersistAsignaCreatedAt() {
        assertNull(viaje.getCreated_at());
        viaje.prePersist();
        assertNotNull(viaje.getCreated_at());
    }

    @Test
    public void testPrePersistNoSobrescribeCreatedAt() {
        LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
        viaje.setCreated_at(fixedDate);
        viaje.prePersist();
        assertEquals(fixedDate, viaje.getCreated_at());
    }
}