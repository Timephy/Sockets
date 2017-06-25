
package tnet;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.Socket;

/**
 * A Class to save a socket with its ObjectStreams
 */
public class SocketConnection
{
    /**
     * The socket itself
     */
    public Socket socket;

    /**
     * The ObjectInputStream
     */
    public ObjectInputStream in;

    /**
     * The ObjectOutputStream
     */
    public ObjectOutputStream out;

    /**
     * Constructor which takes a Socket and saves the ObjectStreams
     *
     * @param Socket the new Socket
     * @throws IOException is thrown when an error happens while getting and creating the streams
     */
    public SocketConnection(Socket socket) throws IOException
    {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream()); //out always before in - otherwise gets stuck while creating ObjectInputStream
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Closes all streams and the socket
     */
    public void close()
    {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The IP {@link #socket} is connecting to
     */
    public String getConnectionIp()
    {
        return socket.getInetAddress().getHostAddress();
    }

    /**
     * @return The port {@link #socket} is connecting to
     */
    public int getPort()
    {
        return socket.getLocalPort();
    }

    /**
     * Reads an incoming Object, returns null when no object is able to be read
     *
     * @return The object, null if no object
     * @throws IOException If error while reading object
     */
    @SuppressWarnings("unchecked")
    public <D> D read() throws IOException
    {
        D obj = null;
        try {
            obj = (D) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            //no data recieved - will return null
        }
        return obj;
    }

    /**
     * Send an object of type T with {@link SocketConnection#out}
     *
     * @param Data obj The object to be sent
     */
    public <D> void write(D obj) throws IOException
    {
        out.writeObject(obj);
    }

}
