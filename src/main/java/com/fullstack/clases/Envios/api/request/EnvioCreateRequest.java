package com.fullstack.clases.Envios.api.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnvioCreateRequest {

   @NotNull
   @Size(min = 1, max = 50)
   private String atendido_por;

   @NotNull
   @Size(min = 5, max = 30)
   private String remitente;

   @NotNull
   @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
   private String email_remitente;

   @NotNull
   @Size(min = 5, max = 30)
   private String destinatario;

   @NotNull
   @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
   private String email_destinatario;

   @NotNull
   @Size(min = 1, max = 50)
   private String nombre_sucursal;

   @NotNull
   @Size(min = 1, max = 50)
   private String destino;

   @NotNull
   @Min(1)
   @Max(999999)
   private int distancia;

   @NotNull
   @Min(1)
   @Max(999999999)
   private int valor_declarado;

   @NotNull
   @Min(1)
   @Max(999999)
   private int peso;

   @NotNull
   @Min(1)
   @Max(999999)
   private int largo;

   @NotNull
   @Min(1)
   @Max(999999)
   private int alto;

   @NotNull
   @Min(1)
   @Max(999999)
   private int ancho;

   @NotNull
   @Size(min = 1, max = 50)
   private String servicio;

   @NotNull
   @Min(1)
   @Max(999999999)
   private int valor_envio;

   @NotNull
   @Size(min = 1, max = 50)
   private String ubicacion_actual;

}
