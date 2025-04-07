package com.fullstack.clases.Antiguos.ViajesMascota;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {
    private static List<Usuario> usuarios = new ArrayList<>();

    public UsuarioController() {
        usuarios.add(new Usuario(1, "Bryan", "Copo"));
        usuarios.add(new Usuario(2, "Gerson", "Luna"));
        usuarios.add(new Usuario(3, "Sofia", "Rocky"));
        usuarios.add(new Usuario(4, "Javiera", "Kika"));
        usuarios.add(new Usuario(5, "Paula", "Firulais"));
        usuarios.add(new Usuario(6, "Roberto", "Micky"));
        usuarios.add(new Usuario(7, "Antonio", "Negro"));

    }

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @GetMapping("/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }
}