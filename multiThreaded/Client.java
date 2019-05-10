package multiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Client implementeert de interface Runnable is daardoor te starten binnen
// een aparte thread.
public class Client implements Runnable {

    private boolean stopping = false;
    private Socket socket;

    private BufferedReader reader;
    private PrintWriter writer;

    public Client(Socket socket) {
        this.socket = socket;

        try {
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        while (!stopping) {
            try {
                String message = this.reader.readLine();
                System.out.println("Received message from client: " + message);
                
                // We moeten nu nog iets met het ontvangen bericht.
                // Kijk voor een voorbeeld bij callbackInterface
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public void sendMessage(String message) {
        System.out.println("Sending message to client: " + message);
        this.writer.println(message);
    }

    public void stop() {
        this.stopping = true;
    }
}
