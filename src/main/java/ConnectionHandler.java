import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * Once the server accepts a remote connection, this class is instantiated for
 * each connection
 * 
 *
 */
public class ConnectionHandler implements Runnable{

    private Socket connection;

    public ConnectionHandler(Socket connection) {
        this.connection = connection;

    }

    @Override
    public void run() {
        String remoteName = connection.getRemoteSocketAddress().toString();
        String s;
        try {
            System.out.println("Accepted connection");
            InputStream input = connection.getInputStream();
            OutputStream output = connection.getOutputStream();
            Reader rin = new InputStreamReader(input);
            BufferedReader brin = new BufferedReader(rin);
            Writer wout = new OutputStreamWriter(output);

            while (true) {
                s = brin.readLine();
                // System.out.println("Received '" + s + "' from client");
                if (s.startsWith("/")) {
                    // System.out.println("Command input on server");
                    if (s.contains("shutdown")) {
                        System.out.println("Server shutdown by: " + "{Address of client}");
                        break;
                    } else if (s.contains("help")) {
                        System.out.println("Help command called by: " + "{Address of client}");
                    } else if (s.contains("info")) {
                        System.out.println("Info command called by: " + "{Address of client}");
                    } else {
                        System.out.println("Unknown command called by: " + "{Address of client}");
                    }
                } else {
                    System.out.println(remoteName + ": " + s);
                    wout.write(remoteName + ": " + s + "\n");
                    wout.flush();
                }
            }
            System.out.println("Closing connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
