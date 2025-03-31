package com.fullstack.clases.ViajesMascota;


class Usuario {
    private int idusuario;
    private String nombreusuario;
    private String amode;

    public Usuario(int idusuario, String nombreusuario, String amode) {
        this.idusuario = idusuario;
        this.nombreusuario = nombreusuario;
        this.amode = amode;
    }

    public int getId() { 
        return idusuario; 
    }

    public String getNombre() { 
        return nombreusuario; 
    }

    public String getAmoDe() {
        return amode;
    }
}

class Mascota {
    private int idmascota;
    private String nombremascota;
    private String nombreamo;

    public Mascota (int idmascota, String nombremascota, String nombreamo){
        this.idmascota = idmascota;
        this.nombremascota = nombremascota;
        this.nombreamo = nombreamo;
    }

    public int getIdMascota() { 
        return idmascota; 
    }

    public String getNombreMascota() { 
        return nombremascota; 
    }

    public String getNombreAmo() { 
        return nombreamo; 
    }
}

class Trabajador {
    private int idtrabajador;
    private String nombretrabajador;
    private String rol;

    public Trabajador(int idtrabajador, String nombretrabajador, String rol) {
        this.idtrabajador = idtrabajador;
        this.nombretrabajador = nombretrabajador;
        this.rol = rol;
    }

    public int getIdTrabajador() { 
        return idtrabajador; 
    }

    public String getNombreTrabajador() { 
        return nombretrabajador; 
    }

    public String getRolTrabajador() { 
        return rol; 
    }
}