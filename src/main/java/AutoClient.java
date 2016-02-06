import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
                OutputStream out = socket.getOutputStream();
                System.out.println("Connection opened");
                Writer wout = new OutputStreamWriter(out);

                try {
                    int count = 1;
                    while (true) {
                        wout.write("sending line " + count++ + "\n");
                        wout.flush();
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
