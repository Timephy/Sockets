
package tnet.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import tlist.TListKeyObject;

/**
 * A Class to save a socket with its ObjectStreams
 */
public class TSocket implements TListKeyObject<Integer> // let is please stay Integer....
{
    /**
     * The socket itself
     */
    private Socket socket;

    /**
     * The ObjectInputStream
     */
    private ObjectInputStream in;

    /**
     * The ObjectOutputStream
     */
    private ObjectOutputStream out;

    private int uid = -1;

    /**
     * The timeout for reading
     */
    private int timeout = 5; // in ms

    /**
     * The timeout for trying to connect to a server (e.g. the server doesnt accept)
     */
    private int connectTimeout = 5000; // in ms

    /**
     * Runs {@link #init()} with the socket
     *
     * @param Socket the new Socket
     * @throws IOException is thrown when an error happens while getting and creating the streams
     */
    public TSocket(Socket socket) throws IOException
    {
        init(socket);
    }

    /**
     * Runs {@link #init()} with a new socket
     *
     * @param String ip The IP to connect the Socket to
     * @param int port The port to connect the Socket to
     * @throws IOException is thrown when an error happens while getting and creating the streams
     */
    public TSocket(String ip, int port) throws IOException, ClassNotFoundException
    {
        Socket socket = new Socket();

        InetSocketAddress address = new InetSocketAddress(ip, port);
        socket.connect(address, connectTimeout);

        init(socket);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Integer i = this.<Integer>read();
        //System.out.println(i); //debug
        setUID(i);
    }

    /**
     * Sets the ObjectStreams and sets SoTimeout
     *
     * @param Socket socket The socket to init from
     */
    private void init(Socket socket) throws IOException
    {
        this.socket = socket;
        if (socket != null)
        {
            out = new ObjectOutputStream(socket.getOutputStream()); //out always before in - otherwise gets stuck while creating ObjectInputStream
            in = new ObjectInputStream(socket.getInputStream());

            socket.setSoTimeout(timeout);
        } else {
            System.out.println("TSocket.init(): Parameter is null!");
        }

    }

    /**
     * Closes all streams and the socket
     */
    public void close()
    {
        //if (!socket.isClosed()) {
            try {
                //in.close(); // not needed
                //out.close(); // not needed
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
    }

    /**
     * @return The IP {@link #socket} is connecting to
     */
    public String getConnectionIp()
    {
        return socket.getInetAddress().getHostAddress();
    }

    /**
     * Returns the Port of the connection
     *
     * @return The port {@link #socket} is connecting to
     */
    public int getPort()
    {
        return socket.getLocalPort();
    }

    public <D> void write(D obj) throws IOException
    {
        out.writeObject(obj);
    }

    @SuppressWarnings("unchecked")
    public <D> D read() throws IOException, ClassNotFoundException
    {
        D obj = null;
        try {
            obj = (D) in.readObject();
        } catch (SocketTimeoutException e) {
            //System.out.println("No data was sent - so nothing was able to be read.");
            //e.printStackTrace();
        }
        return obj;
    }

    @Deprecated
    public <D> D waitUntilRead() throws IOException, ClassNotFoundException
    {
        D obj = null;
        while (obj == null)
        {
            obj = this.<D>read();
        }
        return obj;
    }

    public boolean isClosed()
    {
        return socket.isClosed();
    }

    protected void setUID(int uid)
    {
        this.uid = uid;
    }

    public int getUID()
    {
        return uid;
    }

    public Integer key()
    {
        return getUID();
    }

}
