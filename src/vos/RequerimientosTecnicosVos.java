package vos;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class RequerimientosTecnicosVos
{
    private Long idRequerimientos;
    private String nombre;

    public RequerimientosTecnicosVos() {
    }

    public Long getIdRequerimientos() {
        return idRequerimientos;
    }

    public void setIdRequerimientos(Long idRequerimientos) {
        this.idRequerimientos = idRequerimientos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
