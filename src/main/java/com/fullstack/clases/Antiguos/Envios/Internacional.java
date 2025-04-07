package com.fullstack.clases.Antiguos.Envios;

public class Internacional {
    
};

class Envio {
    private int idenvio;
    private String ubicacionactual;
    private String destino;
    private String remitente;
    private String destinatario;
    private int valor;
    private String sucursal;

    public Envio (int idenvio, String ubicacionactual, String destino, String remitente, String destinatario, int valor, String sucursal) {
        this.idenvio = idenvio;
        this.ubicacionactual = ubicacionactual;
        this.destino = destino;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.valor = valor;
        this.sucursal = sucursal;
    }

    public int getIdEnvio(){
        return idenvio;
    }

    public String getUbicacionActual(){
        return ubicacionactual;
    }

    public String getDestino(){
        return destino;
    }

    public String getRemitente(){
        return remitente;
    }

    public String getDestinatario(){
        return destinatario;
    }

    public int getValor(){
        return valor;
    }

    public String getSucursal(){
        return sucursal;
    }

}


class UsuarioRemitente {
    private int idremitente;
    private String nombreremitente;
    private String direccionremitente;
    private int cantidadenvios;

    public UsuarioRemitente (int idremitente, String nombreremitente, String direccionremitente, int cantidadenvios) {
        this.idremitente = idremitente;
        this.nombreremitente = nombreremitente;
        this.direccionremitente = direccionremitente;
        this.cantidadenvios = cantidadenvios;
    }

    public int getIdRemitente(){
        return idremitente;
    }

    public String getNombreRemitente(){
        return nombreremitente;
    }

    public String getDireccionRemitente(){
        return direccionremitente;
    }

    public int getCantidadEnvios(){
        return cantidadenvios;
    }
}

class Sucursal {
    private int idsucursal;
    private String nombresucursal;
    private String direccion;
    private int cantidadenvios;

    public Sucursal(int idsucursal, String nombresucursal, String direccion, int cantidadenvios) {
        this.idsucursal = idsucursal;
        this.nombresucursal = nombresucursal;
        this.direccion = direccion;
        this.cantidadenvios = cantidadenvios;
    }

    public int getIdSucursal() { 
        return idsucursal; 
    }

    public String getNombreSucursal() { 
        return nombresucursal; 
    }

    public String getDireccion() { 
        return direccion; 
    }

    public int getCantidadEnvios(){
        return cantidadenvios;
    }
}