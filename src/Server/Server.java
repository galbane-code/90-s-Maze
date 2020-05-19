package Server;

//import java.io.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.*;

public class Server implements Runnable
{
    /**
     *The class represents a server containing a serverStrategy that will handle different client requests.
     * each client is being manipulated as a thread - as part of a threadPool.
     */

    private IServerStrategy strategy;
    private int listenTime;
    private int port;
    private volatile boolean stop;
    private ExecutorService executor;
    public static String ThreadPoolSize;


    public Server(int port, int listenTime , IServerStrategy strategy) throws IOException
    {
        Configurations.propertiesCreation();// init the properties by which the server will work (threadPool size, searching algorithm etc.)
        this.strategy = strategy;
        this.listenTime = listenTime;
        this.port = port;
        this.stop = false;
        this.executor = Executors.newFixedThreadPool(Integer.parseInt(ThreadPoolSize));
    }


    public void start ()
    {
        new Thread(()->
        {
            run();
        }).start();
    }


    @Override
    public void run()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listenTime);

            while (!stop)
            {
                try
                {
                    Socket clientSocket = serverSocket.accept();

                    Thread r = new Thread(() ->
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

            try
            {
                serverSocket.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Error closing server", e);
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


    public synchronized void stop() throws InterruptedException {
        Thread.sleep(10000);
        this.stop = true;
        executor.shutdownNow();

    }


    public void handleClient(Socket clientSocket)
    {
        /**
         * handles the client request using a specific server strategy
         */

        try
        {
            OutputStream outToClient = clientSocket.getOutputStream();
            InputStream inFromClient = clientSocket.getInputStream();

            strategy.handleClient(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        }

        catch(IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class Configurations
    {
        public static void propertiesCreation() throws IOException
        {
            /**
             * writes the properties to the config file
             */
            try (OutputStream output = new FileOutputStream("resources\\config.properties")) {

                Properties prop = new Properties();

                // set the properties value
                prop.setProperty("ThreadPoolSize", "3");
                prop.setProperty("ASearchingAlgorithm", "BestFirstSearch");
                prop.setProperty("AMazeGenerator", "MyMazeGenerator");

                // save properties to project root folder
                prop.store(output, null);


                /**
                 * reads the properties from the config file
                 * static variables init
                 */
            try
            {
                InputStream is = Configurations.class.getClassLoader().getResourceAsStream("config.properties");
                Properties properties = new Properties();
                properties.load(is);

                Server.ThreadPoolSize = properties.getProperty("ThreadPoolSize");
                ServerStrategySolveSearchProblem.searchingAlgorithmString = properties.getProperty("ASearchingAlgorithm");
                ServerStrategyGenerateMaze.mazeGeneratorString = properties.getProperty("AMazeGenerator");

            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }


    }
}}
