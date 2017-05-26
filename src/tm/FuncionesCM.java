package tm;

import dao.FuncionDao;
import protocolos.ProtocoloFuncion;
import vos.FuncionVos;
import vos.UsuarioVos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static utils.SQLUtils.parseDateTime;
import static utils.SQLUtils.timeToString;

/**
 *
 */
public class FuncionesCM extends TransactionManager {
    public FuncionesCM(String contextPathP) {
        super(contextPathP);
    }

    public ArrayList<FuncionVos> getFuncionesLocal() throws SQLException {
        ArrayList<FuncionVos> list = new ArrayList<FuncionVos>();
        FuncionDao dao = new FuncionDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getFunciones();
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

        return list;
    }

//    public List<FuncionVos> getFuncionesRemote(Long idEspectaculo) throws Exception {
//        List<FuncionVos> remL;
//        FuncionDao dao = new FuncionDao();
//        try
//        {
//            this.connection = getConnection();
//            this.connection.setAutoCommit(false);
//            dao.setConnection(this.connection);
//            remL = dao.getFuncionesRemote(idEspectaculo);
//        }
//        catch( SQLException e )
//        {
//            System.err.println( "SQLException: " + e.getMessage( ) );
//            connection.rollback( );
//            e.printStackTrace( );
//            throw e;
//        }
//        catch( Exception e )
//        {
//            System.err.println( "GeneralException: " + e.getMessage( ) );
//            connection.rollback( );
//            e.printStackTrace( );
//            throw e;
//        }
//        finally
//        {
//            closeDAO( dao );
//        }
//        return remL;
//    }

    public FuncionVos getFuncionLocal(Long id) throws SQLException {
        FuncionVos elem;
        FuncionDao dao = new FuncionDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            elem = dao.getFuncion(id);
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

        return elem;
    }

    public void createFuncionLocal(FuncionVos obj, String pass, Long id) throws Exception {
        FuncionDao dao = new FuncionDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addFuncion(obj, pass, id);
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

    public void deleteFuncionLocal(Long id) throws Exception {
        FuncionDao dao = new FuncionDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteFuncion(id);
            connection.commit();
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

    public ArrayList<String> cancelarFuncionLocal(Long id) throws Exception {
        FuncionDao dao = new FuncionDao();
        FuncionVos func = dao.getFuncion(id);
        ArrayList<String> list = new ArrayList<>();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);

            Long dur = dao.getDuracion(id) * 60000;
            if (new Date().getTime() < func.getFecha().getTime() + dur) {
                func.setDone(2);
                updateFuncionLocal(func);

                ArrayList<UsuarioVos> usu = dao.usuariosAsistieronFuncion(id);
                for (UsuarioVos usuario : usu) {
                    list.add("Pagar al Senor(a): " + usuario.getNombre() + "el total de la boleta numero: ");
                }


            }
            dao.deleteFuncion(id);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            this.connection.rollback();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            this.connection.rollback();
            throw e;
        } finally {
            closeDAO(dao);
        }
        return list;
    }

    public ArrayList<UsuarioVos> getUsuariosPorFuncionLocal(Long id, Date inicial, Date fin) throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<UsuarioVos>();
        FuncionDao dao = new FuncionDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.asistentesFuncionPorCompany(id, inicial, fin);
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

        return list;
    }

    public ArrayList<UsuarioVos> getUsuariosNoFuncionLocal(Long id, Date inicial, Date fin) throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<UsuarioVos>();
        FuncionDao dao = new FuncionDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.asistentesFuncionNoCompany(id, inicial, fin);
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

        return list;
    }

    public static List<ProtocoloFuncion> funcionesToProtocol(List<FuncionVos> list) {
        List<ProtocoloFuncion> resultList = new LinkedList<>();
        for (FuncionVos funcion : list) {
            resultList.add(funcionToProtocol(funcion));
        }
        return resultList;
    }

    public static FuncionVos protocolToFuncion(ProtocoloFuncion protocoloFuncion) {
        FuncionVos funcion = new FuncionVos();
        funcion.setIdLugar(protocoloFuncion.getIdLugar());
        funcion.setIdShow(protocoloFuncion.getIdEspectaculo());
        funcion.setFecha(parseDateTime(protocoloFuncion.getFecha()));
        funcion.setDone(protocoloFuncion.isRealizado() ? 1 : 0);
        return funcion;
    }

    public static ProtocoloFuncion funcionToProtocol(FuncionVos funcion) {
        ProtocoloFuncion protocoloFuncion = new ProtocoloFuncion();
        protocoloFuncion.setAppName("APP3");
        protocoloFuncion.setFecha(timeToString(funcion.getFecha()));
        protocoloFuncion.setIdEspectaculo(funcion.getIdShow());
        protocoloFuncion.setIdFuncion(-1);
        protocoloFuncion.setIdLugar(funcion.getIdLugar());
        protocoloFuncion.setRealizado(funcion.isDone() == 1);
        return protocoloFuncion;
    }
}