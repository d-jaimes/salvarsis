package vos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AbonoVos {

    private Long idFest;

    private Long idusu;

    private String tipoId;

    private double descuento;

    private ArrayList <FuncionCostoLocalidadVos> funciones;

    public AbonoVos()
    {
        funciones = new ArrayList<>();
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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public ArrayList<FuncionCostoLocalidadVos> getFunciones() {
        return funciones;
    }

    public void setFunciones(ArrayList<FuncionCostoLocalidadVos> funciones) {
        this.funciones = funciones;
    }
}
