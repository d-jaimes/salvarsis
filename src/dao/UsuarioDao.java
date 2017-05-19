package dao;

import vos.UsuarioVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class UsuarioDao extends DAO {
    private static final String TABLA = "USUARIO";

    public ArrayList<UsuarioVos> getUsuarios() throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            UsuarioVos obj = resultToUsuario(rs);
            list.add(obj);
        }
        return list;
    }

    public UsuarioVos getUsuario(Long id, String tipoId) throws SQLException {
        UsuarioVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDUSU = " + id + " AND TIPOID = '" + tipoId + "'";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToUsuario(rs);
        }
        return obj;
    }

    public void addUsuario(UsuarioVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql +=  pObj.getIdusu() + ", '";
        sql += pObj.getTipoId() + "', '";
        sql += pObj.getNombre() + "' , '";
        sql += pObj.getEmail() + "', '";
        sql += pObj.getPassword() + "', '";
        sql += pObj.getRol() + "', ";
        sql += pObj.getIdfest() + ")";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void updateUsuario(UsuarioVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "NAME= '" + pObj.getNombre()+ "', ";
        sql += "EMAIL= '" + pObj.getEmail()+ "', ";
        sql += "PASSWORD= '" + pObj.getPassword() + "', ";
        sql += "ROL= '" + pObj.getRol() + "', ";
        sql += "IDFEST= " + pObj.getIdfest();
        sql += " WHERE IDUSU = " + pObj.getIdusu()+ " ";
        sql += " AND TIPOID = '" + pObj.getTipoId() + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteUsuario(Long id, String tipoId) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDUSU = " + id + " ";
        sql += "AND TIPOID = '" + tipoId + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    public static UsuarioVos resultToUsuario(ResultSet rs) throws SQLException {
        UsuarioVos obj = new UsuarioVos();
        obj.setIdusu(rs.getLong("IDUSU"));
        obj.setTipoId(rs.getString("TIPOID"));
        obj.setNombre(rs.getString("NAME"));
        obj.setEmail(rs.getString("EMAIL"));
        obj.setPassword(rs.getString("PASSWORD"));
        obj.setRol(rs.getString("ROL"));
        obj.setIdfest(rs.getLong("IDFEST"));
        return obj;

    }
}
