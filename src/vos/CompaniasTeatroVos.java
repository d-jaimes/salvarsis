package vos;

import java.util.Date;

/**
 *
 */
public class CompaniasTeatroVos extends UsuarioVos
{
    private String representante;

    private String paisOrigen;

    private Date fechaLlegada;

    private Date fechaSalida;

    private String pgWeb;

    public CompaniasTeatroVos ()
    {
        super();
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getPgWeb() {
        return pgWeb;
    }

    public void setPgWeb(String pgWeb) {
        this.pgWeb = pgWeb;
    }
}
