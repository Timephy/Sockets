
package tnet.communication;

import tnet.sockets.TSocket;
import tnet.TServer;

import tlist.TListKey;

import java.io.IOException;

public class TServerCom implements TCom
{

    private TServer server;

    public TServerCom(TServer server)
    {
        this.server = server;
    }

    public <D> TNetData<D> read()
    {
        if (canCommunicate()) {
            for (TSocket client : server.getClients())
            {
                TNetData<D> data = this.<D>read(client.key());
                if (data != null)
                {
                    if (server.getWriteReadObjectsToAll())
                    {
                        write(data.getData());
                    }
                    return data;
                }
            }
        }
        return null;
    }

    /**
     * Reads incoming messages from one Socket (IP)
     *
     * @param String ip The IP of the socket to read from
     * @return The object of type <D>
     */
    public <D> TNetData<D> read(int uid)
    {
        TSocket client = server.getClients().getKey(uid);
        if (canCommunicate()) {
            try {
                D obj = client.<D>read();
                if (obj != null) {
                    return new TNetData<D>(obj, client.key());
                }
            } catch (IOException | ClassNotFoundException e) {
                errorWhileCommunicating(e, client);
                return null;
            }
        }
        return null;
    }

    /**
     * Writes an object only to one Stream with the IP
     *
     * @param D obj The object to be written
     * @param String ip The IP of the Socket to write to
     */
    public <D> void write(D obj, int uid)
    {
        if (canCommunicate()) {
            TSocket client = server.getClients().getKey(uid);
            try {
                client.<D>write(obj);
            } catch (IOException e) {
                errorWhileCommunicating(e, client);
            }
        } else {
            System.out.println("TCommunicator tried to write to not existing TClientCom " + uid);
        }
    }

    /**
     * Writes an object only to all Streams (all clients)
     *
     * @param D obj The object to be written
     */
    public <D> void write(D obj)
    {
        if (canCommunicate()) {
            for (TSocket client : server.getClients())
            {
                try {
                    client.<D>write(obj);
                } catch (IOException e) {
                    errorWhileCommunicating(e, client);
                }

            }
        }
    }

    private void errorWhileCommunicating(Exception e, TSocket socket) // ClassNotFoundException and IOException
    {
        //e.printStackTrace();
        System.out.println("[TServerCom] Error while communicating (Client with UID " + socket.getUID() + "), going to kick client.");
        server.kick(socket);
    }

    public boolean canCommunicate()
    {
        boolean can = false;
        if (server != null) {
            if (server.isOpen()) {
                can = true;
            }
        }
        return can;
    }

}
