package vos;

/**
 *
 */
public class AbonoFuncionVos
{
    private Long idFest;
    private Long idusu;
    private String tipoId;
    private Long idFuncion;
    private long idLocalidad;

    public AbonoFuncionVos() {
    }

    public Long getIdFest() {
        return idFest;
    }

    public void setIdFest(Long idFest) {
        this.idFest = idFest;
    }

    public Long getIdusu() {
        return idusu;
    }

    public void setIdusu(Long idusu) {
        this.idusu = idusu;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public Long getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Long idFuncion) {
        this.idFuncion = idFuncion;
    }

    public long getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(long idLocalidad) {
        this.idLocalidad = idLocalidad;
    }
}
