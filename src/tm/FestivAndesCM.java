package tm;

import dao.FestivAndesDao;
import exceptions.IncompleteReplyException;
import exceptions.NonReplyException;
import jms.FestivAndesJMS;
import jms.FestivalJMS;
import protocolos.ProtocoloFestival;
import vos.FestivandesVos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static tm.TMConstantes.NUMBER_APPS;
import static tm.TMConstantes.QUEUE_FESTIVAL;
import static tm.TMConstantes.QUEUE_FESTIVAL_RESPONSE;
import static utils.SQLUtils.timeToString;

/**
 * Created by María del Rosario on 11/04/2017.
 */

public class FestivAndesCM extends TransactionManager {

    public FestivAndesCM(String contextPathP) {
        super(contextPathP);
    }

    public ArrayList<FestivandesVos> getFestivalesLocal() throws SQLException {
        ArrayList<FestivandesVos> list = new ArrayList<FestivandesVos>();
        FestivAndesDao dao = new FestivAndesDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            list = dao.getFestivales();
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

    public FestivandesVos getFestivalLocal(Long id) throws SQLException {
        FestivandesVos elem;
        FestivAndesDao dao = new FestivAndesDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            elem = dao.getFestival(id);
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

    public List<ProtocoloFestival> getFestivalRemote() throws Exception, IncompleteReplyException {
        List<ProtocoloFestival> list = new LinkedList<>();
        try {
            this.connection = getConnection();
            this.connection.setAutoCommit(false);

            FestivAndesJMS jms = FestivAndesJMS.getInstance(this);
            jms.setUpJMSManager(NUMBER_APPS, QUEUE_FESTIVAL, QUEUE_FESTIVAL_RESPONSE, FestivAndesJMS.TOPIC_ALL_FESTIVALES_GLOBAL);
            list.addAll(jms.getResponse());
            connection.commit();
        } catch (NonReplyException e) {
            throw new IncompleteReplyException("No Reply from apps", list);
        } catch (IncompleteReplyException e) {
            List<ProtocoloFestival> partialResponse = (List<ProtocoloFestival>) e.getPartialResponse();
            list.addAll(partialResponse);
            throw new IncompleteReplyException("Incomplete Reply:", partialResponse);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            connection.rollback();
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException: " + e.getMessage());
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public void createFestivalLocal(FestivandesVos obj) throws Exception {
        FestivAndesDao dao = new FestivAndesDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.addFestival(obj);
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

    public void updateFestivalLocal(FestivandesVos obj) throws Exception {
        FestivAndesDao dao = new FestivAndesDao();

        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.updateFestival(obj);
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

    public void deleteFestivalLocal(Long id) throws Exception {
        FestivAndesDao dao = new FestivAndesDao();
        try {
            this.connection = getConnection();
            dao.setConnection(connection);
            dao.deleteFestival(id);
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

    @SuppressWarnings("unchecked")
    public List<ProtocoloFestival> getFestivalesRemote() throws Exception, IncompleteReplyException {
        List<ProtocoloFestival> list = new LinkedList<>();
        try {
            list = festivalesToProtocol(getFestivalesLocal());
            this.connection = getConnection();
            this.connection.setAutoCommit(false);

            FestivAndesJMS jms = FestivAndesJMS.getInstance(this);
            jms.setUpJMSManager(NUMBER_APPS, QUEUE_FESTIVAL, QUEUE_FESTIVAL_RESPONSE, FestivalJMS.TOPIC_ALL_FESTIVALES_GLOBAL);
            list.addAll(jms.getResponse());

            connection.commit();
        } catch (NonReplyException e) {
            throw new IncompleteReplyException("No Reply from apps", list);
        } catch (IncompleteReplyException e) {
            List<ProtocoloFestival> partialResponse = (List<ProtocoloFestival>) e.getPartialResponse();
            list.addAll(partialResponse);
            throw new IncompleteReplyException("Incomplete Reply:", partialResponse);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            connection.rollback();
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("GeneralException: " + e.getMessage());
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    public static List<ProtocoloFestival> festivalesToProtocol(ArrayList<FestivandesVos> festivalesList) {
        List<ProtocoloFestival> resultList = new LinkedList<>();
        for (FestivandesVos festival : festivalesList) {
            resultList.add(new ProtocoloFestival("APP3",festival.getIdfest(), "festival tortuguita", festival.getLugar(), timeToString(festival.getFechast()), timeToString(festival.getFechaend())));
        }
        return resultList;
    }
}
