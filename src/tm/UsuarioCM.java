package tm;

import dao.UsuarioDao;
import vos.UsuarioVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class UsuarioCM extends TransactionManager {
    public UsuarioCM(String contextPathP) {
        super(contextPathP);
    }

    public ArrayList<UsuarioVos> getUsuarios() throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<UsuarioVos>();
        UsuarioDao dao = new UsuarioDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getUsuarios();
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

    public UsuarioVos getUsuario(Long idusu, String tipoId) throws SQLException {
        UsuarioVos elem;
        UsuarioDao dao = new UsuarioDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            elem = dao.getUsuario(idusu, tipoId);
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

    public void createUsuario(UsuarioVos obj) throws Exception {
        UsuarioDao dao = new UsuarioDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addUsuario(obj);
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

    public void updateUsuario(UsuarioVos obj) throws Exception {
        UsuarioDao dao = new UsuarioDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateUsuario(obj);
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

    public void deleteUsuario(Long idusu, String tipoId) throws Exception {
        UsuarioDao dao = new UsuarioDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteUsuario(idusu, tipoId);
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
}