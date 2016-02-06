
/* Java source code for Chat Server
 * Original code written by: Bob Paige
 * Additional editing by: Jon Paige
 * Created January 19, 2016
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s;
        int portNumber;
        try {
            portNumber = Integer.parseInt(System.getProperty("port", "1337"));
            // System.out.println("Enter port number for server: ");
            // portNumber = scan.nextInt();
            System.out.println("Waiting for connection");
            ServerSocket socket = new ServerSocket(portNumber);
            Socket connection = socket.accept();

            String remoteName = connection.getRemoteSocketAddress().toString();

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
            // socket.close();
            System.out.println("Closing connection");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}