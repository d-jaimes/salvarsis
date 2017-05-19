package tm;

import dao.FuncionCostoLocalidadDao;
import vos.FuncionCostoLocalidadVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class FuncionCostoLocalidadCM extends TransactionManager
{
    public FuncionCostoLocalidadCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<FuncionCostoLocalidadVos> getFuncionCostoLocalidadesLocal() throws SQLException {
        ArrayList<FuncionCostoLocalidadVos> list = new ArrayList<FuncionCostoLocalidadVos>();
        FuncionCostoLocalidadDao dao = new FuncionCostoLocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getFuncionCostoLocalidades();
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

    public FuncionCostoLocalidadVos getFuncionCostoLocalidadLocal(Long idFuncion, Long idLocalidad) throws SQLException {
        FuncionCostoLocalidadVos elem;
        FuncionCostoLocalidadDao dao = new FuncionCostoLocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getFuncionCostoLocalidad(idFuncion, idLocalidad);
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

    public void createFuncionCostoLocalidadLocal(FuncionCostoLocalidadVos obj) throws Exception
    {
        FuncionCostoLocalidadDao dao = new FuncionCostoLocalidadDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addFuncionCostoLocalidad(obj);
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

    public void updateFuncionCostoLocalidadLocal(FuncionCostoLocalidadVos obj) throws Exception {
        FuncionCostoLocalidadDao dao = new FuncionCostoLocalidadDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateFuncionCostoLocalidad(obj);
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

    public void deleteFuncionCostoLocalidadLocal(Long idFuncion, Long idLocalidad) throws Exception
    {
        FuncionCostoLocalidadDao dao = new FuncionCostoLocalidadDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteFuncionCostoLocalidad(idFuncion, idLocalidad);
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
