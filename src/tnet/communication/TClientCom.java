
package tnet.communication;

import tnet.sockets.TSocket;
import tnet.TNetData;

import tlist.TListKey;

public class TClientCom
{

    //// FIELDS ////

    protected TListKey<TSocket, Integer> clients;

    private int uid = 0;

    protected boolean enabled = false;

    //// CONSTRUCTORS ////

    /**
     * only for inheritation
     */
    public TClientCom()
    {

    }

    public TClientCom(TSocket client)
    {
        this.clients = new TListKey<TSocket, Integer>();
        this.clients.add(client);
        enabled = true;
    }

    //// READ & WRITE ////

    public <D> TNetData<D> waitRead()
    {
        TNetData<D> data = null;
        while (data == null)
        {
            data = read();
        }
        return data;
    }

    /**
     * Reads the first object possible from all connection-InputStreams
     *
     * @return The object read from {@link TSocket#in}
     * @throws IOException If there was an error reading from the InputStream
     */
    public <D> TNetData<D> read()
    {
        for (TSocket client : clients)
        {
            D obj = client.<D>read();
            if (obj != null)
            {
                return new TNetData<D>(obj, client.key());
            }
        }
        return null;
    }

    /**
     * Writes an object to all Streams
     *
     * @param D obj The object to be written
     */
    public <D> void write(D obj)
    {
        for (TSocket client : clients)
        {
            client.<D>write(obj);
        }
    }

    public int getUID()
    {
        return uid;
    }

    public void disable()
    {
        enabled = false;
    }

}
