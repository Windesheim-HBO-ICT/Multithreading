package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Start deze client meerdere keren op vanaf de commandline:
// java -classpath Threads.jar client.Startup [ipaddress] [port] [ClientName]
public class Startup {

    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please provide an ip address, port number and client name");
            return;
        }
        
        String ipaddress = args[0];
        int port = Integer.parseInt(args[1]);
        String clientName = args[2];

        try {
            socket = new Socket(ipaddress, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                writer.println("Message from " + clientName);
                Thread.sleep(2000);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
