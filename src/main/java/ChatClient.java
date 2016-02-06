/* Java source code for Chat Client
 * Original code written by: Bob Paige
 * Additional editing by: Jon Paige
 * Created January 19, 2016
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class ChatClient {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int portNumber;
        String s;
        try {
            System.out.println("Enter port number for client");
            portNumber = scan.nextInt();
            System.out.println("Opening connection");
            Socket socket = new Socket("127.0.0.1", portNumber);
            //InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            System.out.println("Connection opened");
            //Reader rin = new InputStreamReader(input);
            //BufferedReader brin = new BufferedReader(rin);
            Writer wout = new OutputStreamWriter(output);
            
            while(true){
                s = scan.nextLine();
                //System.out.println(InetAddress.getLocalHost() + ": " + s + "\n");
                wout.write(s + "\n");
                wout.flush();
                if(s.startsWith("/")){
                    //System.out.println("Command input on client");
                    if(s.contains("exit")){
                        System.out.println("Exit function on client");
                        break;
                    } else if(s.contains("help")){
                        System.out.println("Available commands:\n  exit - closes connection with the server (and currently crashes the server)\n  help - displays help message\n  info - displays address of the host and port used for chat program\n  shutdown - shuts down the server");
                    } else if(s.contains("info")){
                        System.out.println("Host address is: " + InetAddress.getLocalHost() + "\nPort number is: " + portNumber);
                    } else {
                        System.out.println("Unknown command");
                    }
                }
            }
            socket.close();
            System.out.println("Closing connection");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
//IP address: InetAddress.getLocalHost()