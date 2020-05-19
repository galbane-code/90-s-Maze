package Server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy
{
    /**
     * an interface for all server strategies to implement.
     * @param inputStream
     * @param outputStream
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException, InterruptedException;

}
