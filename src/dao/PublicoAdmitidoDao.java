package dao;

import vos.PublicoAdmitidoVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class PublicoAdmitidoDao extends DAO {
    private static final String TABLA = "PUBLICO_ADMITIDO";

    public ArrayList<PublicoAdmitidoVos> getPublicoAdmitidos() throws SQLException {
        ArrayList<PublicoAdmitidoVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            PublicoAdmitidoVos obj = resultToPublicoAdmitido(rs);
            list.add(obj);
        }
        return list;
    }

    public PublicoAdmitidoVos getPublicoAdmitido(Long id) throws SQLException {
        PublicoAdmitidoVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDPUBLICO ='" + id + "'";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToPublicoAdmitido(rs);
        }
        return obj;
    }

    public void addPublicoAdmitido(PublicoAdmitidoVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdPublico()+ ", '";
        sql += pObj.getCategoria() + "')";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updatePublicoAdmitido(PublicoAdmitidoVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "CATEGORIA = '" + pObj.getCategoria() + "'";
        sql += "WHERE IDPUBLICO = " + pObj.getIdPublico();
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deletePublicoAdmitido(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDPUBLICO = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private PublicoAdmitidoVos resultToPublicoAdmitido(ResultSet rs) throws SQLException {
        PublicoAdmitidoVos obj = new PublicoAdmitidoVos();
        obj.setIdPublico(rs.getLong("IDPUBLICO"));
        obj.setCategoria(rs.getString("CATEGORIA"));
        return obj;

    }
}