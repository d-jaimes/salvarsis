package tm;

import dao.LugaresDao;
import vos.LugaresVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class LugaresCM extends TransactionManager
{
    public LugaresCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<LugaresVos> getLugares() throws SQLException {
        ArrayList<LugaresVos> list = new ArrayList<LugaresVos>();
        LugaresDao dao = new LugaresDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getLugares();
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

    public LugaresVos getLugar(Long id) throws SQLException {
        LugaresVos elem;
        LugaresDao dao = new LugaresDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getLugar(id);
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

    public void createLugar (LugaresVos obj,Long id, String tipo) throws Exception
    {
        LugaresDao dao = new LugaresDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addLugar(obj,id,tipo);
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

    public void updateLugar (LugaresVos obj) throws Exception {
        LugaresDao dao = new LugaresDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateLugar(obj);
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

    public void deleteLugar (Long id) throws Exception
    {
        LugaresDao dao = new LugaresDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteLugar(id);
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