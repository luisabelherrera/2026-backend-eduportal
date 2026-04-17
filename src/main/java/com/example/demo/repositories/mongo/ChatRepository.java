package com.example.demo.repositories.mongo;

import com.example.demo.model.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findFirst10ByOrderByFechaDesc();

    List<Mensaje> findByUsernameAndDestinatarioOrUsernameAndDestinatarioOrderByFechaAsc(
        String userA, String recipientA, String userB, String recipientB
    );
}
