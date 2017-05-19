package vos;

/**
 *
 */
public class LugaresLocalidadVos
{
    private Long idLugare;
    private Long idLocalidad;
    private boolean esNumerado;
    private Integer capacidad;

    public LugaresLocalidadVos (){}

    public Long getIdLugare() {
        return idLugare;
    }

    public void setIdLugare(Long idLugare) {
        this.idLugare = idLugare;
    }

    public Long getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Long idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public boolean isEsNumerado() {
        return esNumerado;
    }

    public void setEsNumerado(boolean esNumerado) {
        this.esNumerado = esNumerado;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}
