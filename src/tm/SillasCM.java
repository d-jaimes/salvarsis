package tm;

import dao.SillaDao;
import vos.SillaVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class SillasCM extends TransactionManager
{
    public SillasCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<SillaVos> getSillas() throws SQLException {
        ArrayList<SillaVos> list = new ArrayList<SillaVos>();
        SillaDao dao = new SillaDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getSillas();
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

    public SillaVos getSilla(Integer fila, Integer numero) throws SQLException {
        SillaVos elem;
        SillaDao dao = new SillaDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getSilla(fila, numero);
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

    public void createSilla (SillaVos obj) throws Exception
    {
        SillaDao dao = new SillaDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addSilla(obj);
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

    public void updateSilla (SillaVos obj) throws Exception {
        SillaDao dao = new SillaDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateSilla(obj);
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

    public void deleteSilla (Integer fila, Integer numero) throws Exception
    {
        SillaDao dao = new SillaDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteSilla(fila, numero);
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
