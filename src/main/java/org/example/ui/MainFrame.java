package org.example.ui;

import org.example.CryptoInterface;
import org.example.server.Server;
import org.example.server.ServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener, ServerResponse {

    private Server server;
    boolean isServerRunning = false;

    private JPanel jPanel1;
    private JLabel label_port;

    private JTextField text_port;

    private JSeparator separator1;

    private JButton btn_start;
    private JButton btn_stop;
    private JLabel server_status_label;


    public MainFrame(Server server){
        super("ROCKY SERVER");

        jPanel1 = new JPanel();

        label_port = new JLabel("Port Number");
        text_port = new JTextField();
        separator1 = new JSeparator();

        btn_start = new JButton("Start");
        btn_start.setBackground(Color.GREEN);

        btn_stop = new JButton("Stop");
        btn_stop.setBackground(Color.RED);

        btn_start.addActionListener(this);
        btn_stop.addActionListener(this);
        server_status_label = new JLabel();

        if (!isServerRunning){
            server_status_label.setText("Server Down");
            server_status_label.setForeground(new Color(185, 21, 51));
        }

        jPanel1.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        jPanel1.setLayout(new GridLayout(0,1));

        jPanel1.add(label_port);
        jPanel1.add(text_port);
        jPanel1.add(separator1);
        jPanel1.add(btn_start);
        jPanel1.add(btn_stop);
        jPanel1.add(server_status_label);

        this.add(jPanel1, BorderLayout.CENTER);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.server = server;

        server.setServerResponse(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_start) startServer();
        else if (e.getSource() == btn_stop) stopServer();
    }

    public void startServer(){
        int port;
        try{
            port = Integer.parseInt(text_port.getText());
            server.setPort(port);
            new Thread(server).start();
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Type Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    public void stopServer(){
        isServerRunning = false;
        btn_start.setEnabled(true);
        server_status_label.setText("Server Down");
        server_status_label.setForeground(new Color(185, 21, 51));
    }

    public JLabel getServer_status_label(){
        return server_status_label;
    }


    @Override
    public void promptConnectionOpen() {
        getServer_status_label().setText("Server Running .. \n Waiting for Clients ...");
        getServer_status_label().setForeground(new Color(59, 192, 115));

        isServerRunning = true;
        text_port.setText("");
        btn_start.setEnabled(false);
    }

    @Override
    public void promptConnectionEstablished() {
    }

    @Override
    public void showMessageReceived(String m) {
    }

    @Override
    public void promptMessageReceived() {
    }
}


