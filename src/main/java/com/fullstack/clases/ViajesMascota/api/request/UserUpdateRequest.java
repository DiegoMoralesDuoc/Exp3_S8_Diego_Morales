package com.fullstack.clases.ViajesMascota.api.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    
    @Size(min = 6, max = 12)
    private String password;

    @Size(min = 3,max = 20)
    private String cargo;

    @Size(min = 1,max = 99999)
    private int mascotaId;
 
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    

}
