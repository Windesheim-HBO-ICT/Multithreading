package multiThreaded;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Hoofdscherm extends JFrame implements ActionListener {

    private JButton startListening;
    private JButton dummyButton;

    private Server server;

    public Hoofdscherm() {
        setTitle("Multi Threaded");
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
        // We started hier een nieuwe thread, met daarin de server
        // Hierdoor blijft de UI thread beschikbaar voor afhandeling van user input 
        server = new Server(59090);
        new Thread(server).start();
    }

    private void stopServer() {
        if (server != null) {
            server.stop();
        }
    }
}
