package com.websystem.gestioneimpianti.service;

import com.websystem.gestioneimpianti.model.*;
import com.websystem.gestioneimpianti.repository.CartelloneRepository;
import com.websystem.gestioneimpianti.repository.ImpiantoRepository;
import com.websystem.gestioneimpianti.repository.PalinsestoRepository;
import com.websystem.gestioneimpianti.repository.SegnalazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class GestioneImpiantiService {

    private final JdbcTemplate jdbcTemplate;
    private final ImpiantoRepository impiantoRepository;
    private final PalinsestoRepository palinsestoRepository;
    private final SegnalazioniRepository segnalazioniRepository;
    private final CartelloneRepository cartelloneRepository;

    @Autowired
    public GestioneImpiantiService(JdbcTemplate jdbcTemplate, ImpiantoRepository impiantoRepository,
                                   PalinsestoRepository palinsestoRepository,
                                   SegnalazioniRepository segnalazioniRepository,
                                   CartelloneRepository cartelloneRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.impiantoRepository = impiantoRepository;
        this.palinsestoRepository = palinsestoRepository;
        this.segnalazioniRepository = segnalazioniRepository;
        this.cartelloneRepository = cartelloneRepository;
    }

    /**
     * Get all impianti with the last timestamp and status based on the last timestamp being less than 2 minutes ago.
     *
     * @return a list of ImpiantoDto objects with their status.
     */
    public List<ImpiantoDto> getImpiantiStatus() {
        String sql = "SELECT i.id_impianto, i.descrizione, i.latitudine, i.longitudine, i.id_palinsesto, " +
                "MAX(s.timestamp) AS ultima_segnalazione, " +
                "CASE " +
                "    WHEN MAX(s.timestamp) > NOW() - INTERVAL 2 MINUTE THEN 'attivo' " +
                "    ELSE 'inattivo' " +
                "    END AS stato " +
                "FROM Impianti i " +
                "    LEFT JOIN Segnalazioni s ON i.id_impianto = s.id_impianto " +
                "GROUP BY i.id_impianto " +
                "ORDER BY i.id_impianto;";

        return jdbcTemplate.query(sql, new RowMapper<ImpiantoDto>() {
            @Override
            public ImpiantoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ImpiantoDto impiantoDto = new ImpiantoDto();
                impiantoDto.setIdImpianto(rs.getInt("id_impianto"));
                impiantoDto.setIdPalinsesto(rs.getInt("id_palinsesto"));
                impiantoDto.setDescrizione(rs.getString("descrizione"));
                impiantoDto.setLatitudine(rs.getFloat("latitudine"));
                impiantoDto.setLongitudine(rs.getFloat("longitudine"));
                Timestamp ts = rs.getTimestamp("ultima_segnalazione");
                impiantoDto.setUltimaSegnalazione(ts == null ? null : ts.toLocalDateTime());
                impiantoDto.setStato(rs.getString("stato"));
                return impiantoDto;
            }
        });
    }

    /**
     * Get an impianto by ID and active status.
     *
     * @param id the ID of the impianto.
     * @return an optional ImpiantoDto.
     */
    public Optional<ImpiantoPalinsesto> getImpiantoPAttivoById(Long id) {
        return impiantoRepository.findImpiantoPalinsestoByIdImpiantoAndIsAttivo(id);
    }

    public List<Impianto> getAllImpianti() {
        return impiantoRepository.findAll();
    }

    public Optional<Impianto> getImpiantoById(Long id) {
        return impiantoRepository.findById(id);
    }

    public Impianto saveImpianto(Impianto impianto) {
        return impiantoRepository.save(impianto);
    }

    public List<Palinsesto> getAllPalinsesti() {
        return palinsestoRepository.findAll();
    }

    public List<Cartellone> getAllCartelloni() {
        return cartelloneRepository.findAll();
    }

    /**
     * Deprecated version of getImpiantoAttivoById using JdbcTemplate.
     *
     * @param id the ID of the impianto.
     * @return an optional ImpiantoDto.
     */
    @Deprecated
    public Optional<Impianto> getImpiantoAttivoByIdDeprecated(Long id) {
        String sql = "SELECT id_impianto, descrizione, latitudine, longitudine, id_palinsesto, is_attivo " +
                "FROM Impianti " +
                "WHERE id_impianto = ? " +
                "AND is_attivo = 1";

        try {
            Impianto impianto = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Impianto>() {
                @Override
                public Impianto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Impianto impianto = new Impianto();
                    impianto.setIdImpianto(rs.getLong("id_impianto"));
                    impianto.setDescrizione(rs.getString("descrizione"));
                    impianto.setLatitudine(rs.getFloat("latitudine"));
                    impianto.setLongitudine(rs.getFloat("longitudine"));
                    impianto.setIsAttivo(rs.getBoolean("is_attivo"));
                    return impianto;
                }
            });
            return Optional.ofNullable(impianto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ImpressioniDto> getImpressioni(LocalDate startDate, LocalDate endDate, Long idImpianto, Long idCartellone, Long idPalinsesto) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
        return segnalazioniRepository.findByFilters(startDateTime, endDateTime, idImpianto, idCartellone, idPalinsesto);
    }
}
