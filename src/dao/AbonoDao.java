package dao;

import vos.AbonoVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 12/04/2017.
 */
public class AbonoDao extends DAO {
    public static final String TABLA = "ABONO";

    public ArrayList<AbonoVos> getAbonos() throws SQLException {
        ArrayList<AbonoVos> list = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            AbonoVos obj = resultToAbono(rs);
            list.add(obj);
        }
        return list;
    }

    public AbonoVos getAbono(Long idusu, String tipoId) throws SQLException {
        AbonoVos obj = null;
        String sql = "SELECT * FROM " + TABLA + " WHERE IDUSU = " + idusu + " AND TIPOID = '" + tipoId+"'";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToAbono(rs);
        }
        return obj;
    }

    public void updateAbono (AbonoVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql+= "DESCUENTO = " + pObj.getDescuento();
        sql +=  "WHERE IDFEST = " + pObj.getIdFest() + " ";
        sql+= "AND IDUSU = " + pObj.getIdusu() + " ";
        sql+= "AND TIPOID = " + "'" + pObj.getTipoId() + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void addAbono (AbonoVos pObj,Long id, String tipo) throws SQLException {
        String sql = new String();
        sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + id;
        sql += " AND TIPOID = '" + tipo + "' ";
        sql += " AND ROL =   'Usuario Registrado'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES ( ";
            sql += pObj.getIdFest() + ", ";
            sql += pObj.getIdusu() + ", '";
            sql += pObj.getTipoId() + "', ";
            sql += pObj.getDescuento() + ")";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }
    }

    public void deleteAbono (Long idusu, String tipoId) throws SQLException
    {
        String sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + idusu;
        sql += " AND TIPOID = '" + tipoId + "'";
        sql += " AND ROL =   'Usuario Registrado'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();

        if (rs.next()) {

            sql = "DELETE FROM " + TABLA + " ";
            sql += "WHERE IDUSU = " + idusu + " ";
            sql += "AND TIPOID = '" + tipoId + "'";
            System.out.println(sql);

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }
    }

    private AbonoVos resultToAbono(ResultSet rs) throws SQLException {
        AbonoVos obj = new AbonoVos();
        obj.setIdFest(rs.getLong("IDFEST"));
        obj.setIdusu(rs.getLong("IDUSU"));
        obj.setTipoId(rs.getString("TIPOID"));
        obj.setDescuento(rs.getDouble("DESCUENTO"));
        return obj;
    }
}

