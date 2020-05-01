package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server
{
    private IServerStrategy strategy;
    private int max_pool;
    private int port;
    private volatile boolean stop;

    public Server(int port, int max_pool , IServerStrategy strategy) throws IOException
    {
        this.strategy = strategy;
        this.max_pool = max_pool;
        this.port = port;
        this.stop = false;
    }



    public void start() throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    OutputStream outToClient = clientSocket.getOutputStream();
                    InputStream inFromClient = clientSocket.getInputStream();

                    this.strategy.handleClient(inFromClient, outToClient);

                    inFromClient.close();
                    outToClient.close();
                    clientSocket.close();
                }
                catch (ClassNotFoundException e)
                {
                    System.out.println("Class not found");
                }
            }
            }

        catch (IOException e) {
            System.out.println("Where are the clients??");
        }
    }


        public void stop()
        {
            this.stop = true;
        }

}
