package com.fullstack.clases.ViajesMascota.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.fullstack.clases.ViajesMascota.api.request.UserUpdateRequest;
import com.fullstack.clases.ViajesMascota.service.Mascota.MascotaService;
import com.fullstack.clases.ViajesMascota.service.User.UserService;
import com.fullstack.clases.ViajesMascota.model.Mascota;
import com.fullstack.clases.ViajesMascota.model.User;
import com.fullstack.clases.ViajesMascota.exceptionhandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final MascotaService mascotaService;

    @PostMapping()
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        logger.info("Creating a new user with request: {}", user);

        if (user.getMascota() != null && user.getMascota().getId() != null) {
            Long mascotaId = user.getMascota().getId();
            Mascota mascota = mascotaService.findMascotaById(mascotaId)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + mascotaId));
            user.setMascota(mascota);
        }

        User savedUser = userService.saveUser(user);
        logger.info("User created successfully. User ID: {}", savedUser.getId());

        EntityModel<User> userResource = EntityModel.of(savedUser,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(savedUser.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("all-users")
        );

        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable Long id) {
        logger.info("Getting a user by ID: {}", id);
        Optional<User> user = userService.findUserById(id);

        return user.map(value -> {
            EntityModel<User> userResource = EntityModel.of(value,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("all-users"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(id)).withRel("delete"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).patchUser(id, null)).withRel("update")
            );
            return new ResponseEntity<>(userResource, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/mascota/{mascotaId}")
    public ResponseEntity<EntityModel<User>> getUserByMascotaId(@PathVariable Long mascotaId) {
        logger.info("Getting a user by Mascota ID: {}", mascotaId);
        try {
            User user = userService.findUserByMascotaId(mascotaId);
            EntityModel<User> userResource = EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserByMascotaId(mascotaId)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getId())).withRel("user-by-id")
            );
            return new ResponseEntity<>(userResource, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.warn("User not found for Mascota ID: {}", mascotaId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error retrieving user by Mascota ID: {}", mascotaId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<EntityModel<User>> getUserByUsername(@PathVariable String username) {
        logger.info("Getting a user by username: {}", username);
        User user = userService.findUserByUsername(username);
        if (user != null) {
            EntityModel<User> userResource = EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserByUsername(username)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getId())).withRel("user-by-id")
            );
            return new ResponseEntity<>(userResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<User>>> getAllUsers() {
        logger.info("Getting all users");
        List<User> users = userService.findAllUsers();
        List<EntityModel<User>> userResources = users.stream().map(user ->
            EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getId())).withSelfRel()
            )
        ).collect(Collectors.toList());

        CollectionModel<EntityModel<User>> collectionModel = CollectionModel.of(
            userResources,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withSelfRel()
        );

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<User>> patchUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateRequest) {
        logger.info("Updating a user with ID: {} and request: {}", id, updateRequest);
        User updatedUser = userService.updateUser(id, updateRequest);
        EntityModel<User> userResource = EntityModel.of(updatedUser,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("all-users")
        );
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting a user with ID: {}", id);
        userService.deleteUserById(id);
        logger.info("User deleted successfully. User ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
