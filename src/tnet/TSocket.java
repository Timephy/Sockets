
package tnet;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import tlist.TListKeyObject;

/**
 * A TSocket is able to connect to a ServerSocket and exchange data
 */
public class TSocket implements TListKeyObject<String>
{
    /**
     * The Socket of this TSocket
     */
    private Socket socket;

    /**
     * The Socket and its streams saved in a SocketConnection
     */
    protected SocketConnection connection;

    /**
     * The IP the ServerSocket is going to {@link #open()} to
     */
    private String ip = "192.168.68.50";

    /**
     * The port the ServerSocket is going to {@link #open()} to
     */
    private int port = 4831;

    private int timeout = 5; // in ms

    /**
     * A new TSocket with a preexisting Socket
     *
     * @param Socket socket The socket
     */
    public TSocket(Socket socket) throws IOException
    {
        this.socket = socket;
        connection = new SocketConnection(socket);
        setSoTimeout(timeout);
    }

    /**
     * Saves the ip and port and runs {@link #open()}
     *
     * @param String ip The IP the {@link #socket} is going to connect to when {@link #open()} is called
     * @param int port The port the {@link #socket} is going to connect to when {@link #open()} is called
     * @throws IOException if e.g. port is already used
     */
    public TSocket(String ip, int port) throws IOException
    {
        this.ip = ip;
        this.port = port;
        open();
    }

    /**
     * Saves the ip and port and runs {@link #open()}
     *
     * @param String address The IP and port from String-Format "192.165.78.34:23422"
     * @throws IOException if e.g. port is already used
     */
    public TSocket(String address) throws IOException
    {
        int colon = address.indexOf(":");
        this.ip = address.substring(0, colon);
        this.port = Integer.parseInt(address.substring(colon+1));
        open();
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
     * Sets the timeout of the socket
     *
     * @param int timeout The new timeout in ms
     */
    public void setSoTimeout(int timeout)
    {
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the {@link #socket} with the IP and port
     *
     * @throws IOException if e.g. the port is already used
     */
    private void open() throws IOException
    {
        if (socket == null) {
            socket = new Socket(ip, port);
            connection = new SocketConnection(socket);
            socket.setSoTimeout(timeout);
        } else {
            System.out.println("[TClientSocket] open(): Socket was already open. Did nothing.");
        }
    }

    /**
     * Closes all streams and socket
     *
     * @throws IOException If an error occurs
     */
    public void close() throws IOException
    {
        connection.close(); //also closes socket
    }

    public String key()
    {
        return getConnectionIp();
    }

}
