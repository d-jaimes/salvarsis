package dao;

import vos.LugaresVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class LugaresDao extends DAO {
    private static final String TABLA = "LUGARES";

    public ArrayList<LugaresVos> getLugares() throws SQLException {
        ArrayList<LugaresVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            LugaresVos obj = resultToLugar(rs);
            list.add(obj);
        }
        return list;
    }

    public LugaresVos getLugar(Long id) throws SQLException {
        LugaresVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDLUGAR =" + id ;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToLugar(rs);
        }
        return obj;
    }

    public void addLugar(LugaresVos pObj,Long id, String tipo) throws SQLException
    {
        String sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " +id;
        sql += " AND TIPOID = '" + tipo+"'" ;
        sql += " AND rol = 'Administrador' ";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdLugar() + ", '";
            sql += pObj.getNombre() + "', ";
            sql += dateToSting(pObj.getAvalabilitySt()) + ", ";
            sql += dateToSting(pObj.getAvalabilityEnd()) + ", ";
            sql += pObj.isRainProtection() ? "1 , " : "0, ";
            sql += pObj.getCapacidad() + ", '";
            sql += pObj.getTipoSilleteria() + "') ";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }

    }

    public void updateLugar(LugaresVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "NOMBRE = '" + pObj.getNombre() + "', ";
        sql += "AVAILABILITYST = " + dateToSting(pObj.getAvalabilitySt()) + ", ";
        sql += "AVAILABILITYEND = " + dateToSting(pObj.getAvalabilityEnd()) + ", ";
        if (pObj.isRainProtection()) {
            sql += "RAINPROTECTION = " + 1 + ", ";
        }
        else
        {
            sql += "RAINPROTECTION = " + 0 + ", ";
        }
        sql += "CAPACIDAD = " + pObj.getCapacidad() + ", ";
        sql += "TIPOSILLETERIA = '" + pObj.getTipoSilleteria() + "' ";
        sql +="WHERE IDLUGAR = " + pObj.getIdLugar();

        System.out.println( sql );

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteLugar(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDLUGAR = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private LugaresVos resultToLugar(ResultSet rs) throws SQLException {
        LugaresVos obj = new LugaresVos();
        obj.setIdLugar(rs.getLong("IDLUGAR"));
        obj.setNombre(rs.getString("NOMBRE"));
        obj.setAvalabilitySt(rs.getDate("AVAILABILITYST"));
        obj.setAvalabilityEnd(rs.getDate("AVAILABILITYEND"));
        if(rs.getInt("RAINPROTECTION")== 1)
        {
            obj.setRainProtection(true);
        }
        else
        {
            obj.setRainProtection(false);
        }

        obj.setCapacidad(rs.getInt("CAPACIDAD"));
        obj.setTipoSilleteria(rs.getString("TIPOSILLETERIA"));

        return obj;

    }
}