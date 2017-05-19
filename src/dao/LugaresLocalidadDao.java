package dao;

import vos.LugaresLocalidadVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class LugaresLocalidadDao extends DAO {
    private static final String TABLA = "LUGARES_LOCALIDAD";

    public ArrayList<LugaresLocalidadVos> getLugaresLocalidades() throws SQLException {
        ArrayList<LugaresLocalidadVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            LugaresLocalidadVos obj = resultToLugaresLocalidad(rs);
            list.add(obj);
        }
        return list;
    }

    public LugaresLocalidadVos getLugaresLocalidad(Long idLugares, Long idLocalidad) throws SQLException {
        LugaresLocalidadVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDLUGARES = " + idLugares;
        sql += "AND IDLOCALIDAD = " + idLocalidad;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToLugaresLocalidad(rs);
        }
        return obj;
    }

    public void addLugaresLocalidad(LugaresLocalidadVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdLugare() + ", ";
        sql += pObj.getIdLocalidad() + ", ";
        if (pObj.isEsNumerado())
        {
            sql += 1 + ", ";
        }
        else
        {
            sql += 0 + ", ";
        }
        sql += pObj.getCapacidad() + ")";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void updateLugaresLocalidad(LugaresLocalidadVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        if (pObj.isEsNumerado())
        {
            sql += "ESNUMERADO = " + 1 + ", ";
        }
        else
        {
            sql += "ESNUMERADO = " + 0 +", ";
        }
        sql += "CAPACIDAD = " + pObj.getCapacidad() + " ";
        sql += "WHERE IDLUGARES = " + pObj.getIdLugare() + " ";
        sql += "AND IDLOCALIDAD = " +pObj.getIdLocalidad();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteLugaresLocalidad(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDLUGARES = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private LugaresLocalidadVos resultToLugaresLocalidad(ResultSet rs) throws SQLException {
        LugaresLocalidadVos obj = new LugaresLocalidadVos();
       obj.setIdLugare(rs.getLong("IDLUGARES"));
       obj.setIdLocalidad(rs.getLong("IDLOCALIDAD"));
       if(rs.getInt("ESNUMERADO")==1){

           obj.setEsNumerado(true);
       }
       else
       {
           obj.setEsNumerado(false);
       }
       obj.setCapacidad(rs.getInt("CAPACIDAD"));

        return obj;

    }
}