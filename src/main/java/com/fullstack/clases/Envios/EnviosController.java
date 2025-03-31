package com.fullstack.clases.Envios;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EnviosController {
    private static List<Envio> envios = new ArrayList<>();

    public EnviosController() {
        envios.add(new Envio(1,"Av. Providencia 123, Santiago","Los Carrera 456, Antofagasta","Roberto Alfaro","Felipe Loyola",50300, "Providencia"));
        envios.add(new Envio(2,"O'Higgins 789, Concepción","Manuel Montt 321, Valparaíso","Andres Manzano","David Morales",150000, "Concepcion"));
        envios.add(new Envio(3,"Av. Providencia 123, Santiago","Matta 159, Temuco","Luis Perez","Javiera Contreras",50000,"Santiago"));
        envios.add(new Envio(4,"Esmeralda 753, Iquique","Baquedano 654, La Serena","Jeremy Albornoz","Paulina Velazquez",80000,"Iquique"));
        envios.add(new Envio(5,"Balmaceda 369, Puerto Montt","Colón 987, Punta Arenas","Claudio Rojas","Maximiliano Lopez",700000,"Puerto Montt"));
    }

    @GetMapping("/envios")
    public List<Envio> getEnvios() {
        return envios;
    }

    @GetMapping("/envio/{id}")
    public Envio getEnviosById(@PathVariable int id) {
        for (Envio envios : envios) {
            if (envios.getIdEnvio() == id) {
                return envios;
            }
        }
        return null;
    }
}