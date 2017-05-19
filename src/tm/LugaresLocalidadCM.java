package tm;

import dao.LugaresLocalidadDao;
import vos.LugaresLocalidadVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class LugaresLocalidadCM extends TransactionManager
{
    public LugaresLocalidadCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<LugaresLocalidadVos> getLugaresLocalidades() throws SQLException {
        ArrayList<LugaresLocalidadVos> list = new ArrayList<LugaresLocalidadVos>();
        LugaresLocalidadDao dao = new LugaresLocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getLugaresLocalidades();
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

    public LugaresLocalidadVos getLugaresLocalidad(Long idLugares, Long idLocalidades) throws SQLException {
        LugaresLocalidadVos elem;
        LugaresLocalidadDao dao = new LugaresLocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getLugaresLocalidad(idLugares, idLocalidades);
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

    public void createLugaresLocalidad (LugaresLocalidadVos obj) throws Exception
    {
        LugaresLocalidadDao dao = new LugaresLocalidadDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addLugaresLocalidad(obj);
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

    public void updateLugaresLocalidad (LugaresLocalidadVos obj) throws Exception {
        LugaresLocalidadDao dao = new LugaresLocalidadDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateLugaresLocalidad(obj);
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

    public void deleteLugaresLocalidad (Long id) throws Exception
    {
        LugaresLocalidadDao dao = new LugaresLocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteLugaresLocalidad(id);
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