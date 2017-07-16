
package tnet.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.Socket;

import tlist.TListKey;

/**
 * Has a ServerSocket and manages the connections
 */
public class TServerSocket
{

    //// FIELDS ////

    /**
     * The ServerSocket of this TServerSocket
     */
    private ServerSocket serverSocket;

    private boolean open = false;

    private int timeout = 50; // in ms

    private int clientCounter = 0;

    //// CONSTRUCTOR ////

    public TServerSocket()
    {

    }

    /**
     * Saves the port and runs {@link #open()}
     *
     * @param int port The port the {@link #socket} is going to connect to when {@link #open()} is called
     * @throws IOException if e.g. port is already used
     */
    public TServerSocket(int port) throws IOException
    {
        open(port);
    }

    //// SETUP CONNECTION ////

    /**
     * Creates the {@link #serverSocket} with the port and sets SoTimeout
     *
     * @throws IOException if e.g. the port is already used
     */
    public void open(int port) throws IOException
    {
        open = true;
        serverSocket = new ServerSocket(port);
        setSoTimeout(timeout);
    }

    /**
     * Closes the socket
     */
    public void close()
    {
        if (isOpen())
        {
            try {
                open = false;
                serverSocket.close(); //also closes socket
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //// METHODS ////

    /**
     * @return The port {@link #socket} is connecting to, if is closed returns -1
     */
    public int getPort()
    {
        int port = -1;
        if (isOpen())
        {
            return serverSocket.getLocalPort();
        }
        return port;
    }

    /**
     * @return If the ServerSocket is open
     */
    public boolean isOpen()
    {
        return open;
    }

    /**
     * Sets the timeout of the socket
     *
     * @param int timeout The new timeout in ms
     */
    public void setSoTimeout(int timeout)
    {
        try {
            serverSocket.setSoTimeout(timeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accepts one socket's connection request and returns that socket
     *
     * @throws IOException if an I/O error occurs
     */
    public TSocket accept() throws IOException
    {
        Socket s = null;
        try {
            s = serverSocket.accept();
        } catch (SocketTimeoutException e) {
            //e.printStackTrace(); // just no-one wanted to connect...
        }

        TSocket tsocket = null;
        if (s != null)
        {
            tsocket = new TSocket(s);
            tsocket.setUID(clientCounter);
            tsocket.write(clientCounter);
            clientCounter++;
        }
        return tsocket;
    }

    public static void main(String[] args) throws Exception
    {
        TServerSocket s = new TServerSocket(4831);
        TSocket socket = null;
        while (socket == null) {
            socket = s.accept();
        }
        //c.write(true);
        Thread.sleep(100);
        while (true) {

        }
        //s.close();
    }

}
