package com.websystem.gestioneimpianti.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Segnalazioni")
public class Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_segnalazione")
    private Long idSegnalazione;

    @Column(name = "id_impianto")
    private Long idImpianto;

    @Column(name = "id_palinsesto")
    private Long idPalinsesto;

    @Column(name = "id_cartellone")
    private Long idCartellone;

    @Column(name = "durata")
    private int durata;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Segnalazione() {
    }

    public Segnalazione(Long idSegnalazione, Long idImpianto, Long idPalinsesto, Long idCartellone, int durata, LocalDateTime timestamp) {
        this.idSegnalazione = idSegnalazione;
        this.idImpianto = idImpianto;
        this.idPalinsesto = idPalinsesto;
        this.idCartellone = idCartellone;
        this.durata = durata;
        this.timestamp = timestamp;
    }

    public Long getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(Long idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public Long getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(Long idImpianto) {
        this.idImpianto = idImpianto;
    }

    public Long getIdPalinsesto() {
        return idPalinsesto;
    }

    public void setIdPalinsesto(Long idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public Long getIdCartellone() {
        return idCartellone;
    }

    public void setIdCartellone(Long idCartellone) {
        this.idCartellone = idCartellone;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
