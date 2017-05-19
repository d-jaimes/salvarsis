package vos;

import java.util.Date;

/**
 *
 */
public class LugaresVos
{
    private Long idLugar;

    private String nombre;

    private Date avalabilitySt;

    private Date avalabilityEnd;

    private boolean rainProtection;

    private Integer capacidad;

    private String tipoSilleteria;

    public LugaresVos () {}

    public Long getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Long idLugar) {
        this.idLugar = idLugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getAvalabilitySt() {
        return avalabilitySt;
    }

    public void setAvalabilitySt(Date avalabilitySt) {
        this.avalabilitySt = avalabilitySt;
    }

    public Date getAvalabilityEnd() {
        return avalabilityEnd;
    }

    public void setAvalabilityEnd(Date avalabilityEnd) {
        this.avalabilityEnd = avalabilityEnd;
    }

    public boolean isRainProtection() {
        return rainProtection;
    }

    public void setRainProtection(boolean rainProtection) {
        this.rainProtection = rainProtection;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipoSilleteria() {
        return tipoSilleteria;
    }

    public void setTipoSilleteria(String tipoSilleteria) {
        this.tipoSilleteria = tipoSilleteria;
    }
}
