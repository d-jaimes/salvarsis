package tm;

import dao.AccesoEspecialDao;
import vos.AccesoEspecialVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class AccesoEspecialCM extends TransactionManager
{
    public AccesoEspecialCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<AccesoEspecialVos> getAccesoEspecialesLocal() throws SQLException {
        ArrayList<AccesoEspecialVos> list = new ArrayList<AccesoEspecialVos>();
        AccesoEspecialDao dao = new AccesoEspecialDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getAccesoEspeciales();
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

    public AccesoEspecialVos getAccesoEspecialLocal(Long id) throws SQLException {
        AccesoEspecialVos elem;
        AccesoEspecialDao dao = new AccesoEspecialDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getAccesoEspecial(id);
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

    public void createAccesoEspecialLocal(AccesoEspecialVos obj) throws Exception
    {
        AccesoEspecialDao dao = new AccesoEspecialDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addAccesoEspecial(obj);
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

    public void updateAccesoEspecialLocal(AccesoEspecialVos obj) throws Exception {
        AccesoEspecialDao dao = new AccesoEspecialDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateAccesoEspecial(obj);
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

    public void deleteAccesoEspecialLocal(Long id) throws Exception
    {
        AccesoEspecialDao dao = new AccesoEspecialDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteAccesoEspecial(id);
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