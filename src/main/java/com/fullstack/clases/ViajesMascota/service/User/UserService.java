package com.fullstack.clases.ViajesMascota.service.User;

import java.util.List;
import java.util.Optional;

import com.fullstack.clases.ViajesMascota.api.request.UserUpdateRequest;
import com.fullstack.clases.ViajesMascota.model.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> findUserById(Long id);
    Optional<User> findClienteById(Long id);
    Optional<User> findTrabajadorById(Long id);
    User findUserByMascotaId(Long mascotaId);
    User findUserByUsername(String username);
    User updateUser(Long id, UserUpdateRequest updateRequest);
    List<User> findAllUsers();
    void deleteUserById(Long id);

}