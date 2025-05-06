package com.fullstack.clases.Envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fullstack.clases.Envios.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

   
}

