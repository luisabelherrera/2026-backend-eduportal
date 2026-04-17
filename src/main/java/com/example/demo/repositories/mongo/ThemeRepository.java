package com.example.demo.repositories.mongo;

import com.example.demo.model.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByIsActiveTrue();
}
