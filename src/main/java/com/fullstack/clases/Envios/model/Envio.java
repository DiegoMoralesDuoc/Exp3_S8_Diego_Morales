package com.fullstack.clases.Envios.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "envios")
public class Envio extends RepresentationModel<Envio> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "envio_seq")
    @SequenceGenerator(name = "envio_seq", sequenceName = "envio_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String atendido_por;

    @NotNull
    @Size(min = 5, max = 30)
    private String remitente;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email_remitente;

    @NotNull
    @Size(min = 5, max = 30)
    private String destinatario;

    @NotNull
    @Column(unique = true)
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

    private LocalDateTime fecha_envio;

    @NotNull
    @Size(min = 1, max = 50)
    private String ubicacion_actual;

    private LocalDateTime ultima_actualizacion;

    @Builder.Default
    @NotNull
    @Size(min = 1, max = 50)
    private String estado = "Pendiente";
}
