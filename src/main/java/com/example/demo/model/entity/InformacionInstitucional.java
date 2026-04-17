package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "informacion_institucional")
public class InformacionInstitucional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreInstitucion;
    @Column(columnDefinition = "TEXT")
    private String mision;
    @Column(columnDefinition = "TEXT")
    private String vision;
    @Column(columnDefinition = "TEXT")
    private String historia;
    @Column(columnDefinition = "TEXT")
    private String valores;
    @Column(columnDefinition = "TEXT")
    private String objetivos;
    private String contacto;
    private String manualConvivenciaPath;
    private String reglamentoInternoPath;
    private String logoPath;
    private String nivelesEducativos;
    private String enfasisInstitucional;
    private String preparacionIcfes;
    @Column(columnDefinition = "TEXT")
    private String programasEspeciales;
    private String convenios;
    @Column(columnDefinition = "TEXT")
    private String actividadesExtracurriculares;
    private String pastoralOCatequesis;
    private String planDeEstudiosPath;
    private String calendarioAcademicoPath;
}
