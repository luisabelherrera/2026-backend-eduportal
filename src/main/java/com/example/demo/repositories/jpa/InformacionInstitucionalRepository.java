package com.example.demo.repositories.jpa;

import com.example.demo.model.entity.InformacionInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacionInstitucionalRepository extends JpaRepository<InformacionInstitucional, Long> {
}
