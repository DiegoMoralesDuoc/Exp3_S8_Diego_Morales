package com.fullstack.clases.ViajesMascota.service.Mascota;

import java.util.List;
import java.util.Optional;

import com.fullstack.clases.ViajesMascota.api.request.MascotaUpdateRequest;
import com.fullstack.clases.ViajesMascota.model.Mascota;

public interface MascotaService {

    Mascota saveMascota(Mascota mascota);
    Optional<Mascota> findMascotaById(Long id);
    Mascota updateMascota(Long id, MascotaUpdateRequest updateRequest);
    List<Mascota> findAllMascotas();
    void deleteMascotaById(Long id);

}
