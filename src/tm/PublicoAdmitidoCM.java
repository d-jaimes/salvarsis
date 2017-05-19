package tm;

import dao.PublicoAdmitidoDao;
import vos.PublicoAdmitidoVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class PublicoAdmitidoCM extends TransactionManager
{
    public PublicoAdmitidoCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<PublicoAdmitidoVos> getPublicoAdmitidos() throws SQLException {
        ArrayList<PublicoAdmitidoVos> list = new ArrayList<PublicoAdmitidoVos>();
        PublicoAdmitidoDao dao = new PublicoAdmitidoDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getPublicoAdmitidos();
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

    public PublicoAdmitidoVos getPublicoAdmitido(Long id) throws SQLException {
        PublicoAdmitidoVos elem;
        PublicoAdmitidoDao dao = new PublicoAdmitidoDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getPublicoAdmitido(id);
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

    public void createPublicoAdmitido (PublicoAdmitidoVos obj) throws Exception
    {
        PublicoAdmitidoDao dao = new PublicoAdmitidoDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addPublicoAdmitido(obj);
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

    public void updatePublicoAdmitido (PublicoAdmitidoVos obj) throws Exception {
        PublicoAdmitidoDao dao = new PublicoAdmitidoDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updatePublicoAdmitido(obj);
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

    public void deletePublicoAdmitido (Long id) throws Exception
    {
        PublicoAdmitidoDao dao = new PublicoAdmitidoDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deletePublicoAdmitido(id);
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