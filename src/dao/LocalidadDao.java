package dao;

import vos.LocalidadVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class LocalidadDao extends DAO {
    private static final String TABLA = "LOCALIDAD";

    public ArrayList<LocalidadVos> getLocalidades() throws SQLException {
        ArrayList<LocalidadVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            LocalidadVos obj = resultToLocalidad(rs);
            list.add(obj);
        }
        return list;
    }

    public LocalidadVos getLocalidad(Long id) throws SQLException {
        LocalidadVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDLOCALIDAD = " + id;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToLocalidad(rs);
        }
        return obj;
    }

    public void addLocalidad(LocalidadVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdLocalidad() + ", '";
        sql += pObj.getTipo() + "' )";


        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void updateLocalidad(LocalidadVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "TIPO = '" + pObj.getTipo() + "' ";
        sql += "WHERE IDLOCALIDAD = " + pObj.getIdLocalidad();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteLocalidad(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDLOCALIDAD = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private LocalidadVos resultToLocalidad(ResultSet rs) throws SQLException {
        LocalidadVos obj = new LocalidadVos();
        obj.setIdLocalidad(rs.getLong("IDLOCALIDAD"));
        obj.setTipo(rs.getString("TIPO"));
        return obj;

    }
//TODO
    public LocalidadVos searchLocalidad(String nombreLocalidad) throws SQLException {
        LocalidadVos localidad = null;

        StringBuilder sql = new StringBuilder( );
        sql.append( "SELECT * " );
        sql.append( "  FROM " + TABLA );
        sql.append( String.format( "WHERE TIPO = '%s' ", nombreLocalidad ) );

        PreparedStatement s = conn.prepareStatement( sql.toString( ) );
        ResultSet rs = s.executeQuery( );
        if( rs.next( ) )
        {
            localidad = resultToLocalidad( rs );
        }
        rs.close( );
        s.close( );
        return localidad;
    }
}