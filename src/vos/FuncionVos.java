package vos;

import java.util.Date;

/**
 *
 */
public class FuncionVos
{
    private Long idFuncion;
    private Date fecha;
    private Long idShow;
    private Long idLugar;
    private Integer done;

    public FuncionVos (){}

    public Long getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Long idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdShow() {
        return idShow;
    }

    public void setIdShow(Long idShow) {
        this.idShow = idShow;
    }

    public Long getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Long idLugar) {
        this.idLugar = idLugar;
    }

    public Integer isDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }
}
