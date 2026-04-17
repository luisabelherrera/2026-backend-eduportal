package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "noticias")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String contenido;
    private String imagenPath;
    private String videoPath;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    private Integer likesCount = 0;

    @ElementCollection
    @CollectionTable(name = "noticia_liked_by", joinColumns = @JoinColumn(name = "noticia_id"))
    @Column(name = "username")
    private List<String> likedBy = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "noticia_comentarios", joinColumns = @JoinColumn(name = "noticia_id"))
    private List<Comentario> comentarios = new ArrayList<>();

    @Embeddable
    @Data
    public static class Comentario {
        private String autor;
        @Column(columnDefinition = "TEXT")
        private String contenido;
        @Temporal(TemporalType.TIMESTAMP)
        private Date fechaCreacion;
    }
}
