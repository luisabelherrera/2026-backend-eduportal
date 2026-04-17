package com.example.demo.services.service;

import com.example.demo.model.entity.dto.NotificacionDTO;
import java.util.List;

public interface NotificacionService {
    List<NotificacionDTO> obtenerTodas();
    NotificacionDTO obtenerPorId(Long id);
    NotificacionDTO crearNotificacion(NotificacionDTO notificacionDTO);
    void eliminarNotificacion(Long id);
    NotificacionDTO marcarComoLeida(Long id);
}
