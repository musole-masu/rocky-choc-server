package org.example.ui;

import org.example.server.Server;
import org.example.server.ServerResponse;
import org.example.util.LoggingMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class TerminalApplication implements ActionListener, ServerResponse {

    Server server;
    Scanner sc = new Scanner(System.in);

    public TerminalApplication(Server server){
        this.server = server;

        System.out.println("[--] ENTER SERVER INFO BELOW: " + LoggingMessage.POINT_DOWN);

        System.out.println(LoggingMessage.ANSI_YELLOW+"[--] PORT NUMBER: "+LoggingMessage.ANSI_RESET);
        int portNumber = sc.nextInt();

        startServer(portNumber);
        server.setServerResponse(this);


    }

    public void startServer(int portNumber){
        try{
            server.setPort(portNumber);
            new Thread(server).start();
        } catch (NumberFormatException e){
            LoggingMessage.printFailedConnect("Failed to start the server");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void promptConnectionOpen() {
        LoggingMessage.printSucceededMessage("Server Running and Waiting for Clients");
    }

    @Override
    public void promptConnectionEstablished() {
        LoggingMessage.printSucceededMessage("Client Connected");

    }

    @Override
    public void showMessageReceived(String m) {

    }

    @Override
    public void promptMessageReceived() {

    }

    @Override
    public void showPrivateKey(String k) {

    }

    @Override
    public void showDecryptedMessage(String decryptedMessage) {

    }

    @Override
    public void showEncryptedMessage(String encryptedMessage) {

    }
}
