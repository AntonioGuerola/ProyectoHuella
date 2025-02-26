package org.example.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "recomendacion", schema = "eco")
public class Recomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacion", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria idCategoria;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "impacto_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal impactoEstimado;

    public Recomendacion(Integer id, Categoria idCategoria, BigDecimal impactoEstimado){
        this.id = id;
        this.idCategoria = idCategoria;
        this.impactoEstimado = impactoEstimado;
    }

    public Recomendacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getImpactoEstimado() {
        return impactoEstimado;
    }

    public void setImpactoEstimado(BigDecimal impactoEstimado) {
        this.impactoEstimado = impactoEstimado;
    }

}