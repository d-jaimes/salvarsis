package dao;

import vos.SillaVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class SillaDao extends DAO {
    private static final String TABLA = "SILLA";

    public ArrayList<SillaVos> getSillas() throws SQLException {
        ArrayList<SillaVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            SillaVos obj = resultToSilla(rs);
            list.add(obj);
        }
        return list;
    }

    public SillaVos getSilla(Integer fila, Integer numero) throws SQLException {
        SillaVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE FILA = " + fila;
        sql += "AND NUMERO = " + numero;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToSilla(rs);
        }
        return obj;
    }

    public void addSilla(SillaVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getFila() + ", ";
        sql += pObj.getNumero() + ", ";
        sql += pObj.getIdLocalidad() + ", ";
        sql += pObj.getIdLugar() + ")";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updateSilla(SillaVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "IDLOCALIDAD = " + pObj.getIdLocalidad() + ", ";
        sql += "IDLUGAR = " + pObj.getIdLugar() + " ";
        sql += "WHERE FILA = " + pObj.getFila() + " ";
        sql += "AND NUMERO = " + pObj.getNumero();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteSilla(Integer fila, Integer numero) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE FILA = " + fila;
        sql += "AND NUMERO = " + numero;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    public static SillaVos resultToSilla(ResultSet rs) throws SQLException {
        SillaVos obj = new SillaVos();
       obj.setFila(rs.getInt("FILA"));
       obj.setNumero(rs.getInt("NUMERO"));
       obj.setIdLocalidad(rs.getLong("IDLOCALIDAD"));
       obj.setIdLugar(rs.getLong("IDLUGAR"));
        return obj;

    }
}