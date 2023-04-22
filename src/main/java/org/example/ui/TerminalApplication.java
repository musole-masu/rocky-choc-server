package org.example.ui;

import org.example.server.Server;
import org.example.server.ServerResponse;
import org.example.server.StreamHandler;
import org.example.util.LoggingMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import static org.example.util.CipherUtils.*;


public class TerminalApplication implements ActionListener, ServerResponse {

    Server server;
    Scanner sc = new Scanner(System.in);
    private StreamHandler streamHandler;


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
        try {
            LoggingMessage.printColoredText("Server received: " + m + " from client", LoggingMessage.ANSI_GREEN);
            Scanner sc = new Scanner(System.in);
            LoggingMessage.printColoredText("Type Yes to Decrypt Data and No to do nothing: ", LoggingMessage.ANSI_GREEN);
            String yes1 = sc.nextLine();
            if (yes1.equalsIgnoreCase("yes")){
                server.decryptMessage(m);
            }
            LoggingMessage.printColoredText("Type Yes to send Data to and No to do nothing: ", LoggingMessage.ANSI_GREEN);
            String yes2 = sc.nextLine();

            if (yes2.equalsIgnoreCase("yes")){
                LoggingMessage.printColoredText( "TO SEND DATA TO Client, FILL THE EMPTY SPACE BELOW "+LoggingMessage.CLOSED_MAILBOX+ ":", LoggingMessage.ANSI_GREEN);
                String toClient = sc.nextLine();
                LoggingMessage.printProgress("ENCRYPTING DATA ...", LoggingMessage.CLOSED_LOCK_WITH_KEY);
                server.encryptMessage(toClient);
            }
        } catch (Exception e){
            sc.close();
            e.printStackTrace();
        }


    }

    @Override
    public void promptMessageReceived() {

    }

    @Override
    public void showPrivateKey(String k) {

    }

    @Override
    public void showDecryptedMessage(String decryptedMessage) {
        LoggingMessage.printColoredText("Decrypted Data: " + decryptedMessage, LoggingMessage.ANSI_BLUE);
    }

    @Override
    public void showEncryptedMessage(String encryptedData) {
        LoggingMessage.printColoredText("Encrypted Data: " + encryptedData, LoggingMessage.ANSI_GREEN);
        // send encrypted data to client
        LoggingMessage.printProgress("Sending Data to client ...", LoggingMessage.PACKAGE);
        server.sendData(encryptedData);
        LoggingMessage.printOutStream("Server Data to client");

    }
}
