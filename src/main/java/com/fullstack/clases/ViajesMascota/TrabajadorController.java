package com.fullstack.clases.ViajesMascota;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TrabajadorController {
    private static List<Trabajador> trabajadores = new ArrayList<>();

    public TrabajadorController() {
        trabajadores.add(new Trabajador(1, "Leandro","Conductor"));
        trabajadores.add(new Trabajador(2, "Leonel","Ayudante de Conductor"));
        trabajadores.add(new Trabajador(3, "Miguel","Recepcionista"));
    }

    @GetMapping("/trabajadores")
    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    @GetMapping("/trabajador/{id}")
    public Trabajador getTrabajadorById(@PathVariable int id) {
        for (Trabajador trabajador : trabajadores) {
            if (trabajador.getIdTrabajador() == id) {
                return trabajador;
            }
        }
        return null;
    }
}