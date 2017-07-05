
package tnet;

import tnet.sockets.TSocket;
import tnet.communication.TClientCom;

import java.io.IOException;

public class TClient
{

    private TSocket socket;

    private TClientCom com;

    private boolean connected = false;

    public TClient()
    {

    }

    public void connect(String ip, int port)
    {
        disconnect();

        try {
            socket = new TSocket(ip, port);
            com = new TClientCom(socket);

            connected = true;
        } catch (IOException e) {
            disconnect();
            e.printStackTrace();
        }

    }

    public TClientCom getCom()
    {
        return com;
    }

    public void disconnect()
    {
        connected = false;
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

    public boolean isConnected()
    {
        return connected;
    }

<<<<<<< HEAD
    public static void main(String[] args) throws Exception
    {
        TClient t = new TClient();
        t.connect("localhost", 8345);
        //t.connect("192.168.6.87", 8345);
        TClientCom c = t.getCom();
        Thread.sleep(100);
        System.out.println(c.read().getData());
        while(true)
        {

        }
=======
    public static void main(String[] args)
    {
        TClient t = new TClient();
        t.connect("localhost", 8345); //192.168.6.87
        TClientCom c = t.getCom();
        System.out.println(c.read().getData());
>>>>>>> 949c65f500582af8b97f1e2a790cfb8028f61948
    }

}
