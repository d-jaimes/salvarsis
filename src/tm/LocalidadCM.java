package tm;

import dao.LocalidadDao;
import vos.LocalidadVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class LocalidadCM extends TransactionManager
{
    public LocalidadCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<LocalidadVos> getLocalidadesLocal() throws SQLException {
        ArrayList<LocalidadVos> list = new ArrayList<LocalidadVos>();
        LocalidadDao dao = new LocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getLocalidades();
            this.connection.commit();
        }
        catch(SQLException e)
        {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        catch (Exception e)
        {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally
        {
            closeDAO(dao);
        }

        return list;
    }

    public LocalidadVos getLocalidadLocal(Long id) throws SQLException {
        LocalidadVos elem;
        LocalidadDao dao = new LocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getLocalidad(id);
            this.connection.commit();

        }
        catch(SQLException e)
        {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        catch (Exception e)
        {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally
        {
            closeDAO(dao);
        }

        return elem;
    }

    public void createLocalidadLocal(LocalidadVos obj) throws Exception
    {
        LocalidadDao dao = new LocalidadDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addLocalidad(obj);
            this.connection.commit();
        }
        catch(SQLException e)
        {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        catch (Exception e)
        {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally
        {
            closeDAO(dao);
        }
    }

    public void updateLocalidadLocal(LocalidadVos obj) throws Exception {
        LocalidadDao dao = new LocalidadDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateLocalidad(obj);
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

    public void deleteLocalidadLocal(Long id) throws Exception
    {
        LocalidadDao dao = new LocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteLocalidad(id);
            connection.commit();
        }
        catch(SQLException e)
        {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        catch (Exception e)
        {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally
        {
            closeDAO(dao);
        }
    }
}