package com.example.demo.repositories.mongo;

import com.example.demo.model.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
