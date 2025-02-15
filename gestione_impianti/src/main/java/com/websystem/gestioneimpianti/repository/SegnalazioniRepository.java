package com.websystem.gestioneimpianti.repository;

import com.websystem.gestioneimpianti.model.ImpressioniDto;
import com.websystem.gestioneimpianti.model.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SegnalazioniRepository extends JpaRepository<Segnalazione, Long> {

    @Query("SELECT new com.websystem.gestioneimpianti.model.ImpressioniDto(I.descrizione, " +
            "P.nome, " +
            "C.nome, " +
            "SUM(S.durata) AS durata_tot, " +
            "COUNT(*) AS numero_impressioni )" +
            "FROM Segnalazione S " +
            "JOIN Impianto I on I.idImpianto = S.idImpianto " +
            "JOIN Cartellone C on C.id = S.idCartellone " +
            "JOIN Palinsesto P on P.idPalinsesto = S.idPalinsesto " +
            "WHERE S.timestamp BETWEEN :startDate AND :endDate " +
            "AND (:idImpianto IS NULL OR S.idImpianto = :idImpianto) " +
            "AND (:idCartellone IS NULL OR S.idCartellone = :idCartellone) " +
            "AND (:idPalinsesto IS NULL OR S.idPalinsesto = :idPalinsesto) " +
            "GROUP BY S.idImpianto, S.idPalinsesto, S.idCartellone " +
            "ORDER BY S.idImpianto, S.idPalinsesto, S.idCartellone")
    List<ImpressioniDto> findByFilters(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("idImpianto") Long idImpianto,
                                       @Param("idCartellone") Long idCartellone,
                                       @Param("idPalinsesto") Long idPalinsesto);

}
