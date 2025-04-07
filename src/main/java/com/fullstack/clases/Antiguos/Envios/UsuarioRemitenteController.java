package com.fullstack.clases.Antiguos.Envios;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioRemitenteController {
    private static List<UsuarioRemitente> usuarioremitentes = new ArrayList<>();

    public UsuarioRemitenteController() {
        usuarioremitentes.add(new UsuarioRemitente(1,"Roberto Alfaro","Av. Irarrázaval 1050, Ñuñoa, Santiago",3));
        usuarioremitentes.add(new UsuarioRemitente(2,"Andres Manzano","Barros Arana 750, Concepción",10));
        usuarioremitentes.add(new UsuarioRemitente(3,"Luis Perez","Av. Apoquindo 4500, Las Condes",1));
        usuarioremitentes.add(new UsuarioRemitente(4,"Jeremy Albornoz","Av. Héroes de la Concepción 2555",50));
        usuarioremitentes.add(new UsuarioRemitente(5,"Claudio Rojas","Av. Diego Portales 998",13));
    }
    @GetMapping("/remitentes")
    public List<UsuarioRemitente> getUsuarioRemitente() {
        return usuarioremitentes;
    }

    @GetMapping("/remitente/{id}")
    public UsuarioRemitente getUsuarioRemitenteById(@PathVariable int id) {
        for (UsuarioRemitente usuarioremitentes : usuarioremitentes) {
            if (usuarioremitentes.getIdRemitente() == id) {
                return usuarioremitentes;
            }
        }
        return null;
    }
}