
package tnet.communication;

import tnet.sockets.TSocket;
import tnet.TNetData;

import tlist.TListKey;

public class TServerCom
{

    protected TListKey<TSocket, Integer> clients;

    protected boolean enabled = false;

    public TServerCom(TListKey<TSocket, Integer> clients)
    {
        this.clients = clients;
        enabled = true;
    }

    /**
     * Reads incoming messages from one Socket (IP)
     *
     * @param String ip The IP of the socket to read from
     * @return The object of type <D>
     */
    public <D> TNetData<D> read(int uid)
    {
        TSocket client = clients.getKey(uid);
        if (client != null) {
            D obj = client.<D>read();
            return new TNetData<D>(obj, client.key());
        } else {
            return null;
        }
    }

    /**
     * Writes an object only to one Stream with the IP
     *
     * @param D obj The object to be written
     * @param String ip The IP of the Socket to write to
     * @throws IOException If an I/O error occurs
     */
    public <D> void write(D obj, int uid)
    {
        TSocket client = clients.getKey(uid);
        if (client != null) {
            client.<D>write(obj);
        } else {
            System.out.println("TCommunicator tried to write to not existing TClientCom " + uid);
        }
    }

    /**
     * Writes an object only to all Streams (all clients)
     *
     * @param D obj The object to be written
     * @throws IOException If an I/O error occurs
     */
    public <D> void write(D obj)
    {
        for (TSocket client : clients)
        {
            client.<D>write(obj);
        }

    }

    public void disable()
    {
        enabled = false;
    }

}
