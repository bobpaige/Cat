import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class AutoClient {

    public static void main(String[] args) {
        int portNumber = Integer.parseInt(System.getProperty("port", "1337"));
        String host = System.getProperty("host", "127.0.0.1");

        System.out.println("Sending to host " + host + ", port " + portNumber);
        while (true) {
            try {

                Socket socket = new Socket(host, portNumber);
                InputStream input = socket.getInputStream();
                Reader rin = new InputStreamReader(input);
                BufferedReader brin = new BufferedReader(rin);
                OutputStream out = socket.getOutputStream();
                System.out.println("Connection opened");
                Writer wout = new OutputStreamWriter(out);

                try {
                    int count = 1;
                    while (true) {
                        // send an automatic message to the server
                        wout.write("sending line " + count++ + "\n");
                        wout.flush();
                        
                        // read all messages from the server and output them
                        String in = null;
                        while ((in=brin.readLine())!=null){
                            System.out.println(in);
                        }
                        
                        // wait before sending more messages
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    System.out.println("Connection lost");
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                // e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
}
