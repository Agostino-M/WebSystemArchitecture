package com.websystem.gestioneimpianti.model;

public class ImpiantoPalinsesto {

    private Long idImpianto;
    private String descrizione;
    private float latitudine;
    private float longitudine;
    private boolean isAttivo;
    private Long idPalinsesto;
    private String nomePalinsesto;
    private String path;


    public ImpiantoPalinsesto() {
    }

    public ImpiantoPalinsesto(Long idImpianto, String descrizione, float latitudine, float longitudine, boolean isAttivo, Long idPalinsesto, String nomePalinsesto, String path) {
        this.idImpianto = idImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.isAttivo = isAttivo;
        this.idPalinsesto = idPalinsesto;
        this.nomePalinsesto = nomePalinsesto;
        this.path = path;
    }

    public Long getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(Long idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    public boolean isAttivo() {
        return isAttivo;
    }

    public void setAttivo(boolean attivo) {
        isAttivo = attivo;
    }

    public Long getIdPalinsesto() {
        return idPalinsesto;
    }

    public void setIdPalinsesto(Long idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public String getNomePalinsesto() {
        return nomePalinsesto;
    }

    public void setNomePalinsesto(String nomePalinsesto) {
        this.nomePalinsesto = nomePalinsesto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ImpiantoPalinsesto{" +
                "idImpianto=" + idImpianto +
                ", descrizione='" + descrizione + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", isAttivo=" + isAttivo +
                ", idPalinsesto=" + idPalinsesto +
                ", nomePalinsesto='" + nomePalinsesto + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
