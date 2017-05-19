package dao;

import vos.ClienteRegistradoVos;
import vos.FuncionVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class ClienteRegistradoDao extends DAO {
    private static final String TABLA = "CLIENTE_REGISTRADO";

    public ArrayList<ClienteRegistradoVos> getClienteRegistrados() throws SQLException {
        ArrayList<ClienteRegistradoVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " INNER JOIN USUARIO ON  CLIENTE_REGISTRADO.IDUSU = USUARIO.IDUSU AND CLIENTE_REGISTRADO.TIPOID= USUARIO.TIPOID ";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            ClienteRegistradoVos obj = resultToClienteRegistrado(rs);
            list.add(obj);
        }
        return list;
    }

    public ClienteRegistradoVos getClienteRegistrado(Long idusu, String tipoId) throws SQLException {
        ClienteRegistradoVos obj = null;

        String sql = "SELECT * FROM " + TABLA;
        sql += " INNER JOIN USUARIO ON USUARIO.IDUSU = " + TABLA + ".IDUSU AND USUARIO.TIPOID = " + TABLA + ".TIPOID";
        sql += " WHERE USUARIO.IDUSU = " + idusu + " ";
        sql += "AND USUARIO.TIPOID = '" + tipoId + "' ";

        System.out.println(sql);

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToClienteRegistrado(rs);
        }
        return obj;
    }

    //TODO listo (?)
    public void addClienteRegistrado(Long id, String tipo, ClienteRegistradoVos pObj) throws SQLException {
        String sql = ("SELECT * ");
        sql += ("FROM USUARIO ");
        sql += "WHERE IDUSU =  " + id;
        sql += " AND TIPOID = '" + tipo + "' ";
        sql += " AND ROL = 'Administrador'";
        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            sql = "INSERT INTO " + TABLA + " VALUES (";
            sql += pObj.getIdusu() + ", '";
            sql += pObj.getTipoId() + "', ";
            sql += pObj.getEdad() + ")";

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            resources.add(prepStmt);
            prepStmt.execute();
        }

    }

    public void updateClienteRegistrado(ClienteRegistradoVos pObj) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET ";
        sql += "EDAD =" + pObj.getEdad();
        sql += " WHERE  IDUSU = " + pObj.getIdusu() + " ";
        sql += " AND TIPOID = '" + pObj.getTipoId() + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }

    public void deleteClienteRegistrado(Long idusu, String tipoId) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDUSU = " + idusu + " ";
        sql += "AND TIPOID = '" + tipoId + "'";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private ClienteRegistradoVos resultToClienteRegistrado(ResultSet rs) throws SQLException {
        ClienteRegistradoVos obj = new ClienteRegistradoVos();
        obj.setIdusu(rs.getLong("IDUSU"));
        obj.setTipoId(rs.getString("TIPOID"));
        obj.setNombre(rs.getString("NAME"));
        obj.setEmail(rs.getString("EMAIL"));
        obj.setPassword(rs.getString("PASSWORD"));
        obj.setRol(rs.getString("ROL"));
        obj.setIdfest(rs.getLong("IDFEST"));
        obj.setEdad(rs.getInt("EDAD"));
        return obj;
    }

    public ArrayList<FuncionVos> asistenciaUsusarios(Long id, String tipoId) throws SQLException {
        ArrayList<FuncionVos> list = new ArrayList<>();

        String sql = "SELECT FUNCION.* ";
        sql += " FROM FUNCION ";
        sql += " INNER JOIN RESERVA ON FUNCION.IDFUNCION = RESERVA.IDFUNCION ";
        sql += " WHERE RESERVA.IDUSU = " + id;
        sql += " AND TIPOID = '" + tipoId + "' ";
        sql += " ORDER BY DONE ";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            FuncionVos obj = FuncionDao.resultToFuncion(rs);
            list.add(obj);
        }

        return list;
    }
}
