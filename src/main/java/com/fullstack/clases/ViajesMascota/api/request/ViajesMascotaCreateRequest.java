package com.fullstack.clases.ViajesMascota.api.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViajesMascotaCreateRequest {
    
   @NotNull
   private Long UserClienteId;

   @NotNull
   private Long UserTrabajadorId;

   @NotNull
   private Long MascotaId;

    @NotNull
    @Size(max=50)
    private String origen;

    @NotNull
    @Size(max=50)
    private String destino;

    @NotNull
    @Size(max=30)
    private String tipo_transporte;

    private LocalDate fecha_viaje;

}
