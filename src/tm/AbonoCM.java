package tm;

import dao.*;
import vos.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by María del Rosario on 12/04/2017.
 */
public class AbonoCM extends TransactionManager
{
    public AbonoCM(String contextPathP)
    {
        super(contextPathP);
    }

    public ArrayList<AbonoVos> getAbonosLocal() throws SQLException {
        ArrayList<AbonoVos> list = new ArrayList<AbonoVos>();
        AbonoDao dao = new AbonoDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getAbonos();
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

    public AbonoVos getAbonoLocal(Long idusu, String tipoId) throws SQLException {
        AbonoVos elem;
        AbonoDao dao = new AbonoDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection( connection);
            elem = dao.getAbono(idusu, tipoId);
            this.connection.commit();;

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

    public void createAbonoLocal(AbonoVos obj, Long id, String tipo) throws Exception
    {
        AbonoDao dao = new AbonoDao();
        FuncionCostoLocalidadDao fun = new FuncionCostoLocalidadDao();
        ReservaDao res = new ReservaDao();
        AbonoFuncionDao ab = new AbonoFuncionDao();

        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            fun.setConnection(connection);
            res.setConnection(connection);
            ab.setConnection(connection);
            for (FuncionCostoLocalidadVos funcion : obj.getFunciones())
            {
                FuncionCostoLocalidadVos func = fun.getFuncionCostoLocalidad(funcion.getIdFuncion(), funcion.getIdLocalidad());
                if (!res.reservaMultiple(id, tipo, func.getIdLocalidad(), func.getIdFuncion()).isEmpty())
                {
                    AbonoFuncionVos abfu = new AbonoFuncionVos();
                    abfu.setIdFest(obj.getIdFest());
                    abfu.setIdusu(obj.getIdusu());
                    abfu.setTipoId(obj.getTipoId());
                    abfu.setIdFuncion(funcion.getIdFuncion());
                    abfu.setIdLocalidad(funcion.getIdLocalidad());
                    ab.addAbonoFuncion(abfu);
                }
            }
            dao.addAbono(obj,id, tipo);
            this.connection.commit();
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
    }

    public void updateAbonoLocal(AbonoVos obj) throws Exception {
        AbonoDao dao = new AbonoDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateAbono(obj);
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

    public void deleteAbonoLocal(Long idusu, String tipoId) throws Exception
    {
        AbonoDao dao = new AbonoDao();
        AbonoFuncionDao dao2 = new AbonoFuncionDao();
        try
        {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao2.setConnection(connection);
            for (FuncionCostoLocalidadVos func : dao.getAbono(idusu, tipoId).getFunciones() )
            {
                dao2.deleteAbonoFuncion(dao.getAbono(idusu, tipoId).getIdFest(), idusu, tipoId,func.getIdFuncion(), func.getIdLocalidad() );
            }
            dao.deleteAbono(idusu, tipoId);

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

    public String devolverAbonoLocal(Long idusu, String tipoId) throws Exception {
        AbonoDao dao = new AbonoDao();
        AbonoVos ab = dao.getAbono(idusu, tipoId);
        FestivAndesDao fest = new FestivAndesDao();
        FestivandesVos f = fest.getFestival(ab.getIdFest());
        AbonoFuncionDao dao2 = new AbonoFuncionDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao2.setConnection(connection);

            Calendar cal = Calendar.getInstance();
            cal.setTime(f.getFechast());
            cal.add(Calendar.WEEK_OF_YEAR, -3);

            if (new Date().compareTo(cal.getTime()) <= 0)
            {
                for (FuncionCostoLocalidadVos func : dao.getAbono(idusu, tipoId).getFunciones() )
                {
                    dao2.deleteAbonoFuncion(dao.getAbono(idusu, tipoId).getIdFest(), idusu, tipoId,func.getIdFuncion(), func.getIdLocalidad() );
                }
                dao.deleteAbono(idusu, tipoId);
                deleteAbonoLocal(idusu, tipoId);
            }
            dao.deleteAbono(idusu, tipoId);
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
        return "Pagar al Senor(a): " + "el total del abono numero: " ;
    }
}
