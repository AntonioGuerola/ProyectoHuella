package org.example.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "habito", schema = "eco")
public class Habito {
    @EmbeddedId
    private HabitoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @MapsId("idActividad")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad idActividad;

    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "ultima_fecha")
    private LocalDate ultimaFecha;

    public Habito(Usuario idUsuario, Actividad idActividad, int frecuencia, Tipo tipo, LocalDate ultimaFecha) {
        this.id = new HabitoId(idUsuario.getId(), idActividad.getId());
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
        this.frecuencia = frecuencia;
        this.tipo = tipo != null ? Tipo.valueOf(tipo.name()) : null;
        this.ultimaFecha = ultimaFecha;
    }

    public Habito() {
    }

    public HabitoId getId() {
        return id;
    }

    public void setId(HabitoId id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipo() {
        return tipo != null ? tipo.name() : null;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo != null ? Tipo.valueOf(tipo) : null;
    }

    public LocalDate getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(LocalDate ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

}