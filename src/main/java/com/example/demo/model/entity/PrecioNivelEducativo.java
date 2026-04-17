package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "precios_nivel_educativo")
public class PrecioNivelEducativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nivel;
    private String concepto;
    private double monto;
    private String imagenPath;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private String periodicidad;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    private Double descuento;
    private String categoria;

    public PrecioNivelEducativo() {}

    public PrecioNivelEducativo(String nivel, String concepto, double monto, String imagenPath,
                                String descripcion, String periodicidad, Date fechaInicio,
                                Date fechaFin, Double descuento, String categoria) {
        this.nivel = nivel;
        this.concepto = concepto;
        this.monto = monto;
        this.imagenPath = imagenPath;
        this.descripcion = descripcion;
        this.periodicidad = periodicidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descuento = descuento;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getImagenPath() { return imagenPath; }
    public void setImagenPath(String imagenPath) { this.imagenPath = imagenPath; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPeriodicidad() { return periodicidad; }
    public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
