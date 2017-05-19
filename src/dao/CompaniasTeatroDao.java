package dao;

import vos.CompaniasTeatroVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class CompaniasTeatroDao extends DAO {
    private static final String TABLA = "COMPANIAS_TEATRO";

    public ArrayList<CompaniasTeatroVos> getCompaniaTeatros() throws SQLException {
        ArrayList<CompaniasTeatroVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            CompaniasTeatroVos obj = resultToCompaniaTeatro(rs);
            list.add(obj);
        }
        return list;
    }

    public CompaniasTeatroVos getCompaniaTeatro(Long idCompania, String tipoId) throws SQLException {
        CompaniasTeatroVos obj = null;

        String sql = sql = "SELECT * FROM COMPANIAS_TEATRO INNER JOIN USUARIO ON COMPANIAS_TEATRO.IDCOMPANIA = USUARIO.IDUSU";
        sql += " AND COMPANIAS_TEATRO.TIPOID = USUARIO.TIPOID ";
        sql += " WHERE IDCOMPANIA = " + idCompania;
        sql += " AND COMPANIAS_TEATRO.TIPOID =  '" + tipoId + "'";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToCompaniaTeatro(rs);
        }
        return obj;
    }

    public void addCompaniaTeatro(CompaniasTeatroVos pObj, Long id, String tipo) throws SQLException
    {
        String sql = new String();
        sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + id;
        sql += " AND TIPOID = '" + tipo + "' ";
        sql += " AND rol =   'Administrador'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdusu() + ", '";
            sql += pObj.getRepresentante() + "', '";
            sql += pObj.getNombre() + "', '";
            sql += pObj.getPaisOrigen() + "', ";
            sql += dateToSting(pObj.getFechaLlegada()) + ", ";
            sql += dateToSting(pObj.getFechaSalida()) + ", '";
            sql += pObj.getPgWeb() + "', '";
            sql += pObj.getTipoId() + "')";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }

    }

    public void updateCompaniaTeatro(CompaniasTeatroVos pObj) throws SQLException
    {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += " REPRESENTANTE = '" + pObj.getRepresentante( ) + "', ";
        sql += " NOMBRE = '" + pObj.getNombre( ) + "', ";
        sql += " PAISORIGEN = '" + pObj.getPaisOrigen( ) + "', ";
        sql += " FECHALLEGADA = " + dateToSting( pObj.getFechaLlegada( ) ) + ", ";
        sql += " FECHASALIDA = " + dateToSting( pObj.getFechaSalida( ) ) + ", ";
        sql += " PGWEB = '" + pObj.getPgWeb( ) + "' ";
        sql += " WHERE IDCOMPANIA = " + pObj.getIdusu( );
        sql += " AND TIPOID = '" + pObj.getTipoId( ) + "' ";

        System.out.println( sql);

        PreparedStatement prepStmt = conn.prepareStatement( sql );
        resources.add( prepStmt );
        prepStmt.execute( );
    }

    public void deleteCompaniaTeatro(Long id, String tipoId) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDCOMPANIA = " + id;
        sql += " AND TIPOID = '" + tipoId + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private CompaniasTeatroVos resultToCompaniaTeatro(ResultSet rs) throws SQLException {
        CompaniasTeatroVos obj = new CompaniasTeatroVos();
       obj.setIdusu(rs.getLong("IDCOMPANIA"));
       obj.setRepresentante(rs.getString("REPRESENTANTE"));
       obj.setNombre(rs.getString("NOMBRE"));
       obj.setPaisOrigen(rs.getString("PAISORIGEN"));
       obj.setFechaLlegada(rs.getDate("FECHALLEGADA"));
       obj.setFechaSalida(rs.getDate("FECHASALIDA"));
       obj.setPgWeb(rs.getString("PGWEB"));
       obj.setTipoId(rs.getString("TIPOID"));
        return obj;

    }

    //todo: req 16
    public ArrayList<Long> idFuncionesDelete(Long id, String tipo) throws SQLException {

        ArrayList<Long> list=new ArrayList<>();
            //TODO verificacion
        String sql = new String();
        sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " +id;
        sql += " AND rol = 'Administrador' ";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = " Select funcion.IDFUNCION ";
            sql += " from funcion natural inner join ofrece ";
            sql += " where ofrece.id_companiateatro = " + id;
            sql += " and ofrece.tipoid = '" + tipo + "'";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            ResultSet rs2 = prepStmt.executeQuery();

            while (rs2.next()) {
                Long obj = rs.getLong("IDFUNCION");
                list.add(obj);
            }
        }
        return list;

    }

}