
package tnet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * TClientSocket is the TSocket solution for the client side
 */
public class TClientSocket extends TSocketCom
{

    /**
     * Saves the ip and port and runs {@link #open()}
     *
     * @param String ip The IP the {@link #socket} is going to connect to when {@link #open()} is called
     * @param int port The port the {@link #socket} is going to connect to when {@link #open()} is called
     * @throws IOException if e.g. port is already used
     */

    public TClientSocket(String ip, int port) throws IOException
    {
        super(ip, port);
    }

    /**
     * Saves the ip and port and runs {@link #open()}
     *
     * @param String address The IP and port from String-Format "192.165.78.34:23422"
     * @throws IOException if e.g. port is already used
     */
    public TClientSocket(String address) throws IOException
    {
        super(address);
    }

    /**
     * @return The IP of this machine
     */
    public String getLocalIp()
    {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args)
    {
        try {
            TClientSocket t1 = new TClientSocket("localhost", 4831);
            //TClientSocket t2 = new TClientSocket("localhost", 4831);
            String s1 = t1.<String>read();
            System.out.println(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
