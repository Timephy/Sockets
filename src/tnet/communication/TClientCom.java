
package tnet.communication;

import tnet.sockets.TSocket;
import tnet.TClient;

import tlist.TListKey;

import java.io.IOException;

public class TClientCom
{

    //// FIELDS ////

    private TClient client;

    //// CONSTRUCTORS ////

    public TClientCom(TClient client)
    {
        this.client = client;
    }

    //// READ & WRITE ////

    public <D> TNetData<D> waitRead()
    {
        TNetData<D> data = null;
        if (canCommunicate()) // not needed, but stops unneeded tries
        {
            while (data == null)
            {
                data = read();
            }
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
        TSocket socket = client.getSocket();
        if (canCommunicate()) {
            try {
                D obj = socket.<D>read();
                if (obj != null)
                {
                    return new TNetData<D>(obj, socket.key());
                }
            } catch (IOException | ClassNotFoundException e) {
                errorWhileCommunicating(e);
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
        if (canCommunicate()) {
            try {
                client.getSocket().<D>write(obj);
            } catch (IOException e) {
                errorWhileCommunicating(e);
            }
        }
    }

    private void errorWhileCommunicating(Exception e) // ClassNotFoundException and IOException
    {
        //e.printStackTrace();
        System.out.println("[TClientCom] Error while communicating, going to disconnect.");
        client.disconnect();
    }

    private boolean canCommunicate()
    {
        boolean can = false;
        if (client != null) {
            if (client.isConnected()) {
                can = true;
            }
        }
        return can;
    }

}
