package multiThreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// Server implementeert de interface Runnable is daardoor te starten binnen
// een aparte thread.
public class Server implements Runnable {

    private ArrayList<Client> clients;
    private ServerSocket listener;
    private boolean stopping = false;
    private int port;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<Client>();
    }

    @Override
    public void run() {
        try {
            this.listener = new ServerSocket(port);

            System.out.println("Server started");

            // We luisteren continu naar clients die willen connecten
            while (!stopping) {
                Socket socket = listener.accept();

                System.out.println("Connected to " + socket.getInetAddress().toString());

                // Voor iedere client die connect, starten we weer een nieuwe thread
                // die de communicatie met deze client afhandelt
                Client client = new Client(socket);
                new Thread(client).start();

                // De aangemaakte thread plaatsen we in een ArrayList, zodat we een
                // lijst krijgen met alle connected clients.
                this.clients.add(client);
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
        // kunnen we hier met een for-each doorheen lopen:
        for (Client client : clients) {
            client.stop();
        }
    }
}
