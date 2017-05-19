package tm;

import dao.ClienteRegistradoDao;
import dao.UsuarioDao;
import vos.ClienteRegistradoVos;
import vos.FuncionVos;
import vos.UsuarioVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class ClienteRegistradoCM extends TransactionManager {
    public ClienteRegistradoCM(String contextPathP) {
        super(contextPathP);
    }

    public ArrayList<ClienteRegistradoVos> getClienteRegistradosLocal() throws SQLException {
        ArrayList<ClienteRegistradoVos> list = new ArrayList<ClienteRegistradoVos>();
        ClienteRegistradoDao dao = new ClienteRegistradoDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getClienteRegistrados();
            this.connection.commit();
        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            closeDAO(dao);
        }

        return list;
    }

    public ClienteRegistradoVos getClienteRegistradoLocal(Long idusu, String tipoId) throws SQLException {
        ClienteRegistradoVos elem;
        ClienteRegistradoDao dao = new ClienteRegistradoDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            elem = dao.getClienteRegistrado(idusu, tipoId);
            this.connection.commit();

        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            closeDAO(dao);
        }

        return elem;
    }

    public void createClienteRegistradoLocal(Long id, String tipo, ClienteRegistradoVos obj) throws Exception {
        ClienteRegistradoDao dao = new ClienteRegistradoDao();
        UsuarioDao dao2 = new UsuarioDao();
        try {
            this.connection = getConnection();
            connection.setAutoCommit(false);
            dao2.setConnection(connection);
            dao.setConnection(connection);

            dao2.addUsuario(obj);
            dao.addClienteRegistrado(id, tipo, obj);
            this.connection.commit();
        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            closeDAO(dao);
        }
    }

    public void updateClienteRegistradoLocal(ClienteRegistradoVos obj) throws Exception {
        ClienteRegistradoDao dao = new ClienteRegistradoDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateClienteRegistrado(obj);
            this.connection.commit();
        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            closeDAO(dao);
        }
    }

    public void deleteClienteRegistradoLocal(Long idusu, String tipoId) throws Exception {
        ClienteRegistradoDao dao = new ClienteRegistradoDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteClienteRegistrado(idusu, tipoId);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            closeDAO(dao);
        }
    }

    //TODO: María
    public ArrayList<FuncionVos> darAsistenciaClienteLocal(Long idusu, String tipoID) throws SQLException {
        ArrayList<FuncionVos> list = new ArrayList<FuncionVos>();
        ClienteRegistradoDao dao = new ClienteRegistradoDao();

        UsuarioDao usu = new UsuarioDao();

        try {
            this.connection = getConnection();
            usu.setConnection(this.connection);
            UsuarioVos usua = usu.getUsuario(idusu, tipoID);

            if (usua.getRol().equals("Usuario Registrado")) {
                dao.setConnection(connection);
                list = dao.asistenciaUsusarios(idusu, tipoID);
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            this.connection.rollback();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            this.connection.rollback();
            throw e;
        } finally {
            closeDAO(dao);
        }

        return list;
    }
}


