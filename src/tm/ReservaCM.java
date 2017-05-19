package tm;

import dao.FuncionDao;
import dao.ReservaDao;
import dao.UsuarioDao;
import vos.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class ReservaCM extends TransactionManager {
    public ReservaCM(String contextPathP) {
        super(contextPathP);
    }

    public ArrayList<ReservaVos> getReservas() throws SQLException {
        ArrayList<ReservaVos> list = new ArrayList<ReservaVos>();
        ReservaDao dao = new ReservaDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getReservas();
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

    public ReservaVos getReserva(Long idReserva, Long idusu, String tipoId) throws SQLException {
        ReservaVos elem;
        ReservaDao dao = new ReservaDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            elem = dao.getReserva(idReserva, idusu, tipoId);
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

    public void createReserva(ReservaVos obj, Long id, String tipo) throws Exception {
        ReservaDao dao = new ReservaDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addReserva(obj, id, tipo);
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

    public void updateReserva(ReservaVos obj) throws Exception {
        ReservaDao dao = new ReservaDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateReserva(obj);
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

    public void deleteReserva(Long idReserva, Long idusu, String tipoId) throws Exception {
        ReservaDao dao = new ReservaDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteReserva(idReserva, idusu, tipoId);
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

    public ArrayList<ReservaVos> reservaMultiple(int cantSillas, Long idFuncion, Long idLocalidad, Long idusu, String tipoId) throws Exception {

        ArrayList<ReservaVos> retorno = new ArrayList<>();

        FuncionDao fun = new FuncionDao();
        ReservaDao dao = new ReservaDao();
        UsuarioDao usu = new UsuarioDao();

        this.connection = getConnection();
        this.connection.setAutoCommit(false);

        usu.setConnection(this.connection);
        dao.setConnection(connection);
        fun.setConnection(connection);

        UsuarioVos usuAct = usu.getUsuario(idusu, tipoId);
        ArrayList<SillaVos> list = new ArrayList<>();
        try {

            if (usuAct.getRol().equals("Usuario Registrado")) {
                list = dao.reservaMultiple(idusu, tipoId, idLocalidad, idFuncion);
                int numSilla = -1;
                int filas = -1;
                int cantidadSillas = cantSillas;
                for (SillaVos silla : list) {
                    filas = filas == -1 ? silla.getFila() : filas;
//
                    if (silla.getFila() == filas) {
                        if (numSilla != -1 && silla.getNumero() == numSilla + 1) {
                            cantidadSillas--;

                        } else if (numSilla == -1) {
                            numSilla = silla.getNumero();
                            cantidadSillas--;
                        } else {
                            cantidadSillas = cantSillas;
                            numSilla = -1;
                        }
                    } else {
                        filas = -1;
                        cantidadSillas = cantSillas;
                        numSilla = -1;
                    }
                    if (cantidadSillas == 0) {
                        break;
                    }
                }

                System.out.println(cantidadSillas + "vs " + cantSillas + ": " + numSilla);

                if (cantidadSillas == 0) {
                    for (int i = numSilla - cantSillas; i <= cantSillas; i++) {
                        FuncionVos f = fun.getFuncion(idFuncion);
                        Long lugar = 0L;
                        if (f != null) {
                            lugar = f.getIdLugar();
                        }
                        ReservaVos reserva = new ReservaVos();
                        reserva.setIdReserva(dao.siguienteID());
                        reserva.setFechaCompra(new Date());
                        reserva.setIdFuncion(idFuncion);
                        reserva.setFila(filas);
                        reserva.setPolugares(numSilla);
                        reserva.setIdLocalidad(idLocalidad);
                        reserva.setIdLugares(lugar);
                        reserva.setIdUsu(idusu);
                        reserva.setTipoId(tipoId);

                        createReserva(reserva, idusu, tipoId);

                        retorno.add(reserva);
                    }
                    connection.commit();
                }
            }

        } catch (SQLException e) {
            System.err.println("SQLException:" + e.getMessage());
            e.printStackTrace();
            connection.rollback();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException:" + e.getMessage());
            e.printStackTrace();
            connection.rollback();
            throw e;
        } finally {
            closeDAO(dao);
        }
        return retorno;
    }

    public String devolverBoleta(Long idBoleta, Long idusu, String tipoId) throws Exception {
        ReservaDao dao = new ReservaDao();
        ReservaVos resrv = getReserva(idBoleta, idusu, tipoId);
        FuncionDao fun = new FuncionDao();
        FuncionVos f = fun.getFuncion(resrv.getIdFuncion());

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            Calendar cal = Calendar.getInstance();
            cal.setTime(f.getFecha());
            cal.add(Calendar.DAY_OF_YEAR, -5);

            if (new Date().compareTo(cal.getTime()) <= 0) {
                deleteReserva(idBoleta, idusu, tipoId);
            }
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
        return "Pagar al Senor(a): " + "el total de la boleta numero: " + resrv.getIdReserva() + "\n" + "Total: ";
    }

    public ArrayList<UsuarioVos> getClienteFiel( int num) throws SQLException {
        ArrayList<UsuarioVos> list = new ArrayList<UsuarioVos>();
        ReservaDao dao = new ReservaDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.clienteFiel(num);
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
}
