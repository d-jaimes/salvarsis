package vos;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class FuncionCostoLocalidadVos
{
    private Long idFuncion;
    private Long idLugar;
    private Long idLocalidad;
    private double costo;

    public FuncionCostoLocalidadVos() {
    }

    public Long getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Long idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Long getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Long idLugar) {
        this.idLugar = idLugar;
    }

    public Long getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Long idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
