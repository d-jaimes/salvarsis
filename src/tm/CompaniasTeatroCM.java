package tm;

import dao.CompaniasTeatroDao;
import dao.UsuarioDao;
import protocolos.ProtocoloCompania;
import vos.CompaniasTeatroVos;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class CompaniasTeatroCM extends TransactionManager
{
    public CompaniasTeatroCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<CompaniasTeatroVos> getCompaniasTeatrosLocal() throws SQLException {
        ArrayList<CompaniasTeatroVos> list = new ArrayList<CompaniasTeatroVos>();
        CompaniasTeatroDao dao = new CompaniasTeatroDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getCompaniaTeatros();
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

    public CompaniasTeatroVos getCompaniasTeatroLocal(Long idusu, String tipoID) throws SQLException {
        CompaniasTeatroVos elem;
        CompaniasTeatroDao dao = new CompaniasTeatroDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getCompaniaTeatro(idusu,tipoID);
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

    public void createCompaniasTeatroLocal(CompaniasTeatroVos obj, Long id, String tipo) throws Exception
    {
        CompaniasTeatroDao dao = new CompaniasTeatroDao();
        UsuarioDao dao2= new UsuarioDao();
        try
        {
            this.connection = getConnection();
            connection.setAutoCommit(false);
            dao.setConnection(connection);
            dao2.setConnection(connection);

            dao2.addUsuario(obj);
            dao.addCompaniaTeatro(obj,id, tipo);
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

    public void updateCompaniasTeatroLocal(CompaniasTeatroVos obj) throws Exception {
        CompaniasTeatroDao dao = new CompaniasTeatroDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateCompaniaTeatro(obj);
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

    public ProtocoloCompania deleteCompaniasTeatro(Long idusu, String tipoId) throws Exception
    {
        CompaniasTeatroDao dao = new CompaniasTeatroDao();
        ProtocoloCompania prot= new ProtocoloCompania();
        ProtocoloCompania protocoloCompania = new ProtocoloCompania( );
        UsuarioDao daoUsuario = new UsuarioDao( );

        protocoloCompania.setResponse( 0 );
        try
        {
            this.connection = getConnection();
            this.connection.setAutoCommit( false );
            dao.setConnection(connection);
            daoUsuario.setConnection( connection );
            dao.deleteCompaniaTeatro(idusu, tipoId);
            daoUsuario.deleteUsuario(idusu,tipoId);
            prot.setResponse( 1 );
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
            closeDAO( daoUsuario );
            closeDAO(dao);
        }
        return prot;
    }

    //todo: req 16
    public ArrayList<Long> traerFuncionesPorCompaniaLocal(Long idusu, String tipoId) throws Exception
    {
        CompaniasTeatroDao dao = new CompaniasTeatroDao();
        ArrayList<Long> funcionesAsociadas;
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);

            funcionesAsociadas  = dao.idFuncionesDelete(idusu, tipoId);

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
        return funcionesAsociadas;
    }




}
