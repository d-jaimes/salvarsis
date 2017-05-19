package vos;

import java.util.Date;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class ReservaVos
{
    private Long idReserva;
    private Date fechaCompra;
    private Long idFuncion;
    private Integer fila;
    private Integer polugares;
    private Long idLocalidad;
    private Long idLugares;
    private Long idUsu;
    private String tipoId;

    public ReservaVos() {
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Long getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Long idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getPolugares() {
        return polugares;
    }

    public void setPolugares(Integer polugares) {
        this.polugares = polugares;
    }

    public Long getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Long idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public Long getIdLugares() {
        return idLugares;
    }

    public void setIdLugares(Long idLugares) {
        this.idLugares = idLugares;
    }

    public Long getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(Long idUsu) {
        this.idUsu = idUsu;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }
}
