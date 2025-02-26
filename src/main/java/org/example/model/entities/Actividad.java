package org.example.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actividad", schema = "eco")
public class Actividad {
    @Id
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    private Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private List<Habito> habitos = new ArrayList<>();

    @OneToMany(mappedBy = "idActividad")
    private List<Huella> huellas = new ArrayList<>();

    public Actividad(int id, String nombre, Categoria idCategoria){
        this.id = id;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
    }

    public Actividad() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habito> habitos) {
        this.habitos = habitos;
    }

    public List<Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(List<Huella> huellas) {
        this.huellas = huellas;
    }

}