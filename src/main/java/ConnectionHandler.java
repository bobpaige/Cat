import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Once the server accepts a remote connection, this class is instantiated for
 * each connection
 * 
 *
 */
public class ConnectionHandler implements Runnable {

    private static Set<ConnectionHandler> allConnections = new HashSet<ConnectionHandler>();
    private Socket connection;

    public ConnectionHandler(Socket connection) {
        this.connection = connection;
        allConnections.add(this);
    }

    private Writer wout = null;

    @Override
    public void run() {
        String remoteName = connection.getRemoteSocketAddress().toString();
        String s;
        try {
            System.out.println("Accepted connection");
            OutputStream output = connection.getOutputStream();
            InputStream input = connection.getInputStream();
            Reader rin = new InputStreamReader(input);
            BufferedReader brin = new BufferedReader(rin);
            wout = new OutputStreamWriter(output);

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
                    // build the message
                    String message = remoteName + ": " + s + "\n";

                    // print message out to all clients
                    for (ConnectionHandler conn : allConnections) {
                        conn.send(message);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Closing connection");
        }
    }

    public void send(String message) throws IOException {
//        System.out.print(message);
        wout.write(message);
        wout.flush();
    }
}
