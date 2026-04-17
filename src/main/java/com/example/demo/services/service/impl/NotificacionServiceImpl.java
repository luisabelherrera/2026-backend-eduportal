package com.example.demo.services.service.impl;

import com.example.demo.model.entity.Notificacion;
import com.example.demo.model.entity.dto.NotificacionDTO;
import com.example.demo.repositories.jpa.NotificacionRepository;
import com.example.demo.services.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public List<NotificacionDTO> obtenerTodas() {
        return notificacionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionDTO obtenerPorId(Long id) {
        return notificacionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public NotificacionDTO crearNotificacion(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = new Notificacion(notificacionDTO.getTitulo(), notificacionDTO.getMensaje());
        return convertToDTO(notificacionRepository.save(notificacion));
    }

    @Override
    public void eliminarNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }

    @Override
    public NotificacionDTO marcarComoLeida(Long id) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setLeida(true);
            return convertToDTO(notificacionRepository.save(notificacion));
        }).orElse(null);
    }

    private NotificacionDTO convertToDTO(Notificacion notificacion) {
        return new NotificacionDTO(
                notificacion.getId().toString(),
                notificacion.getTitulo(),
                notificacion.getMensaje(),
                notificacion.isLeida(),
                notificacion.getFechaHora()
        );
    }
}
