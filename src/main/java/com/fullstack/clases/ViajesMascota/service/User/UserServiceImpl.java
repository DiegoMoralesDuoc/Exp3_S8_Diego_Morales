package com.fullstack.clases.ViajesMascota.service.User;

import java.time.LocalDateTime;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fullstack.clases.ViajesMascota.api.request.UserUpdateRequest;
import com.fullstack.clases.ViajesMascota.exceptionhandler.ResourceNotFoundException;
import com.fullstack.clases.ViajesMascota.model.Mascota;
import com.fullstack.clases.ViajesMascota.model.User;
import com.fullstack.clases.ViajesMascota.repository.UserRepository;
import com.fullstack.clases.ViajesMascota.repository.MascotaRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final MascotaRepository mascotaRepository;

    /**
     * Guarda un usuario nuevo, generando su username automáticamente.
     * @param userToSave datos del usuario entrante
     * @return usuario persistido
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User saveUser(@Valid User userToSave) {

        // Validar nombre y apellido
        if (userToSave.getNombre() == null || userToSave.getNombre().isBlank() ||
            userToSave.getApellido() == null || userToSave.getApellido().isBlank()) {
            throw new IllegalArgumentException("Nombre y Apellido no pueden ser vacíos");
        }

        // Generar y asignar username
        String username = generarUsername(userToSave.getNombre(), userToSave.getApellido());
        userToSave.setUsername(username);

        // Manejo seguro de mascota 
        if (userToSave.getMascota() != null) {
            Long mascotaId = userToSave.getMascota().getId();
            if (mascotaId != null) {
                Mascota mascota = mascotaRepository.findById(mascotaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + mascotaId));
                userToSave.setMascota(mascota);
            } else {
                userToSave.setMascota(null);
            }
        }

        // Fecha de creación si no existe
        if (userToSave.getCreated_at() == null) {
            userToSave.setCreated_at(LocalDateTime.now());
        }

        // Persistir y devolver
        User saved = userRepository.save(userToSave);
        logger.info("Usuario creado (ID={}): {}", saved.getId(), saved.getUsername());
        return saved;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findClienteById(Long id) {
        return userRepository.findByIdAndRol(id, "CLIENTE");
    }

    @Override
    public Optional<User> findTrabajadorById(Long id) {
        return userRepository.findByIdAndRol(id, "TRABAJADOR");
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByMascotaId(Long mascotaId) {
        Mascota m = mascotaRepository.findById(mascotaId)
            .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + mascotaId));
        return m.getUser();
    }

    @Override
    public User updateUser(Long id, UserUpdateRequest req) {
        User u = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (req.getPassword() != null) u.setPassword(req.getPassword());
        if (req.getCargo()    != null) u.setCargo(req.getCargo());
        if (req.getEmail()    != null) u.setEmail(req.getEmail());
        if (req.getMascotaId() > 0) {
            Mascota mascota = mascotaRepository.findById((long) req.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
            u.setMascota(mascota);
        }

        return userRepository.save(u);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Genera un username base a partir de nombre y apellido,
     * elimina espacios, acentos y caracteres inválidos,
     * y asegura unicidad.
     */
    private String generarUsername(String nombre, String apellido) {
        // Base
        String base = (nombre.substring(0,1) + apellido)
            .toLowerCase()
            .replaceAll("\\s+", "")
            .replaceAll("[^a-z0-9._-]", "");

        // Normalizar sin acentos
        base = Normalizer.normalize(base, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "");

        String username = base;
        int i = 1;
        while (userRepository.existsByUsername(username)) {
            username = base + i++;
        }
        return username;
    }
}