package com.fullstack.clases.Antiguos.Envios;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SucursalController {
    private static List<Sucursal> sucursales = new ArrayList<>();
    
    public SucursalController() {
        sucursales.add(new Sucursal(1,"Providencia","Av. Providencia 123, Santiago",240));
        sucursales.add(new Sucursal(2,"Concepcion","O'Higgins 789, Concepción",1010));
        sucursales.add(new Sucursal(3,"Iquique","Esmeralda 753, Iquique",508));
        sucursales.add(new Sucursal(4,"Puerto Montt","Balmaceda 369, Puerto Montt",640));
    }

    @GetMapping("/sucursales")
    public List<Sucursal> getSucursal() {
        return sucursales;
    }

    @GetMapping("/sucursal/{id}")
    public Sucursal getSucursalById(@PathVariable int id) {
        for (Sucursal sucursales : sucursales) {
            if (sucursales.getIdSucursal() == id) {
                return sucursales;
            }
        }
        return null;
    }
}