package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client
{
    /**
     *The class represents a client sending different requests from the server.
     * has a clientStrategy data member that represents the way the client will handle the server response
     * the clientStrategies implementations can be found in the RunCommunicateWithServer class
     */

    private IClientStrategy clientStrategy;
    private int port;
    private InetAddress serverIP;

    public Client(InetAddress serverIP, int port ,IClientStrategy clientStrategy)
    {
            this.clientStrategy = clientStrategy;
            this.port = port;
            this.serverIP = serverIP;
    }

    public void communicateWithServer()
    {
        try
        {
            Socket socket = new Socket(this.serverIP, this.port);
            OutputStream clientOutPutStream = socket.getOutputStream();
            InputStream clientInPutStream = socket.getInputStream();

            clientStrategy.clientStrategy(clientInPutStream, clientOutPutStream);
            socket.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
