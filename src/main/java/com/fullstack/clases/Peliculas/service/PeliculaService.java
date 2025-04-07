package com.fullstack.clases.Peliculas.service;

import com.fullstack.clases.Peliculas.model.Pelicula;
import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    List <Pelicula> getAllPeliculas();
    Optional <Pelicula> getPeliculaById (Long id);
    Pelicula createPelicula (Pelicula pelicula);
    Pelicula updatePelicula (Long id, Pelicula pelicula);
    void deletePelicula (Long id);
}
