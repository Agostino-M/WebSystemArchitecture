package com.websystem.gestioneimpianti.repository;

import com.websystem.gestioneimpianti.model.Impianto;
import com.websystem.gestioneimpianti.model.ImpiantoPalinsesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImpiantoRepository extends JpaRepository<Impianto, Long> {

    @Query(value = "SELECT new com.websystem.gestioneimpianti.model.ImpiantoPalinsesto(" +
            "I.idImpianto, " +
            "I.descrizione, " +
            "I.latitudine, " +
            "I.longitudine, " +
            "I.isAttivo, " +
            "I.idPalinsesto, " +
            "P.nome, " +
            "P.path) " +
            "FROM Impianto I " +
            "JOIN Palinsesto P on I.idPalinsesto = P.idPalinsesto " +
            "WHERE I.isAttivo = TRUE " +
            "AND I.idImpianto = :idImpianto ")
    Optional<ImpiantoPalinsesto> findImpiantoPalinsestoByIdImpiantoAndIsAttivo(@Param(value = "idImpianto") Long idImpianto);
}
