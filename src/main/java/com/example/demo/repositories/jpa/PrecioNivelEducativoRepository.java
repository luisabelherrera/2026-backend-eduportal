package com.example.demo.repositories.jpa;

import com.example.demo.model.entity.PrecioNivelEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioNivelEducativoRepository extends JpaRepository<PrecioNivelEducativo, Long> {
}
