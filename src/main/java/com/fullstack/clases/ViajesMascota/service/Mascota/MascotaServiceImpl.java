package com.fullstack.clases.ViajesMascota.service.Mascota;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fullstack.clases.ViajesMascota.api.request.MascotaUpdateRequest;
import com.fullstack.clases.ViajesMascota.exceptionhandler.DatabaseTransactionException;
import com.fullstack.clases.ViajesMascota.exceptionhandler.ResourceNotFoundException;
import com.fullstack.clases.ViajesMascota.model.Mascota;
import com.fullstack.clases.ViajesMascota.repository.MascotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {
    
@Autowired
    private MascotaRepository mascotaRepository;
    private static final Logger logger = LoggerFactory.getLogger(MascotaServiceImpl.class);

    @Override
    public Mascota saveMascota(Mascota mascotaToSave) {
        
        

        logger.info("Creando mascota con request: {} - metodo saveMascota", mascotaToSave);
        try {
            Mascota savedMascota = mascotaRepository.save(mascotaToSave);
            logger.info("Mascota creado satisfactoriamente. Mascota ID: {} - metodo saveMascota", savedMascota.getId());
            return savedMascota;
    
        } catch (Exception e) {
            logger.error("Error creando mascota - metodo saveMascota", e);
            throw new DatabaseTransactionException("Error creando usuario", e);
        }
    }

    @Override
    public Optional<Mascota> findMascotaById(Long id) {
        logger.info("Finding mascota by ID: {} - method findMascotaById", id);
        return mascotaRepository.findById(id);
    }


    @Override
    public Mascota updateMascota(Long id, MascotaUpdateRequest updateRequest) {
        logger.info("Updating mascota with ID: {} and request: {} - method updateMascota", id, updateRequest);
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota not found"));

        if (updateRequest.getRaza() != null) {
            logger.info("Updating Raza to: {} - method updateMascota", updateRequest.getRaza());
            mascota.setRaza(updateRequest.getRaza());
        }
        if (updateRequest.getName() != null) {
            logger.info("Updating Name to: {} - method updateMascota", updateRequest.getName());
            mascota.setName(updateRequest.getName());
        }


        logger.info("Saving mascota - method updateMascota");
        Mascota updatedMascota = mascotaRepository.save(mascota);
        logger.info("Mascota updated successfully. mascota ID: {}", updatedMascota.getId());
        return updatedMascota;
    }

    @Override
    public List<Mascota> findAllMascotas() {
        logger.info("Finding all mascotas - method findAllMascotas");
        return mascotaRepository.findAll();
    }

    @Override
    public void deleteMascotaById(Long id) {
        logger.info("Deleting mascota by ID: {} - method deleteMascotaById", id);
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if (mascota.isEmpty()) {
            logger.info("Mascota not found - method deleteMascotaById");
            throw new ResourceNotFoundException("Mascota not found");
        }
        logger.info("Deleting mascota - method deleteMascotaById");
        mascotaRepository.deleteById(id);
        logger.info("Mascota deleted successfully. Mascota ID: {} - method deleteMascotaById", id);
    }


}