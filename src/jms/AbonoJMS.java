package jms;

import protocolos.ProtocoloAbono;
import tm.AbonoCM;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

/**
 * Created by María del Rosario on 23/05/2017.
 */
public class AbonoJMS extends JMSManager<ProtocoloAbono>
{

    private ProtocoloAbono abono;
    private static AbonoJMS instancia;

    private AbonoCM master;

    public AbonoJMS getInstance (AbonoCM transactionManager)
    {
        instancia = instancia == null ? new AbonoJMS() : instancia;
        instancia.master = transactionManager;
        return instancia;
    }



    @Override
    public void onMessage(Message message)
    {
        try
        {
            OnMessageResponse response = onMessageSetUp(message, CREAR_ABONOS_ASK, CREAR_ABONOS_RESPONSE);
            if (response.isAnswer())
            {
                if(waiting)
                {
                    this.respuesta.add(protocolToObject(response.getParams(), ProtocoloAbono.class));
                    this.numberApps++;
                }
            }
            else if (response.getQueue()!= null)
            {
                String protocol = CREAR_ABONOS_RESPONSE + CONNECTOR + master.createAbonoLocal(protocolToObject(response.getParams(), ProtocoloAbono.class), 0L , "");
                enqueueResponse(response.getQueue(), protocol);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAbono(ProtocoloAbono abono)
    {
        this.abono = abono;
    }

    @Override
    void sendMessage() throws NamingException, JMSException
    {
        sendMessageSetUp(CREAR_ABONOS_ASK, abono.toString());
    }
}
