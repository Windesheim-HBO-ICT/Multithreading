package callbackInterface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Hoofdscherm extends JFrame implements ActionListener, Callback {

    private JButton startListening;
    private JTextField port;
    private JButton dummyButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    private Server server;

    public Hoofdscherm() {
        setTitle("Callback Interface");
        setSize(550, 450);
        setLayout(new FlowLayout());
        
        port = new JTextField("59090", 10);
        add(port);
        
        startListening = new JButton("Start Listening");
        startListening.addActionListener(this);
        add(startListening);

        dummyButton = new JButton("Dummy Button");
        add(dummyButton);

        textArea = new JTextArea(20, 50);
        textArea.setAutoscrolls(true);
        scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // Bij het afsluiten van de applicatie, stoppen we ook de server
        // die op zijn beurt ook weer alle clients stopt
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopServer();
                System.exit(0);
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int p = Integer.parseInt(port.getText());
        server = new Server(p, this);
        new Thread(server).start();
    }

    @Override
    public void messageRecieved(String message) {
        textArea.append(message + "\n");

        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private void stopServer() {
        if (server != null) {
            server.stop();
        }
    }
}
