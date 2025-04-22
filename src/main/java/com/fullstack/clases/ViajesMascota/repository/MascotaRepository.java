package com.fullstack.clases.ViajesMascota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fullstack.clases.ViajesMascota.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long>{
    


}

