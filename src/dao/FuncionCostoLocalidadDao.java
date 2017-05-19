package dao;

import vos.FuncionCostoLocalidadVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class FuncionCostoLocalidadDao extends DAO {
    private static final String TABLA = "FUNCION_COSTO_LOCALIDAD";

    public ArrayList<FuncionCostoLocalidadVos> getFuncionCostoLocalidades() throws SQLException {
        ArrayList<FuncionCostoLocalidadVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            FuncionCostoLocalidadVos obj = resultToFuncionCostoLocalidad(rs);
            list.add(obj);
        }
        return list;
    }

    public FuncionCostoLocalidadVos getFuncionCostoLocalidad(Long idFuncion, Long idLocalidad) throws SQLException {
        FuncionCostoLocalidadVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDFUNCION = " + idFuncion;
        sql += " AND IDLOCALIDAD = " + idLocalidad;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToFuncionCostoLocalidad(rs);
        }
        return obj;
    }

    public void addFuncionCostoLocalidad(FuncionCostoLocalidadVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdFuncion() + ", ";
        sql += pObj.getIdLocalidad() + ", ";
        sql += pObj.getIdLocalidad() + ", ";
        sql += pObj.getCosto() + ")";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updateFuncionCostoLocalidad(FuncionCostoLocalidadVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql+= " IDlUGAR = " + pObj.getIdLugar() + ", ";
        sql += "COSTO = " + pObj.getCosto();
        sql += " WHERE IDFUNCION = " + pObj.getIdFuncion();
        sql += " AND IDLOCALIDAD = " + pObj.getIdLocalidad();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteFuncionCostoLocalidad(Long idFuncion, Long idLocalidad) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDFUNCION = " + idFuncion;
        sql += " AND IDLOCALIDAD = " + idLocalidad;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private FuncionCostoLocalidadVos resultToFuncionCostoLocalidad(ResultSet rs) throws SQLException {
        FuncionCostoLocalidadVos obj = new FuncionCostoLocalidadVos();
       obj.setIdFuncion(rs.getLong("IDFUNCION"));
       obj.setIdLugar(rs.getLong("IDlUGAR"));
       obj.setIdLocalidad(rs.getLong("IDLOCALIDAD"));
       obj.setCosto(rs.getDouble("COSTO"));
        return obj;
    }
}
