package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "archivos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] datos;
}
