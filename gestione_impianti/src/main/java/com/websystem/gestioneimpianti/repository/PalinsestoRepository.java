package com.websystem.gestioneimpianti.repository;

import com.websystem.gestioneimpianti.model.Palinsesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalinsestoRepository extends JpaRepository<Palinsesto, Long> {
}
