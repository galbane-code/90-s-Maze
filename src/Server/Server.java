package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    private IServerStrategy strategy;
    private int listenTime;
    private int port;
    private volatile boolean stop;
    private ExecutorService executor;

    public Server(int port, int listenTime , IServerStrategy strategy) throws IOException
    {
        this.strategy = strategy;
        this.listenTime = listenTime;
        this.port = port;
        this.stop = false;
        this.executor = Executors.newFixedThreadPool(3);
    }



    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listenTime);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    Runnable r = new Thread (()->
                    {
                        handleClient(clientSocket);
                    });
                    executor.execute(r);


                }
                catch (IOException e)
                {
                    System.out.println("Where are the clients?");
                }
            }
            }

        catch (SocketTimeoutException | SocketException e)
        {
            e.printStackTrace();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


        public void stop()
        {
            this.stop = true;
        }


    public void handleClient(Socket clientSocket)
    {
        try
        {
            // had to put the outPutStream in a var because of a failure. notice that!
            OutputStream outToClient = clientSocket.getOutputStream();
            InputStream inFromClient = clientSocket.getInputStream();

            this.strategy.handleClient(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run()
    {
        this.start();
    }
}
