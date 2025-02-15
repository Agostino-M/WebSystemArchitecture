package com.websystem.gestioneimpianti.model;

public class ImpressioniDto {

    private String descrizioneImpianto;
    private String nomePalinsesto;
    private String nomeCartellone;
    private Long durataTot;
    private Long nImpressioni;

    public ImpressioniDto() {
    }

    public ImpressioniDto(String descrizioneImpianto, String nomePalinsesto, String nomeCartellone, Long durataTot, Long nImpressioni) {
        this.descrizioneImpianto = descrizioneImpianto;
        this.nomePalinsesto = nomePalinsesto;
        this.nomeCartellone = nomeCartellone;
        this.durataTot = durataTot;
        this.nImpressioni = nImpressioni;
    }

    public String getDescrizioneImpianto() {
        return descrizioneImpianto;
    }

    public void setDescrizioneImpianto(String descrizioneImpianto) {
        this.descrizioneImpianto = descrizioneImpianto;
    }

    public String getNomePalinsesto() {
        return nomePalinsesto;
    }

    public void setNomePalinsesto(String nomePalinsesto) {
        this.nomePalinsesto = nomePalinsesto;
    }

    public String getNomeCartellone() {
        return nomeCartellone;
    }

    public void setNomeCartellone(String nomeCartellone) {
        this.nomeCartellone = nomeCartellone;
    }

    public Long getDurataTot() {
        return durataTot;
    }

    public void setDurataTot(Long durataTot) {
        this.durataTot = durataTot;
    }

    public Long getnImpressioni() {
        return nImpressioni;
    }

    public void setnImpressioni(Long nImpressioni) {
        this.nImpressioni = nImpressioni;
    }

    @Override
    public String toString() {
        return "ImpressioniDto{" +
                "descrizioneImpianto='" + descrizioneImpianto + '\'' +
                ", nomePalinsesto='" + nomePalinsesto + '\'' +
                ", nomeCartellone='" + nomeCartellone + '\'' +
                ", durataTot=" + durataTot +
                ", nImpressioni=" + nImpressioni +
                '}';
    }
}
