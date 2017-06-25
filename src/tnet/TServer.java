
package tnet;

import java.io.IOException;

public class TServer extends TServerSocket
{
    /**
     * Saves the port and runs {@link #open()}
     *
     * @param int port The port the {@link #socket} is going to connect to when {@link #open()} is called
     * @throws IOException if e.g. port is already used
     */
    public TServer(int port) throws IOException
    {
        super(port);
    }

    public <D> D read() throws IOException
    {
        for (SocketConnection connection : connections)
        {
            D obj = connection.<D>read();
            if (obj != null)
            {
                return obj;
            }
        }
        return null;
    }

    /**
     * Reads incoming messages from one Socket (IP)
     *
     * @param String ip The IP of the socket to read from
     * @return The object of type <D>
     * @throws IOException If an I/O error occurs
     */
    public <D> D read(String ip) throws IOException
    {
        return connections.getKey(ip).<D>read();
    }

    /**
     * Writes an object to all Streams
     *
     * @param D obj The object to be written
     */
    public <D> void write(D obj)
    {
        for (SocketConnection connection : connections)
        {
            try {
                connection.<D>write(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes an object only to one Stream with the IP
     *
     * @param D obj The object to be written
     * @param String ip The IP of the Socket to write to
     * @throws IOException If an I/O error occurs
     */
    public <D> void write(D obj, String ip) throws IOException
    {
        connections.getKey(ip).write(obj);
    }

    public int connections()
    {
        return connections.length();
    }

    public void _showConnectionIps()
    {
        for (SocketConnection connection : connections)
        {
            System.out.println(connection.getConnectionIp());
        }
    }

    public static void main(String[] args)
    {
        try {
            TServer s1 = new TServer(4831);
            System.out.println(s1.connections());
            while (s1.accept() == null) {
            }

            while (s1.accept() == null) {
            }

            while (s1.accept() == null) {
            }

            s1._showConnectionIps();

            System.out.println(s1.connections());

            Thread.sleep(500);

            //s1.clientConnections.get(0).write("abc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
