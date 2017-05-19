package tm;

import dao.FestivAndesDao;
import vos.FestivandesVos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by María del Rosario on 11/04/2017.
 */

public class FestivAndesCM extends TransactionManager
{

    public FestivAndesCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<FestivandesVos> getFestivalesLocal() throws SQLException {
        ArrayList<FestivandesVos> list = new ArrayList<FestivandesVos>();
        FestivAndesDao dao = new FestivAndesDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getFestivales();
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

    public FestivandesVos getFestivalLocal(Long id) throws SQLException {
        FestivandesVos elem;
        FestivAndesDao dao = new FestivAndesDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getFestival( id);
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

    public void createFestivalLocal(FestivandesVos obj) throws Exception
    {
        FestivAndesDao dao = new FestivAndesDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addFestival(obj);
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

    public void updateFestivalLocal(FestivandesVos obj) throws Exception
    {
        FestivAndesDao dao = new FestivAndesDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateFestival(obj);
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

    public void deleteFestivalLocal(Long id) throws Exception
    {
        FestivAndesDao dao = new FestivAndesDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteFestival(id);
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
