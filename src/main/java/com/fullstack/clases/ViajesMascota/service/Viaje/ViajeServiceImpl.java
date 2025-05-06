package com.fullstack.clases.ViajesMascota.service.Viaje;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fullstack.clases.ViajesMascota.api.request.ViajesMascotaCreateRequest;
import com.fullstack.clases.ViajesMascota.model.Viaje;
import com.fullstack.clases.ViajesMascota.repository.ViajeRepository;
import com.fullstack.clases.ViajesMascota.service.Mascota.MascotaService;
import com.fullstack.clases.ViajesMascota.service.User.UserService;
import com.fullstack.clases.ViajesMascota.exceptionhandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViajeServiceImpl implements ViajeService {

    private static final Logger logger = LoggerFactory.getLogger(ViajeServiceImpl.class);

    private final ViajeRepository viajeRepository;
    private final UserService userService;
    private final MascotaService mascotaService;

    @Override
    @Transactional
    public Viaje saveViaje(ViajesMascotaCreateRequest viajeRequest) {
        logger.debug("Saving viaje with request: {} - method saveViaje", viajeRequest);
        Long userClienteId = viajeRequest.getUserClienteId();
        Long userTrabajadorId = viajeRequest.getUserTrabajadorId();
        Long mascotaId = viajeRequest.getMascotaId();

        if (userClienteId != null && mascotaId != null && userTrabajadorId != null ) {
            Viaje viaje = Viaje.builder()
                    .origen(viajeRequest.getOrigen())
                    .destino(viajeRequest.getDestino())
                    .tipo_transporte(viajeRequest.getTipo_transporte())
                    .fecha_viaje(viajeRequest.getFecha_viaje())
                    .created_at(LocalDateTime.now())
                    .cliente(userService.findUserById(userClienteId)
                        .orElseThrow(() -> new ResourceNotFoundException("ClienteId not found")))
                    .mascota(mascotaService.findMascotaById(mascotaId)
                        .orElseThrow(() -> new ResourceNotFoundException("MascotaId not found")))
                    .trabajador(userService.findUserById(userTrabajadorId)
                        .orElseThrow(() -> new ResourceNotFoundException("TrabajadorId not found")))
                    .build();

            return viajeRepository.save(viaje);
        } else {
            logger.error("ClienteId, TrabajadorId and MascotaId are required - method saveViaje");
            throw new IllegalArgumentException("ClienteId, TrabajadorId and MascotaId are required");
        }
    }

    @Override
    public Optional<Viaje> findViajeById(Long id) {
        logger.debug("Getting viaje by id: {} - method findViajeById", id);
        return viajeRepository.findById(id);
    }

    @Override
    public List<Viaje> findAllViajes() {
        logger.debug("Getting all viajes - method findAllViaje");
        return viajeRepository.findAll();
    }

    @Override
    public void deleteViajeById(Long id) {
        logger.debug("Deleting viaje by id: {} - method deleteViajeById", id);
        try {
            viajeRepository.deleteById(id);
            logger.debug("Viaje deleted successfully - method deleteViajeById");
        } catch (Exception e) {
            logger.error("Viaje not found by id: {} - method deleteViajeById", id);
            throw new ResourceNotFoundException("Viaje not found");
        }
    }
}