package com.fullstack.clases.ViajesMascota.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.fullstack.clases.ViajesMascota.model.User;
import com.fullstack.clases.ViajesMascota.service.Mascota.MascotaService;
import com.fullstack.clases.ViajesMascota.service.User.UserService;

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
public class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("juan");
    }

    @Test
    void testGetUserById_UserFound() {
        when(userService.findUserById(1L)).thenReturn(Optional.of(testUser));
    
        ResponseEntity<EntityModel<User>> response = userController.getUserById(1L);
    
        // Verificación de que respuesta no sea null
        assertNotNull(response, "Response should not be null");
    
        // Obtención del cuerpo de la respuesta de forma segura
        EntityModel<User> entityModel = response.getBody();
        
        // Verificación de que cuerpo no sea null
        assertNotNull(entityModel, "The response body should not be null");
    
        // Verificación de que contenido no sea null
        User user = entityModel.getContent();
        assertNotNull(user, "The user content should not be null");
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, user.getId());
        assertTrue(entityModel.getLinks().hasLink("self"));
        assertTrue(entityModel.getLinks().hasLink("delete"));
        assertTrue(entityModel.getLinks().hasLink("update"));
        assertTrue(entityModel.getLinks().hasLink("all-users"));
    }
    
    @Test
    void testGetUserById_UserNotFound() {
        when(userService.findUserById(2L)).thenReturn(Optional.empty());
    
        ResponseEntity<EntityModel<User>> response = userController.getUserById(2L);
    
        // Verificar que el cuerpo sea null cuando no se encuentra el usuario
        assertNull(response.getBody(), "The response body should be null when the user is not found");
    
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    
}
