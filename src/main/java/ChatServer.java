
/* Java source code for Chat Server
 * Original code written by: Bob Paige
 * Additional editing by: Jon Paige
 * Created January 19, 2016
 */

import java.io.IOException;
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
            ServerSocket socket = new ServerSocket(portNumber);
            while (true) {
                System.out.println("Waiting for connection");
                Socket connection = socket.accept();

                ConnectionHandler handler = new ConnectionHandler(connection);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}