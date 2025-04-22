package com.fullstack.clases.ViajesMascota.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
    "id",
    "name",
    "raza",
    "user",
    "created_at"
})

@AllArgsConstructor
@NoArgsConstructor
@Data



@Entity
@Table(name="mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mascota_seq")
    @SequenceGenerator(name= "mascota_seq", sequenceName = "mascota_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @NotNull
    @Size(min = 3,max = 30)
    private String name;

    @NotNull
    @Size(min = 3,max = 30)
    private String raza;

    @OneToOne(mappedBy = "mascota")
    @JsonBackReference
    private User user;

    private LocalDateTime created_at;

        @PrePersist
    public void prePersist() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
    }
    
}
