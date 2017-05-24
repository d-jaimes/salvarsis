package tm;

import dao.FuncionDao;
import protocolos.ProtocoloFuncion;
import vos.FuncionVos;
import vos.UsuarioVos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.DateUtils.timeToString;

/**
 *
 */
public class FuncionesCM extends TransactionManager
{
    public FuncionesCM(String contextPathP)
    {
        super(contextPathP);
    }

    public List<ProtocoloFuncion> getFuncionesLocal() throws SQLException {
        List<ProtocoloFuncion> list = new ArrayList<>();
        FuncionDao dao = new FuncionDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list= toProtocol(dao.getFunciones());
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

    public FuncionVos getFuncionLocal(Long id) throws SQLException {
        FuncionVos elem;
        FuncionDao dao = new FuncionDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getFuncion(id);
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

    public void createFuncionLocal(FuncionVos obj, String pass, Long id) throws Exception
    {
        FuncionDao dao = new FuncionDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addFuncion(obj, pass,id);
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

    public void updateFuncionLocal(FuncionVos obj) throws Exception {
        FuncionDao dao = new FuncionDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateFuncion(obj);
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

    public void deleteFuncionLocal(Long id) throws Exception
    {
        FuncionDao dao = new FuncionDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteFuncion(id);
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

    public ArrayList<String> cancelarFuncionLocal(Long id) throws Exception
    {
        FuncionDao dao = new FuncionDao();
        FuncionVos func = dao.getFuncion(id);
        ArrayList<String> list = new ArrayList<>();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);

            Long dur = dao.getDuracion(id)*60000;
            if (new Date().getTime() < func.getFecha().getTime() + dur )
            {
                func.setDone(2);
                updateFuncionLocal(func);

                ArrayList<UsuarioVos> usu = dao.usuariosAsistieronFuncion(id);
                for (UsuarioVos usuario: usu)
                {
                   list.add("Pagar al Senor(a): " + usuario.getNombre() + "el total de la boleta numero: ");
                }


            }
            dao.deleteFuncion(id);
            connection.commit();
        }
        catch(SQLException e)
        {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            this.connection.rollback();
            throw e;
        }

        catch (Exception e)
        {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            this.connection.rollback();
            throw e;
        }
        finally
        {
            closeDAO(dao);
        }
        return list;
    }
    public ArrayList<UsuarioVos> getUsuariosPorFuncionLocal(Long id, Date inicial, Date fin) throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<UsuarioVos>();
        FuncionDao dao = new FuncionDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.asistentesFuncionPorCompany(id,inicial,fin);
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

    public ArrayList<UsuarioVos> getUsuariosNoFuncionLocal(Long id, Date inicial, Date fin) throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<UsuarioVos>();
        FuncionDao dao = new FuncionDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.asistentesFuncionNoCompany(id,inicial,fin);
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

    private List<ProtocoloFuncion> toProtocol( List<FuncionVos> list)
    {
        List<ProtocoloFuncion> lista = new ArrayList<>();
        for (FuncionVos funcion : list)
        {
            // TODO
            ProtocoloFuncion f = new ProtocoloFuncion("app3", funcion.getIdFuncion(), timeToString( funcion.getFecha()),funcion.getIdLugar(),funcion.getIdShow(),funcion.isDone()==1);
        }
        return lista;
    }
}