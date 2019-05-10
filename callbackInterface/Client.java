package callbackInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private boolean stopping = false;
    private Socket socket;

    private BufferedReader reader;
    private PrintWriter writer;
    
    private Callback callback;

    public Client(Socket socket, Callback callback) {
        this.socket = socket;
        this.callback = callback;

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
                callback.messageRecieved(message);
            } catch (IOException ex) {
                System.out.println(ex);
                stopping = true;
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
