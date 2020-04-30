package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    private IServerStrategy strategy;
    private int max_pool;
    private int port;
    private volatile boolean stop;

    public Server(int port, int max_pool , IServerStrategy strategy)
    {
        this.strategy = strategy;
        this.max_pool = max_pool;
        this.port = port;
        this.stop = false;
    }



    public void run()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true)
            {
                try {
                    Socket clientSocket = serverSocket.accept();

                    InputStream inFromClient = clientSocket.getInputStream();
                    OutputStream outToClient = clientSocket.getOutputStream();

                    this.strategy.handleClient(inFromClient, outToClient);

                    inFromClient.close();
                    outToClient.close();
                    clientSocket.close();
                }
                catch (ClassNotFoundException e) {
                    System.out.println("Class not found");
                }
                catch (IOException e) {
                    System.out.println("Where are the clients??");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}

   // public void stop()
   // {
   //     this.stop = true;
   // }


