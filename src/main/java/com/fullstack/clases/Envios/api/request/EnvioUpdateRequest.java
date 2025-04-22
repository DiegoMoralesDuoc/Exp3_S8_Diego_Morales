package com.fullstack.clases.Envios.api.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnvioUpdateRequest {

//
   @NotNull
   @Size(min = 1,max = 50)
   private String estado;

   @NotNull
   @Size(min = 1,max = 50)
   private String ubicacion_actual;

   private LocalDateTime ultima_actualizacion;
}
