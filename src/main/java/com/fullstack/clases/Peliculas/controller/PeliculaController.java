package com.fullstack.clases.Peliculas.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.clases.Peliculas.model.Pelicula;
import com.fullstack.clases.Peliculas.service.PeliculaService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {
    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Pelicula> getAllPeliculas() {
        return peliculaService.getAllPeliculas();
    }
    
    @GetMapping("/{id}")
    public Optional<Pelicula> getPeliculaById(@PathVariable Long id) {
        return peliculaService.getPeliculaById(id);
    }

    @PostMapping
    public Pelicula creaPelicula (@RequestBody Pelicula pelicula) {
        return peliculaService.createPelicula(pelicula);
    }

    @PostMapping("/{id}")
    public Pelicula updatePelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
        return peliculaService.updatePelicula(id, pelicula);
    }
    
    @DeleteMapping("/{id}")
    public void deletePelicula(@PathVariable Long id){
        peliculaService.deletePelicula(id);
    }

}
