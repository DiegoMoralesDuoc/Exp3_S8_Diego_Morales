package com.fullstack.clases;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class PeliculaController {
    private static List<Pelicula> peliculas = new ArrayList<>();

    public PeliculaController() {
        peliculas.add(new Pelicula(1, "Titanic", 1997, "Romántica", "James Cameron", "Una historia de amor en el Titanic."));
        peliculas.add(new Pelicula(2, "Matrix", 1999, "Ciencia Ficción", "Lana & Lilly Wachowski", "Un hacker descubre la realidad simulada."));
        peliculas.add(new Pelicula(3, "Avatar", 2009, "Ciencia Ficción", "James Cameron", "Un exmarine explora un planeta alienígena."));
        peliculas.add(new Pelicula(4, "The Avengers", 2012, "Acción", "Joss Whedon", "Los héroes de Marvel se unen para salvar el mundo."));
        peliculas.add(new Pelicula(5, "Jurassic World", 2015, "Aventura", "Colin Trevorrow", "Un parque temático con dinosaurios revive el caos."));
        peliculas.add(new Pelicula(6, "Star Wars: The Force Awakens", 2015, "Ciencia Ficción", "J.J. Abrams", "Una nueva amenaza surge en la galaxia."));
        peliculas.add(new Pelicula(7, "Avengers: Endgame", 2019, "Acción", "Anthony & Joe Russo", "El enfrentamiento final contra Thanos."));
    }

    @GetMapping("/peliculas")
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    @GetMapping("/peliculas/{id}")
    public Pelicula getPeliculaById(@PathVariable int id) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getId() == id) {
                return pelicula;
            }
        }
        return null;
    }
}
