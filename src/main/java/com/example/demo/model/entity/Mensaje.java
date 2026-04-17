package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mensajes")
public class Mensaje implements Serializable {

    private static final long serialVersionUID = -3777582564067492550L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;
    private Long fecha;
    private String username;
    private String destinatario;
    private String tipo;
    private String color;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Long getFecha() { return fecha; }
    public void setFecha(Long fecha) { this.fecha = fecha; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
