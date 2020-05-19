package Client;

import java.io.InputStream;
import java.io.OutputStream;

public interface IClientStrategy
{
    /**
     * an interface for all client strategies to implement.
     * @param inFromServer
     * @param outToServer
     */
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
