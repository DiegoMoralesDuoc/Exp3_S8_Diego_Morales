package com.fullstack.clases.Peliculas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.clases.Peliculas.model.Pelicula;

public interface PeliculaRepository extends JpaRepository <Pelicula, Long>{
    
}
