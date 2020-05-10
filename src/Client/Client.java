package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client
{
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
