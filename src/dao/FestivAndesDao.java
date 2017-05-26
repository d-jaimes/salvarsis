package dao;

import protocolos.ProtocoloFestival;
import vos.FestivandesVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class FestivAndesDao extends DAO {
    private static final String TABLA = "FESTIVANDES";

    public ArrayList<FestivandesVos> getFestivales() throws SQLException {
        ArrayList<FestivandesVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            FestivandesVos obj = resultToFestival(rs);
            list.add(obj);
        }
        return list;
    }

    public FestivandesVos getFestival(Long id) throws SQLException {
        FestivandesVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDFEST = " + id;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToFestival(rs);
        }
        return obj;
    }

    public void addFestival(FestivandesVos pObj) throws SQLException {
        String sql = "INSERT INTO " + TABLA + " VALUES ( ";
        sql += pObj.getIdfest() + ", ";
        sql += dateToSting(pObj.getFechast()) + ", ";
        sql += dateToSting(pObj.getFechaend()) + ", '";
        sql += pObj.getLugar() + "' )";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void updateFestival(FestivandesVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "FECHAST= " + dateToSting(pObj.getFechast()) + ", ";
        sql += "FECHAEND= " + dateToSting(pObj.getFechaend()) + ", ";
        sql += "LUGAR= '" + pObj.getLugar() + "' ";
        sql += "WHERE IDFEST = " + pObj.getIdfest();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteFestival(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA + " ";
        sql += "WHERE IDFEST = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private FestivandesVos resultToFestival(ResultSet rs) throws SQLException {
        FestivandesVos obj = new FestivandesVos();
        obj.setIdfest(rs.getLong("IDFEST"));
        obj.setFechast(rs.getDate("FECHAST"));
        obj.setFechaend(rs.getDate("FECHAEND"));
        obj.setLugar(rs.getString("LUGAR"));
        return obj;
    }

    public FestivandesVos searchFestival(String nombreFestival) throws SQLException {
        FestivandesVos festival = null;

        StringBuilder sql = new StringBuilder( );
        sql.append( "SELECT * " );
        sql.append( "  FROM FESTIVANDES " );
        sql.append( String.format( "WHERE NOMBRE = '%s' ", nombreFestival ) );

        PreparedStatement s = conn.prepareStatement( sql.toString( ) );
        ResultSet rs = s.executeQuery( );
        if( rs.next( ) )
        {
            festival = resultToFestival( rs );
        }

        rs.close( );
        s.close( );
        return festival;
    }
}
