package dao;

import vos.CategoriaVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class CategoriaDao extends DAO {
    private static final String TABLA = "CATEGORIA";

    public ArrayList<CategoriaVos> getCategorias() throws SQLException {
        ArrayList<CategoriaVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            CategoriaVos obj = resultToCategoria(rs);
            list.add(obj);
        }
        return list;
    }

    public CategoriaVos getCategoria(Long id) throws SQLException {
        CategoriaVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDCATEGORIA = " + id;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToCategoria(rs);
        }
        return obj;
    }

    public void addCategoria(CategoriaVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdCategoria() + ", '";
        sql += pObj.getNombre() + "')";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updateCategoria(CategoriaVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "NOMBRE = '" + pObj.getNombre();
        sql += "' WHERE IDCATEGORIA = " + pObj.getIdCategoria();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteCategoria(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDCATEGORIA = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private CategoriaVos resultToCategoria(ResultSet rs) throws SQLException {
        CategoriaVos obj = new CategoriaVos();
       obj.setIdCategoria(rs.getLong("IDCATEGORIA"));
       obj.setNombre(rs.getString("NOMBRE"));
        return obj;

    }
}