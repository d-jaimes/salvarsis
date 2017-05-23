package jms;

;
import tm.FestivAndesCM;
import protocolos.ProtocoloFestival;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.jms.Message;
import java.sql.SQLException;

/**
 * Created by María del Rosario on 23/05/2017.
 */
public class FestivAndesJMS extends JMSManager<ProtocoloFestival>
{

    private static FestivAndesJMS instancia;

    private FestivAndesCM master;

    public FestivAndesJMS getInstance (FestivAndesCM transactionManager)
    {
        instancia = instancia == null ? new FestivAndesJMS() : instancia;
        instancia.master = transactionManager;
        return instancia;
    }

    public void onMessage(Message message)
    {
        try
        {
            OnMessageResponse response = onMessageSetUp(message, ALL_FESTIVALES_ASK, ALL_FESTIVALES_RESPONSE);
            if (response.isAnswer())
            {
                if(waiting)
                {
                    this.respuesta.addAll(protocolToList(response.getParams(), ProtocoloFestival.class));
                    this.numberApps++;
                }
            }
            else if (response.getQueue()!= null)
            {
                String protocol = ALL_FESTIVALES_RESPONSE + CONNECTOR + listToProtocol(FestivAndesCM.festivalesToProtocol(master.getFestivalesLocal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    void sendMessage() throws NamingException, JMSException
    {
        sendMessageSetUp(ALL_FESTIVALES_ASK, "");
    }
}
