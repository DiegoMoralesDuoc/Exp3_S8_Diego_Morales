package com.fullstack.clases.Envios.controller;

import com.fullstack.clases.Envios.model.Envio;
import com.fullstack.clases.Envios.service.EnvioService;
import com.fullstack.clases.Envios.api.request.EnvioUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

public class EnvioControllerTest {

    @Mock
    private EnvioService envioService;

    @InjectMocks
    private EnvioController envioController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(envioController).build();
    }

    //Verificar que se pueda crear un envío
    @Test
    public void testCreateEnvio() throws Exception {
        Envio envio = Envio.builder().id(1L).remitente("Test Remitente").destinatario("Test Destinatario").build();
        when(envioService.saveEnvio(any(Envio.class))).thenReturn(envio);

        mockMvc.perform(post("/api/envio")
                .contentType("application/json")
                .content("{ \"remitente\": \"Test Remitente\", \"destinatario\": \"Test Destinatario\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.remitente").value("Test Remitente"))
                .andExpect(jsonPath("$.destinatario").value("Test Destinatario"));

        verify(envioService, times(1)).saveEnvio(any(Envio.class));
    }

    //Verificar que se pueda obtener envio por ID
    @Test
    public void testGetEnvioById() throws Exception {
        Envio envio = Envio.builder().id(1L).remitente("Test Remitente").destinatario("Test Destinatario").build();
        when(envioService.findEnvioById(1L)).thenReturn(Optional.of(envio));

        mockMvc.perform(get("/api/envio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.remitente").value("Test Remitente"))
                .andExpect(jsonPath("$.destinatario").value("Test Destinatario"));

        verify(envioService, times(1)).findEnvioById(1L);
    }

    //Verificar cuando no se encuentra un envio por ID
    @Test
    public void testGetEnvioByIdNotFound() throws Exception {
        when(envioService.findEnvioById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/envio/1"))
                .andExpect(status().isNotFound());

        verify(envioService, times(1)).findEnvioById(1L);
    }

    //Verificar que se puede obtener todos los envios
    @Test
    public void testGetAllEnvios() throws Exception {
        Envio envio1 = Envio.builder().id(1L).remitente("Test Remitente 1").destinatario("Test Destinatario 1").build();
        Envio envio2 = Envio.builder().id(2L).remitente("Test Remitente 2").destinatario("Test Destinatario 2").build();

        when(envioService.findAllEnvios()).thenReturn(List.of(envio1, envio2));

        mockMvc.perform(get("/api/envio/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))  // Acceder a 'content' y luego al primer elemento
                .andExpect(jsonPath("$.content[1].id").value(2));  // Acceder a 'content' y luego al segundo elemento

        verify(envioService, times(1)).findAllEnvios();
    }

    //Verificar que se pueda actualizar usando el método Patch
    @Test
    public void testPatchEnvio() throws Exception {
        Envio envio = Envio.builder().id(1L).remitente("Test Remitente").destinatario("Test Destinatario")
                        .estado("En Proceso").build();  // Aquí ya asignamos el estado actualizado
        EnvioUpdateRequest updateRequest = new EnvioUpdateRequest();
        updateRequest.setEstado("En Proceso");

        when(envioService.updateEnvio(1L, updateRequest)).thenReturn(envio);

        mockMvc.perform(patch("/api/envio/1")
                    .contentType("application/json")
                    .content("{ \"estado\": \"En Proceso\" }"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.estado").value("En Proceso"));  // Ahora debería coincidir

        verify(envioService, times(1)).updateEnvio(eq(1L), any(EnvioUpdateRequest.class));
    }

    //Verificar que se pueda eliminar un envío
    @Test
    public void testDeleteEnvio() throws Exception {
        doNothing().when(envioService).deleteEnvioById(1L);

        mockMvc.perform(delete("/api/envio/1"))
                .andExpect(status().isNoContent());

        verify(envioService, times(1)).deleteEnvioById(1L);
    }
}
