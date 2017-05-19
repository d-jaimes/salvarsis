package dao;

import vos.FuncionVos;
import vos.UsuarioVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class FuncionDao extends DAO {
    private static final String TABLA = "FUNCION";

    public ArrayList<FuncionVos> getFunciones() throws SQLException {
        ArrayList<FuncionVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            FuncionVos obj = resultToFuncion(rs);
            list.add(obj);
        }
        return list;
    }

    public FuncionVos getFuncion(Long id) throws SQLException {
        FuncionVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDFUNCION = " + id;
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToFuncion(rs);
        }
        return obj;
    }

    public void addFuncion(FuncionVos pObj,String pass, Long id) throws SQLException {
        String sql = new String();
        sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " +id;
        sql += " AND PASSWORD = '" + pass + "' ";
        sql += " AND rol = 'Administrador' ";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdFuncion() + ", ";
            sql += dateToSting(pObj.getFecha()) + ", ";
            sql += pObj.getIdShow() + ", ";
            sql += pObj.getIdLugar() + ", ";
            sql += pObj.isDone() + ")";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }
    }

    public void updateFuncion(FuncionVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "FECHA = " + dateToSting(pObj.getFecha()) + ", ";
        sql += "IDSHOW = " + pObj.getIdShow() + ", ";
        sql += "IDLUGAR = " + pObj.getIdLugar() + ", ";
        sql += "DONE = " + pObj.isDone() + " ";

        sql += "WHERE IDFUNCION = " + pObj.getIdFuncion();

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteFuncion(Long id) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDFUNCION = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    public static FuncionVos resultToFuncion(ResultSet rs) throws SQLException {
        FuncionVos obj = new FuncionVos();

        obj.setIdFuncion(rs.getLong("IDFUNCION"));
        obj.setFecha(rs.getTimestamp("FECHA"));
        obj.setIdShow(rs.getLong("IDSHOW"));
        obj.setIdLugar(rs.getLong("IDLUGAR"));
        obj.setDone(rs.getInt("DONE"));

        return obj;

    }

    public ArrayList<UsuarioVos> usuariosAsistieronFuncion(Long id) throws SQLException {
        ArrayList list = new ArrayList();
        String sql = "SELECT IDUSU";
        sql += " FROM FUNCION";
        sql += "  INNER JOIN RESERVA ON FUNCION.IDFUNCION = RESERVA.IDFUNCION";
        sql += " WHERE FUNCION.IDFUNCION = 1;";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            UsuarioVos obj = UsuarioDao.resultToUsuario(rs);
            list.add(obj);
        }
        return list;
    }

    public Long getDuracion(Long id) throws SQLException {
        String sql = "SELECT SHOWS.DURATION";
        sql += " FROM FUNCION";
        sql += " INNER JOIN SHOWS ON FUNCION.IDSHOW = SHOWS.IDSHOWS";
        sql += " WHERE IDFUNCION = " + id;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();
        rs.next();
        return rs.getLong("SHOWS.DURATION");
    }

    public ArrayList<UsuarioVos> asistentesFuncionPorCompany(Long id, Date inicial, Date fin )throws SQLException
    {
        ArrayList list = new ArrayList();
        String sql= " SELECT U.IDUSU,U.TIPOID,U.NAME,U.EMAIL,U.ROL,U.IDFEST ";
        sql+=" FROM ";
        sql+=" FUNCION F ";
        sql+=" INNER JOIN ";
        sql+=  "  SHOWS S ";
        sql+=   " ON F.IDSHOW = S.IDSHOWS ";
        sql+=   " INNER JOIN ";
        sql+=   " OFRECE O ";
        sql+=   " ON O.IDSHOW = S.IDSHOWS ";
        sql+=   " INNER JOIN ";
        sql+=   " COMPANIAS_TEATRO CT ";
        sql+=   " ON O.ID_COMPANIATEATRO = CT.IDCOMPANIA AND 'O.TIPOID' = 'CT.TIPOID' ";
        sql+=   " INNER JOIN ";
        sql+=   " RESERVA R ";
        sql+=   " ON R.IDFUNCION = F.IDSHOW --AND R.FECHACOMPRA = F.FECHA ";
        sql+=   " INNER JOIN ";
        sql+=   " USUARIO U ";
        sql+=   " ON R.IDUSU = U.IDUSU AND R.TIPOID = U.TIPOID ";
        sql+=   " WHERE CT.IDCOMPANIA = " +id;
         sql+=  " AND F.FECHA BETWEEN "+ inicial +" AND " +fin+" ;";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            UsuarioVos obj = UsuarioDao.resultToUsuario(rs);
            list.add(obj);
        }
        return list;
    }

    public ArrayList<UsuarioVos> asistentesFuncionNoCompany(Long id, Date inicial, Date fin )throws SQLException
    {
        ArrayList list = new ArrayList();

        String sql= " SELECT USUARIO.IDUSU,USUARIO.TIPOID,USUARIO.NAME,USUARIO.EMAIL,USUARIO.rOL,USUARIO.IDFEST ";
        sql+=" FROM USUARIO INNER JOIN RESERVA ";
        sql+= " ON RESERVA.IDUSU=USUARIO.IDUSU and 'RESERVA.TIPOID' = 'USUARIO.TIPOID' ";
        sql+=" MINUS( ";
        sql+=" SELECT U.IDUSU,U.TIPOID,U.NAME,U.EMAIL,U.ROL,U.IDFEST ";
        sql+=" FROM ";
        sql+=" FUNCION F ";
        sql+=" INNER JOIN ";
        sql+=  "  SHOWS S ";
        sql+=   " ON F.IDSHOW = S.IDSHOWS ";
        sql+=   " INNER JOIN ";
        sql+=   " OFRECE O ";
        sql+=   " ON O.IDSHOW = S.IDSHOWS ";
        sql+=   " INNER JOIN ";
        sql+=   " COMPANIAS_TEATRO CT ";
        sql+=   " ON O.ID_COMPANIATEATRO = CT.IDCOMPANIA AND 'O.TIPOID' = 'CT.TIPOID' ";
        sql+=   " INNER JOIN ";
        sql+=   " RESERVA R ";
        sql+=   " ON R.IDFUNCION = F.IDSHOW --AND R.FECHACOMPRA = F.FECHA ";
        sql+=   " INNER JOIN ";
        sql+=   " USUARIO U ";
        sql+=   " ON R.IDUSU = U.IDUSU AND R.TIPOID = U.TIPOID ";
        sql+=   " WHERE CT.IDCOMPANIA = " +id;
        sql+=  " AND F.FECHA BETWEEN "+ inicial +" AND " +fin+" ); ";

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
