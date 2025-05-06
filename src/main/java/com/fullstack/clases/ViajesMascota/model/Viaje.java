package com.fullstack.clases.ViajesMascota.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
    "id",
    "cliente",
    "trabajador",
    "mascota",
    "origen",
    "destino",
    "tipo_transporte",
    "fecha_viaje",
    "created_at"
})

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "viaje_seq")
    @SequenceGenerator(name = "viaje_seq", sequenceName = "viaje_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    // Cliente que solicita el viaje
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;

    // Trabajador que atiende el viaje
    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false) 
    private User trabajador;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = true)
    private Mascota mascota;

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

    private LocalDateTime created_at;

    @PrePersist
    public void prePersist() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
    }
    
}