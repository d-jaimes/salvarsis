package tm;

import dao.*;
import protocolos.ProtocoloAbono;
import vos.*;
import utils.*;

import java.sql.SQLException;
import java.util.*;

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

    private void createAbono(ProtocoloAbono abono) throws Exception
    {
        ProtocoloAbono retAbono = null;

        FestivAndesDao daoFestival = new FestivAndesDao();
        FuncionDao daoFuncion = new FuncionDao();
        LocalidadDao localidad = new LocalidadDao();
        FuncionCostoLocalidadDao funcostloc = new FuncionCostoLocalidadDao();
        ShowsDao show = new ShowsDao();

        try
        {
           try
           {
               this.connection = getConnection();
               daoFestival.setConnection(this.connection);
               daoFuncion.setConnection(this.connection);
               localidad.setConnection(this.connection);
               funcostloc.setConnection(this.connection);

               retAbono = abonoToProtocol(createAbonoLocal(protocolToAbono(abono, daoFestival, localidad, daoFuncion, funcostloc),daoFestival, localidad, daoFuncion, funcostloc ));

           }
           catch(SQLException e)
           {
               System.out.println("La aplicacion no pudo crear el abono");
               System.err.println("SQLException:" + e.getMessage());
               e.printStackTrace();
               this.connection.rollback();
               throw e;
           }
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

    public List<ProtocoloAbono> createAbonoRemote(ProtocoloAbono abono) throws Exception
    {
        List <ProtocoloAbono> list = new LinkedList<>( );

        FestivAndesDao festivalDao = new FestivAndesDao();
        FuncionDao funcionDao = new FuncionDao();
        LocalidadDao localidadDao = new LocalidadDao();
        FuncionCostoLocalidadDao funcosloc = new FuncionCostoLocalidadDao();
        ShowsDao showDao = new ShowsDao();

        try
        {
            try
            {
                this.connection = getConnection();
                festivalDao.setConnection(this.connection);
                funcionDao.setConnection(this.connection);
                localidadDao.setConnection(this.connection);
                funcosloc.setConnection(this.connection);
                showDao.setConnection(this.connection);

                list.add(c);
            }
        }
    }

    private AbonoVos protocolToAbono(ProtocoloAbono abono, FestivAndesDao daoFestival, LocalidadDao localidad, FuncionDao daoFuncion, FuncionCostoLocalidadDao funcostloc) throws SQLException {
        AbonoVos abonoV = new AbonoVos();
        abonoV.setTipoId(abono.getTipoId());
        abonoV.setIdusu(abono.getIdUsuario());
        abonoV.setDescuento(Float.parseFloat(String.valueOf(abono.getDescuento())));
        abonoV.setFunciones(protocoloFuncionesToCostoLocalidad(abono.getFunciones(), localidad, daoFuncion, funcostloc));
        abonoV.setIdFest(daoFestival.searchFestival(abono.getNombreFestival()).getId());
        return abonoV;
    }

    private ArrayList<FuncionCostoLocalidadVos> protocoloFuncionesToCostoLocalidad(List<ProtocoloAbono.FuncionAbono> funciones, LocalidadDao localidad, FuncionDao daoFuncion, FuncionCostoLocalidadDao funcostloc) throws SQLException {
        ArrayList<FuncionCostoLocalidadVos> list = new ArrayList<>();
        for( ProtocoloAbono.FuncionAbono funcion : funciones )
        {
            FuncionCostoLocalidadVos funcionCosto = new FuncionCostoLocalidadVos( );

                funcionCosto.setCosto(funcionCosto.getCosto());
                funcionCosto.setIdLugar(funcion.search(funcion.getNombreEspectaculo(), funcionCosto.getIdLugar()));
                funcionCosto.setIdLocalidad(localidad.searchLocalidad(funcion.getNombreLocalidad()).getIdLocalidad());
                funcionCosto.setIdFuncion(SQLUtils.DateUtils.parseDateTime( funcion.getFecha( ) ));
            list.add( funcionCosto );
        }


        return list;
    }


    public ProtocoloAbono abonoToProtocol (AbonoVos abono, FestivAndesDao daoFestival, FuncionDao daoFuncion, ShowsDao daoEspectaculo, LocalidadDao daoLocalidad ) throws SQLException
    {
        ProtocoloAbono prot = null;
        FestivandesVos festival = daoFestival.getFestival(abono.getIdFest());

        if(festival != null)
        {
            prot = new ProtocoloAbono();
            prot.setNombreFestival("festival app3: " + festival.getIdfest());
            prot.setFunciones();
        }
//
//         ProtocoloAbono prot = null;
//        Festival festival = daoFestival.getFestival( abono.getIdFestival( ) );
//        if( festival != null )
//        {
//            prot = new ProtocoloAbono( );
//            prot.setNombreFestival( festival.getNombre( ) );
//            prot.setDescuento( abono.getDescuento( ) );
//            prot.setIdUsuario( abono.getIdUsuario( ) );
//            prot.setTipoId( abono.getTipoId( ) );
//            prot.setFunciones( funcionAbonoToProtocol( abono.getFunciones( ), daoFuncion, daoEspectaculo, daoLocalidad ) );
//            prot.setAppName( APP );
//        }
//        return prot;
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
