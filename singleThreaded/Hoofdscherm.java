package singleThreaded;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Hoofdscherm extends JFrame implements ActionListener {

    private JButton startListening;
    private JButton dummyButton;
    private ServerSocket listener;

    public Hoofdscherm() {
        setTitle("Single Threaded");
        setSize(400, 300);
        setLayout(new FlowLayout());

        startListening = new JButton("Start Listening");
        startListening.addActionListener(this);
        add(startListening);

        dummyButton = new JButton("Dummy Button");
        add(dummyButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            listener = new ServerSocket(59090);
            System.out.println("The server is running...");

            // Deze continue loop blokkeert de user interface !
            while (true) {
                Socket socket = listener.accept();
                System.out.println("Connected to " + socket.getInetAddress().toString());
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
