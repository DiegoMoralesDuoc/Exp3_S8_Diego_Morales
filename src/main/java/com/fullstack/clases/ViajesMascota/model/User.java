package com.fullstack.clases.ViajesMascota.model;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonPropertyOrder({
    "id",
    "rol",
    "cargo",
    "nombre",
    "apellido",
    "username",
    "password",
    "email",
    "mascota",
    "created_at"
})

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="users")
public class User extends RepresentationModel<User>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name= "user_seq", sequenceName = "user_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    //Rol: Cliente o Trabajador
    @NotNull
    @Size(min = 3,max = 10)
    private String rol;

    //Cargo: Conductor, Asistente, Secretaria
    //Puede ser nulo en caso que el Rol sea Cliente
    @Size(min = 3,max = 20)
    private String cargo;

    @NotNull
    @Size(min = 3,max = 30)
    private String nombre;

    @NotNull
    @Size(min = 3,max = 30)
    private String apellido;

    @Column(unique=true)
    @Size(min= 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$")
    private String username;

    @NotNull
    @Size(min= 6, max=20)
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mascota_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Mascota mascota;

    private LocalDateTime created_at;

    @PrePersist
    public void prePersist() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
    }

}
