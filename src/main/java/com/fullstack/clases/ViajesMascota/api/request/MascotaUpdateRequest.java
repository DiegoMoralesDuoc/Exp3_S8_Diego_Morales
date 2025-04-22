package com.fullstack.clases.ViajesMascota.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MascotaUpdateRequest {
    
    @NotNull
    @Size(min = 3,max = 30)
    private String name;

    @NotNull
    @Size(min = 3,max = 30)
    private String raza;

}
