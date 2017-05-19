package dao;

import vos.PreferenciaClienteVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class PreferenciaClienteDao extends DAO {
    private static final String TABLA = "PREFERENCIA_CLIENTE";

    public ArrayList<PreferenciaClienteVos> getPreferencias() throws SQLException {
        ArrayList<PreferenciaClienteVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            PreferenciaClienteVos obj = resultToPreferencia(rs);
            list.add(obj);
        }
        return list;
    }

    public PreferenciaClienteVos getPreferencia(Long idPreferencia,  Long idusu, String tipoId, String tipoPref) throws SQLException {
        PreferenciaClienteVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDPREFERENCIA = " + idPreferencia;
        sql += " AND IDUSU = " + idusu + ", ";
        sql += "AND TIPOID = '" + tipoId + "', ";
        sql += "AND TIPOPREF = '" + tipoPref + "', ";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToPreferencia(rs);
        }
        return obj;
    }

    //TODO listo (?)
    public void addPreferencia(Long id, String tipo, PreferenciaClienteVos pObj) throws SQLException
    {
        String sql = ("SELECT * ");
        sql += ("FROM CLIENTE_REGISTRADO ");
        sql += "WHERE IDUSU =  " + id;
        sql += " AND TIPOID = '" + tipo + "' ";
        sql += " AND rol =   'Usuario Registrado'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdusu() + ", '";
            sql += pObj.getTipoiD() + "', ";
            sql += pObj.getIdPreferencia() + ", '";
            sql += pObj.getTipoPreferencia() + "')";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }


    }

    public void deletePreferencia(Long idPreferencia,  Long idusu, String tipoId, String tipoPref) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += "WHERE IDPREFERENCIA = " + idPreferencia;
        sql += " AND IDUSU = " + idusu + ", ";
        sql += "AND TIPOID = '" + tipoId + "', ";
        sql += "AND TIPOPREF = '" + tipoPref + "', ";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private PreferenciaClienteVos resultToPreferencia(ResultSet rs) throws SQLException {
        PreferenciaClienteVos obj = new PreferenciaClienteVos();
        obj.setIdusu(rs.getLong("IDUSU"));
        obj.setTipoiD(rs.getString("TIPOID"));
        obj.setIdPreferencia(rs.getLong("IDPREFERENCIA"));
        obj.setTipoPreferencia(rs.getString("TIPOPREF"));
        return obj;

    }
}
