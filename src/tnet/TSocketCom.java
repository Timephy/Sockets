
package tnet;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.Socket;

public class TSocketCom extends TSocket
{
    /**
     * A new TSocket with a preexisting Socket
     *
     * @param Socket socket The socket
     */
    public TSocketCom(Socket socket) throws IOException
    {
        super(socket);
    }

    /**
     * A new TSocket to connect to this IP and port and runs {@link #open()}
     *
     * @param String ip The IP the {@link #socket} is going to connect to when {@link #open()} is called
     * @param int port The port the {@link #socket} is going to connect to when {@link #open()} is called
     * @throws IOException if e.g. port is already used
     */
    public TSocketCom(String ip, int port) throws IOException
    {
        super(ip, port);
    }

    /**
     * Saves the ip and port and runs {@link #open()}
     *
     * @param String address The IP and port from String-Format "192.165.78.34:23422"
     * @throws IOException is thrown when an error happens while getting and creating the streams
     */
    public TSocketCom(String address) throws IOException
    {
        super(address);
    }

    //// communication methods ////

    /**
     * Reads an incoming Object, returns null when no object is able to be read
     *
     * @return The object, null if no object
     * @throws IOException If error while reading object
     */
    @SuppressWarnings("unchecked")
    public <D> D read() throws IOException
    {
        return connection.<D>read();
    }

    /**
     * Send an object of type T with {@link SocketConnection#out}
     *
     * @param Data obj The object to be sent
     */
    public <D> void write(D obj) throws IOException
    {
        connection.<D>write(obj);
    }
}
