package vos;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class PreferenciaClienteVos
{
    private Long idusu;
    private Long idPreferencia;
    private String tipoPreferencia;
    private String tipoiD;

    public PreferenciaClienteVos() {
    }

    public Long getIdusu() {
        return idusu;
    }

    public void setIdusu(Long idusu) {
        this.idusu = idusu;
    }

    public Long getIdPreferencia() {
        return idPreferencia;
    }

    public void setIdPreferencia(Long idPreferencia) {
        this.idPreferencia = idPreferencia;
    }

    public String getTipoPreferencia() {
        return tipoPreferencia;
    }

    public void setTipoPreferencia(String tipoPreferencia) {
        this.tipoPreferencia = tipoPreferencia;
    }

    public String getTipoiD() {
        return tipoiD;
    }

    public void setTipoiD(String tipoiD) {
        this.tipoiD = tipoiD;
    }
}
