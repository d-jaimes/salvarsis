package vos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *
 */
public class FestivandesVos
{
    @JsonProperty("idfest")
    private Long idfest;

    private Date fechast;

    private Date fechaend;

    private String lugar;

   public FestivandesVos(){}
    public Long getIdfest() {
        return idfest;
    }

    public void setIdfest(Long idfest) {
        this.idfest = idfest;
    }

    public Date getFechast() {
        return fechast;
    }

    public void setFechast(Date fechast) {
        this.fechast = fechast;
    }

    public Date getFechaend() {
        return fechaend;
    }

    public void setFechaend(Date fechaend) {
        this.fechaend = fechaend;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
