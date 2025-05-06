package com.fullstack.clases.ViajesMascota.service.Viaje;

import java.util.List;
import java.util.Optional;

import com.fullstack.clases.ViajesMascota.api.request.ViajesMascotaCreateRequest;
import com.fullstack.clases.ViajesMascota.model.Viaje;

public interface ViajeService {

    Viaje saveViaje(ViajesMascotaCreateRequest viajeRequest);
    Optional<Viaje> findViajeById(Long id);
    List<Viaje> findAllViajes();
    void deleteViajeById(Long id);

}
