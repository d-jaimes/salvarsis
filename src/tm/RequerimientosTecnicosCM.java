package tm;

import dao.RequerimientosTecnicosDao;
import vos.RequerimientosTecnicosVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class RequerimientosTecnicosCM extends TransactionManager
{
    public RequerimientosTecnicosCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<RequerimientosTecnicosVos> getRequerimientosTecnicoss() throws SQLException {
        ArrayList<RequerimientosTecnicosVos> list = new ArrayList<RequerimientosTecnicosVos>();
        RequerimientosTecnicosDao dao = new RequerimientosTecnicosDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getReqTecnicos();
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

    public RequerimientosTecnicosVos getRequerimientosTecnicos(Long id) throws SQLException {
        RequerimientosTecnicosVos elem;
        RequerimientosTecnicosDao dao = new RequerimientosTecnicosDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getReqTecnico(id);
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

    public void createRequerimientosTecnicos (RequerimientosTecnicosVos obj) throws Exception
    {
        RequerimientosTecnicosDao dao = new RequerimientosTecnicosDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addReqTecnico(obj);
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

    public void updateRequerimientosTecnicos (RequerimientosTecnicosVos obj) throws Exception {
        RequerimientosTecnicosDao dao = new RequerimientosTecnicosDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateReqTecnico(obj);
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

    public void deleteRequerimientosTecnicos (Long id) throws Exception
    {
        RequerimientosTecnicosDao dao = new RequerimientosTecnicosDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteReqTecnico(id);
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