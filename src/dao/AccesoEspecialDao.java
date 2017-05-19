package dao;

import vos.AccesoEspecialVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class AccesoEspecialDao extends DAO {
    private static final String TABLA = "ACCESO_ESPECIAL";

    public ArrayList<AccesoEspecialVos> getAccesoEspeciales() throws SQLException {
        ArrayList<AccesoEspecialVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            AccesoEspecialVos obj = resultToAccesoEspecial(rs);
            list.add(obj);
        }
        return list;
    }

    public AccesoEspecialVos getAccesoEspecial(Long id) throws SQLException {
        AccesoEspecialVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE ID_ACCESO_ESPECIAL = " + id;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToAccesoEspecial(rs);
        }
        return obj;
    }

    public void addAccesoEspecial(AccesoEspecialVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdAccesoEspecial() + ", '";
        sql += pObj.getTipoAccesoEspecial() + "')";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updateAccesoEspecial(AccesoEspecialVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "TIPO_ACCESO = '" + pObj.getTipoAccesoEspecial() + "' ";
        sql += "WHERE ID_ACCESO_ESPECIAL = " + pObj.getIdAccesoEspecial();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteAccesoEspecial(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE ID_ACCESO_ESPECIAL = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private AccesoEspecialVos resultToAccesoEspecial(ResultSet rs) throws SQLException {
        AccesoEspecialVos obj = new AccesoEspecialVos();
       obj.setIdAccesoEspecial(rs.getLong("ID_ACCESO_ESPECIAL"));
       obj.setTipoAccesoEspecial(rs.getString("TIPO_ACCESO"));
        return obj;

    }
}