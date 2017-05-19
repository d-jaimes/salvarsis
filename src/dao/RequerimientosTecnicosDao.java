package dao;

import vos.RequerimientosTecnicosVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class RequerimientosTecnicosDao extends DAO {
    private static final String TABLA = "REQTECNICOS";

    public ArrayList<RequerimientosTecnicosVos> getReqTecnicos() throws SQLException {
        ArrayList<RequerimientosTecnicosVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            RequerimientosTecnicosVos obj = resultToReqTecnico(rs);
            list.add(obj);
        }
        return list;
    }

    public RequerimientosTecnicosVos getReqTecnico(Long id) throws SQLException {
        RequerimientosTecnicosVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDREQUERIMIENTOS = " + id;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToReqTecnico(rs);
        }
        return obj;
    }

    public void addReqTecnico(RequerimientosTecnicosVos pObj) throws SQLException {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdRequerimientos() + ", '";
        sql += pObj.getNombre() + "')";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updateReqTecnico(RequerimientosTecnicosVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "NOMBRE = '" + pObj.getNombre() + "' ";
        sql += "WHERE IDREQUERIMIENTOS = " + pObj.getIdRequerimientos();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteReqTecnico(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDREQUERIMIENTOS = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private RequerimientosTecnicosVos resultToReqTecnico(ResultSet rs) throws SQLException {
        RequerimientosTecnicosVos obj = new RequerimientosTecnicosVos();
        obj.setIdRequerimientos(rs.getLong("IDREQUERIMIENTOS"));
        obj.setNombre(rs.getString("NOMBRE"));
        return obj;

    }
}