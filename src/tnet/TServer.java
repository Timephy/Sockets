
package tnet;

import tlist.TListKey;
import tnet.sockets.TSocket;
import tnet.sockets.TServerSocket;
import tnet.communication.TServerCom;

import java.io.IOException;

public class TServer
{

    private TServerSocket socket;

    private TServerCom com;

    private TListKey<TSocket, Integer> clients = new TListKey<TSocket, Integer>();

    private boolean open = false;;

    public TServer()
    {

    }

    public TServerCom getCom()
    {
        return com;
    }

    public void open(int port)
    {
        close();

        try {
            socket = new TServerSocket(port);
            com = new TServerCom(clients);
            open = true;
        } catch (IOException e) {
            close();
            e.printStackTrace();
        }

    }

    public void close()
    {
        open = false;
        if (socket != null)
        {
            socket.close();
            socket = null;
        }
        if (com != null)
        {
            com.disable();
        }
        com = null;
    }

    public boolean acceptClient()
    {
        try {
            TSocket s = socket.accept();
            if (s != null)
            {
                clients.add(s);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isOpen()
    {
        return open;
    }

    public static void main(String[] args)
    {
        TServer s = new TServer();
        s.open(8345);
        TServerCom c = s.getCom();

        while (true)
        {
            //System.out.println(s.clients.length());
            s.acceptClient();
            c.write("hi");
        }
    }

}
