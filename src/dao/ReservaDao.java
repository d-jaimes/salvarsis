package dao;

import tm.UsuarioCM;
import vos.ReservaVos;
import vos.SillaVos;
import vos.UsuarioVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class ReservaDao extends DAO {
    private static final String TABLA = "RESERVA";

    public ArrayList<ReservaVos> getReservas() throws SQLException {
        ArrayList<ReservaVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            ReservaVos obj = resultToReserva(rs);
            list.add(obj);
        }
        return list;
    }

    public ReservaVos getReserva(Long idReserva, Long idusu, String tipoId) throws SQLException {
        ReservaVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDRESERVA = " + idReserva;
        sql += " AND IDUSU = " + idusu;
        sql += " AND TIPOID = '" + tipoId + "'";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToReserva(rs);
        }
        return obj;
    }


    public void addReserva(ReservaVos pObj, Long id, String tipo) throws SQLException {
        String sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + id;
        sql += " AND TIPOID ='" + tipo + "'";
        sql += " AND ROL = 'Usuario Registrado' OR ROL='Usuario No-Registrado'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdReserva() + ", ";
            sql += dateToSting(pObj.getFechaCompra()) + ", ";
            sql += pObj.getIdFuncion() + ", ";
            sql += pObj.getFila() + ", ";
            sql += pObj.getPolugares() + ", ";
            sql += pObj.getIdLocalidad() + ", ";
            sql += pObj.getIdLugares() + ", ";
            sql += pObj.getIdUsu() + ", '";
            sql += pObj.getTipoId() + "')";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }


    }

    public void updateReserva(ReservaVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "FECHACOMPRA = " + dateToSting(pObj.getFechaCompra()) + ", ";
        sql += "IDFUNCION = " + pObj.getIdFuncion() + ", ";
        sql += "FILA = " + pObj.getFila() + ", ";
        sql += "POLUGARESN = " + pObj.getPolugares() + ", ";
        sql += "IDLOCALIDAD = " + pObj.getIdLocalidad() + ", ";
        sql += "IDLUGARES = " + pObj.getIdLugares() + " ";
        sql += "WHERE IDRESERVA = " + pObj.getIdReserva();
        sql += " AND IDUSU = " + pObj.getIdUsu();
        sql += " AND TIPOID = '" + pObj.getTipoId() + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    public void deleteReserva(Long idResrva, Long idusu, String tipoId) throws SQLException {
        String sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + idusu;
        sql += " AND TIPOID ='" + tipoId + "'";
        sql += " AND ROL = 'Usuario Registrado' OR ROL='Usuario No-Registrado'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {

            sql = "DELETE FROM " + TABLA;
            sql += " WHERE IDRESERVA = " + idResrva;
            sql += " AND IDUSU = " + idusu;
            sql += " AND TIPOID = '" + tipoId + "'";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }
    }


    private ReservaVos resultToReserva(ResultSet rs) throws SQLException {
        ReservaVos obj = new ReservaVos();
        obj.setIdReserva(rs.getLong("IDRESERVA"));
        obj.setFechaCompra(rs.getDate("FECHACOMPRA"));
        obj.setIdFuncion(rs.getLong("IDFUNCION"));
        obj.setFila(rs.getInt("FILA"));
        obj.setPolugares(rs.getInt("POLUGARESN"));
        obj.setIdLocalidad(rs.getLong("IDLOCALIDAD"));
        obj.setIdLugares(rs.getLong("IDLUGARES"));
        obj.setIdUsu(rs.getLong("IDUSU"));
        obj.setTipoId(rs.getString("TIPOID"));
        return obj;

    }

    public ArrayList<SillaVos> reservaMultiple(Long idusu, String tipo, Long idLocalidad, Long idFuncion) throws SQLException {

        ArrayList<SillaVos> list = new ArrayList<>();
        String sql = new String();
        sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + idusu;
        sql += " AND TIPOID ='" + tipo + "'";
        sql += " AND ROL = 'Usuario Registrado'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "SELECT S.* ";
            sql += "FROM (SELECT * ";
            sql += "FROM (SELECT * ";
            sql += "FROM SILLA ";
            sql += "WHERE IDLOCALIDAD =" + idLocalidad + ") ";
            sql += "MINUS (SELECT SILLA.* ";
            sql += "FROM SILLA ";
            sql += "INNER JOIN RESERVA ";
            sql += "ON SILLA.FILA = RESERVA.FILA ";
            sql += "AND SILLA.NUMERO = RESERVA.POLUGARESN ";
            sql += "AND SILLA.IDLOCALIDAD = RESERVA.IDLOCALIDAD ";
            sql += "AND SILLA.IDLUGAR = RESERVA.IDLUGARES)) S ";
            sql += "INNER JOIN FUNCION ON S.IDLUGAR = FUNCION.IDLUGAR ";
            sql += "INNER JOIN FUNCION_COSTO_LOCALIDAD ON FUNCION_COSTO_LOCALIDAD.IDLOCALIDAD = S.IDLOCALIDAD ";
            sql += "AND FUNCION_COSTO_LOCALIDAD.IDFUNCION = FUNCION.IDFUNCION ";
            sql += "WHERE FUNCION.IDFUNCION =" + idFuncion + " ";
            sql += "ORDER BY FILA, NUMERO ";


            System.out.println(sql);

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            rs = prepStmt.executeQuery();


            while (rs.next()) {
                SillaVos obj = SillaDao.resultToSilla(rs);
                list.add(obj);
            }
        }
        return list;
    }

    public Long siguienteID() throws SQLException {
        String sql = "SELECT MAX (IDRESERVA) AS SIG FROM RESERVA";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next())
            return rs.getLong("SIG");
        return -1L;
    }


  public   ArrayList<UsuarioVos> clienteFiel (int numBoletas) throws SQLException
    {
        ArrayList<UsuarioVos> list = new ArrayList<>();
        String sql = new String();
        sql=" SELECT * FROM  (SELECT  USUARIO.* , LOC, COUNT(RESERVA.IDUSU) AS F FROM USUARIO ";
         sql+= " INNER JOIN RESERVA ON RESERVA.IDUSU = USUARIO.IDUSU AND RESERVA.TIPOID = USUARIO.TIPOID ";
          sql+=" INNER JOIN ";
        sql+=" (SELECT * FROM (SELECT RESERVA.IDLOCALIDAD, RESERVA.IDUSU, RESERVA.TIPOID, COUNT (IDLOCALIDAD) AS LOC FROM RESERVA ";
        sql+=" GROUP BY RESERVA.IDLOCALIDAD, RESERVA.IDUSU, RESERVA.TIPOID) D ";
        sql+="   INNER JOIN LOCALIDAD ON D.IDLOCALIDAD = LOCALIDAD.IDLOCALIDAD ";
        sql+="  WHERE LOCALIDAD.TIPO = 'VIP') E ON E.IDUSU = USUARIO.IDUSU AND USUARIO.TIPOID = E.TIPOID ";
        sql+="  GROUP BY USUARIO.IDUSU, USUARIO.TIPOID, NAME, EMAIL, PASSWORD, ROL, IDFEST, LOC) ";
        sql+= " WHERE F = LOC AND LOC >= " + numBoletas+ ";";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            UsuarioVos obj = UsuarioDao.resultToUsuario(rs);
            list.add(obj);
        }
        return list;
    }

}