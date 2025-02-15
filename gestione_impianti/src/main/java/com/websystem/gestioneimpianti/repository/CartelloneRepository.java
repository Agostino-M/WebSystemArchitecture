package com.websystem.gestioneimpianti.repository;

import com.websystem.gestioneimpianti.model.Cartellone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartelloneRepository extends JpaRepository<Cartellone, Long> {
}
