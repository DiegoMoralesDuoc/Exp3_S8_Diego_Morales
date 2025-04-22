package com.fullstack.clases.ViajesMascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fullstack.clases.ViajesMascota.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByIdAndRol(Long id, String rol);

}
