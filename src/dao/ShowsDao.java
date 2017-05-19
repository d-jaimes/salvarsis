package dao;

import vos.ShowsVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class ShowsDao extends DAO {
    private static final String TABLA = "SHOWS";

    public ArrayList<ShowsVos> getShows() throws SQLException {
        ArrayList<ShowsVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            ShowsVos obj = resultToShow(rs);
            list.add(obj);
        }
        return list;
    }

    public ShowsVos getShow(Long id) throws SQLException {
        ShowsVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDSHOWS ='" + id + "'";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToShow(rs);
        }
        return obj;
    }

    public void addShow(Long id, String tipoId, ShowsVos pObj) throws SQLException {

        String sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + id;
        sql += " AND TIPOID = '" + tipoId + "' ";
        sql += " AND rol =   'Administrador'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();

        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdShows() + ", '";
            sql += pObj.getNombre() + "', '";
            sql += pObj.getDescripcion() + "', ";
            sql += pObj.getDuracion() + ", '";
            sql += pObj.getIdioma() + "', ";
            sql += pObj.getPublicoAdmitido() + ", ";
            sql += pObj.getCosto() + ", ";
            if (pObj.isParticipacionPublico()) {
                sql += 1 + ", ";
            } else {
                sql += 0 + ", ";
            }
            sql += pObj.getIdFest() + ")";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }

    }

    public void updateShow(ShowsVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "NOMBRE = '" + pObj.getNombre() + "', ";
        sql += "DESCRIPCION = '" + pObj.getDescripcion() + "', ";
        sql += "DURATION = " + pObj.getDuracion() + ", ";
        sql += "IDIOMA = '" + pObj.getIdioma() + "', ";
        sql += "PUBLICOADMITIDO = " + pObj.getPublicoAdmitido() + ", ";
        sql += "COSTO = " + pObj.getCosto() + ", ";
        if (pObj.isParticipacionPublico()) {
            sql += "PARTICIPACION_PUBLICO =" + 1 + ", ";
        } else {
            sql += "PARTICIPACION_PUBLICO =" + 0 + ", ";
        }
        sql += "IDFEST = " + pObj.getIdFest() + " ";
        sql += "WHERE IDSHOWS = " + pObj.getIdShows();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteShow(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDSHOWS = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private ShowsVos resultToShow(ResultSet rs) throws SQLException {
        ShowsVos obj = new ShowsVos();
        obj.setIdShows(rs.getLong("IDSHOWS"));
        obj.setNombre(rs.getString("NOMBRE"));
        obj.setDescripcion(rs.getString("DESCRIPCION"));
        obj.setDuracion(rs.getLong("DURATION"));
        obj.setIdioma(rs.getString("IDIOMA"));
        obj.setPublicoAdmitido(rs.getLong("PUBLICOADMITIDO"));
        obj.setCosto(rs.getDouble("COSTO"));
        if (rs.getInt("PARTICIPACION_PUBLICO") == 1) {
            obj.setParticipacionPublico(true);
        } else {
            obj.setParticipacionPublico(false);
        }
        obj.setIdFest(rs.getLong("IDFEST"));
        return obj;

    }
}