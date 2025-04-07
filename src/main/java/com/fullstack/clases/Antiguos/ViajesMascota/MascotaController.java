package com.fullstack.clases.Antiguos.ViajesMascota;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
public class MascotaController {
    private static List<Mascota> mascotas = new ArrayList<>();

    public MascotaController() {
        mascotas.add(new Mascota(1, "Copo", "Bryan"));
        mascotas.add(new Mascota(2, "Luna", "Gerson"));
        mascotas.add(new Mascota(3, "Rocky","Sofia"));
        mascotas.add(new Mascota(4, "Kika","Javiera"));
        mascotas.add(new Mascota(5, "Firulais","Paula"));
        mascotas.add(new Mascota(6, "Micky","Roberto"));
        mascotas.add(new Mascota(7, "Negro","Antonio"));
    }

    @GetMapping("/mascotas")
    public List<Mascota> getMascotas() {
        return mascotas;
    }

    @GetMapping("/mascotas/{id}")
    public Mascota getMascotaById(@PathVariable int id) {
        for (Mascota mascota : mascotas) {
            if (mascota.getIdMascota() == id) {
                return mascota;
            }
        }
        return null;
    }
}