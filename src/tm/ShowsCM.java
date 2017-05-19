package tm;

import dao.ShowsDao;
import vos.ShowsVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class ShowsCM extends TransactionManager
{
    public ShowsCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<ShowsVos> getShows() throws SQLException {
        ArrayList<ShowsVos> list = new ArrayList<ShowsVos>();
        ShowsDao dao = new ShowsDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getShows();
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

    public ShowsVos getShow(Long id) throws SQLException {
        ShowsVos elem;
        ShowsDao dao = new ShowsDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getShow(id);
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

    public void createShow (Long id, String tipo, ShowsVos obj) throws Exception
    {
        ShowsDao dao = new ShowsDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addShow(id, tipo, obj);
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

    public void updateShow (ShowsVos obj) throws Exception {
        ShowsDao dao = new ShowsDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateShow(obj);
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

    public void deleteShow (Long id) throws Exception
    {
        ShowsDao dao = new ShowsDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteShow(id);
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