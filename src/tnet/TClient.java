
package tnet;

import tnet.sockets.TSocket;
import tnet.communication.TClientCom;
import tnet.communication.TNetData;

import java.io.IOException;

public class TClient
{

    private TSocket socket;

    private TClientCom com;

    private boolean connected = false;

    public TClient()
    {

    }

    public void connect(String ip, int port) throws IOException
    {
        disconnect();

        System.out.println("[TClient] Trying to connected to " + ip + ":" + port);

        try {
            socket = new TSocket(ip, port);
            com = new TClientCom(this);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            connected = true;
            System.out.println("[TClient] Connected (UID is " + socket.getUID() + ")");
        } catch (IOException | ClassNotFoundException e) {
            disconnect();
            System.out.println("[TClient] Connection failed!");
            //throw e;
        }

    }

    public void disconnect()
    {
        connected = false;
        if (socket != null)
        {
            socket.close();
            socket = null;
            System.out.println("[TClient] Disconnected");
        }

        com = null;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public TSocket getSocket()
    {
        return socket;
    }

    public TClientCom getCom()
    {
        return com;
    }

    public int getUID()
    {
        return socket.getUID();
    }






    public static void main(String[] args)
    {
        TClient t = new TClient();
        try {
            t.connect("localhost", 8345);
            //t.connect("192.168.6.87", 8345);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TClientCom c = t.getCom();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (t.isConnected())
        {
            TNetData<String> d = c.read();
            String s = null;
            if (d != null) {
                s = d.getData();
            }
            if (s != null) {
                System.out.println(s);
            }
            //t.disconnect();
        }
    }

}
