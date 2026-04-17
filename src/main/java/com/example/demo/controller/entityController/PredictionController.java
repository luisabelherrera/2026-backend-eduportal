package com.example.demo.controller.entityController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.example.demo.model.entity.DatosEstudiante;
import com.example.demo.model.entity.Estudiante;
import com.example.demo.repositories.jpa.DatosEstudianteRepository;
import com.example.demo.repositories.jpa.EstudianteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Controlador de predicción de rendimiento estudiantil. Delega el cómputo ML al
 * microservicio Python (FastAPI + scikit-learn). Endpoint Python: POST
 * http://localhost:5000/predecir
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PredictionController {

    private static final Logger LOGGER = Logger.getLogger(PredictionController.class.getName());

    @Value("${ml.service.url:http://localhost:5000}")
    private String mlServiceUrl;

    private final DatosEstudianteRepository datosEstudianteRepository;
    private final EstudianteRepository estudianteRepository;
    private final RestTemplate restTemplate;

    public PredictionController(DatosEstudianteRepository datosEstudianteRepository,
            EstudianteRepository estudianteRepository) {
        this.datosEstudianteRepository = datosEstudianteRepository;
        this.estudianteRepository = estudianteRepository;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/predecir")
    public ResponseEntity<Map<String, String>> predecir(@RequestBody DatosEstudiante datos) {
        try {
            LOGGER.info("Datos recibidos para predicción: " + datos.toString());

            // Asociar el estudiante si existe
            Optional<Estudiante> estudianteOpt = estudianteRepository
                    .findByDocumentoIdentidad(String.valueOf(datos.getDocumento()));
            estudianteOpt.ifPresent(datos::setEstudiante);

            // Construir payload para el microservicio Python
            Map<String, Object> payload = new HashMap<>();
            payload.put("documento", datos.getDocumento());
            payload.put("edad", datos.getEdad());
            payload.put("genero", datos.getGenero());
            payload.put("horasEstudioSemanal", datos.getHorasEstudioSemanal());
            payload.put("asistencia", datos.getAsistencia());
            payload.put("promedioParciales", datos.getPromedioParciales());
            payload.put("participacionClases", datos.getParticipacionClases());
            payload.put("usoPlataformaVirtual", datos.getUsoPlataformaVirtual());
            payload.put("antecedentesPerdida", datos.getAntecedentesPerdida());
            payload.put("apoyoFamiliar", datos.getApoyoFamiliar());
            payload.put("cargaAcademica", datos.getCargaAcademica());
            payload.put("problemasPersonales", datos.getProblemasPersonales());

            // Llamar al microservicio Python
            String pythonEndpoint = mlServiceUrl + "/predecir";
            LOGGER.info("Llamando al microservicio Python: " + pythonEndpoint);

            @SuppressWarnings("unchecked")
            Map<String, Object> pythonResponse = restTemplate.postForObject(
                    pythonEndpoint, payload, Map.class);

            if (pythonResponse == null) {
                throw new RuntimeException("El microservicio Python no retornó respuesta");
            }

            String resultado = (String) pythonResponse.get("prediccion");
            String confianza = (String) pythonResponse.get("confianza");

            LOGGER.info("Predicción obtenida: " + resultado + " | Confianza: " + confianza);

            // Guardar o actualizar en base de datos
            Optional<DatosEstudiante> existingOpt = datosEstudianteRepository.findByDocumento(datos.getDocumento());
            DatosEstudiante datosToSave = existingOpt.orElse(datos);

            datosToSave.setPerderaAsignatura(resultado);
            datosToSave.setConfianza(confianza);
            datosToSave.setEdad(datos.getEdad());
            datosToSave.setGenero(datos.getGenero());
            datosToSave.setHorasEstudioSemanal(datos.getHorasEstudioSemanal());
            datosToSave.setAsistencia(datos.getAsistencia());
            datosToSave.setPromedioParciales(datos.getPromedioParciales());
            datosToSave.setParticipacionClases(datos.getParticipacionClases());
            datosToSave.setUsoPlataformaVirtual(datos.getUsoPlataformaVirtual());
            datosToSave.setAntecedentesPerdida(datos.getAntecedentesPerdida());
            datosToSave.setApoyoFamiliar(datos.getApoyoFamiliar());
            datosToSave.setCargaAcademica(datos.getCargaAcademica());
            datosToSave.setProblemasPersonales(datos.getProblemasPersonales());
            if (estudianteOpt.isPresent()) {
                datosToSave.setEstudiante(estudianteOpt.get());
            }

            DatosEstudiante savedDatos = datosEstudianteRepository.save(datosToSave);
            LOGGER.info("Predicción guardada con id: " + savedDatos.getId());

            // Respuesta al frontend (mantiene compatibilidad con Angular)
            Map<String, String> response = new HashMap<>();
            response.put("prediccion", resultado);
            response.put("confianza", confianza);
            response.put("id", savedDatos.getId() != null ? savedDatos.getId().toString() : "0");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.severe("Error en predicción: " + e.getMessage());
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }

    @GetMapping("/historial")
    public ResponseEntity<List<DatosEstudiante>> getHistorialPredicciones() {
        try {
            List<DatosEstudiante> historial = datosEstudianteRepository.findAll();
            LOGGER.info("Historial de predicciones: " + historial.size() + " registros.");
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            LOGGER.severe("Error al obtener historial: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/prediccion-estudiante/{documento}")
    public ResponseEntity<?> getUltimaPrediccionPorDocumento(@PathVariable Long documento) {
        try {
            Optional<DatosEstudiante> datosOpt = datosEstudianteRepository.findByDocumento(documento);
            if (datosOpt.isPresent()) {
                return ResponseEntity.ok(datosOpt.get());
            }
            return ResponseEntity.status(404)
                    .body(Map.of("error", "No existe historial de predicción para el documento " + documento));
        } catch (Exception e) {
            LOGGER.severe("Error al obtener predicción por documento: " + e.getMessage());
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }

    @GetMapping("/metricas-modelo")
    public ResponseEntity<?> getMetricasModelo() {
        try {
            String pythonEndpoint = mlServiceUrl + "/metricas";
            Object metricas = restTemplate.getForObject(pythonEndpoint, Object.class);
            return ResponseEntity.ok(metricas);
        } catch (Exception e) {
            LOGGER.severe("Error al obtener métricas del modelo Python: " + e.getMessage());
            return ResponseEntity.status(503)
                    .body(Map.of("error", "Microservicio Python no disponible: " + e.getMessage()));
        }
    }
}
