package callbackInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ArrayList<Client> clients;
    private ServerSocket listener;
    private boolean stopping = false;
    private int port;
    private Callback callback;

    public Server(int port, Callback callback) {
        this.port = port;
        this.clients = new ArrayList<Client>();
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            this.listener = new ServerSocket(port);

            callback.messageReceived("Server started on port " + port);
            System.out.println("Server started");

            while (!stopping) {
                Socket socket = listener.accept();

                System.out.println("Connected to " + socket.getInetAddress().toString());

                Client client = new Client(socket, callback);
                this.clients.add(client);
                new Thread(client).start();
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        System.out.println("Server stopped");
    }

    public void stop() {
        System.out.println("Stopping server");

        this.stopping = true;

        // Doordat we alle clients in een ArrayList hebben geplaatst,
        // kunnen we hier met een for-each doorheen loopen:
        for (Client client : clients) {
            client.stop();
        }
    }

    public void broadcastMessage(String message) {
        for (Client client : clients) {
            client.sendMessage(message);
        }
    }
}
