package tm;

import dao.CategoriaDao;
import vos.CategoriaVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class CategoriasCM extends TransactionManager
{
    public CategoriasCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<CategoriaVos> getCategoriasLocal() throws SQLException {
        ArrayList<CategoriaVos> list = new ArrayList<CategoriaVos>();
        CategoriaDao dao = new CategoriaDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getCategorias();
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

    public CategoriaVos getCategoriaLocal(Long id) throws SQLException {
        CategoriaVos elem;
        CategoriaDao dao = new CategoriaDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getCategoria(id);
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

    public void createCategoriaLocal(CategoriaVos obj) throws Exception
    {
        CategoriaDao dao = new CategoriaDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addCategoria(obj);
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

    public void updateCategoriaLocal(CategoriaVos obj) throws Exception {
        CategoriaDao dao = new CategoriaDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateCategoria(obj);
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

    public void deleteCategoriaLocal(Long id) throws Exception
    {
        CategoriaDao dao = new CategoriaDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteCategoria(id);
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