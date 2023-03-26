package org.example.server;

import org.example.d_hellman.SecretKeyExchange;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.example.util.CipherUtils.decrypt;
import static org.example.util.CipherUtils.encrypt;


/**
 * @author Winner Musole
 */
public class Server implements Runnable {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ServerResponse serverResponse;
    private StreamHandler streamHandler;
    private SecretKeyExchange secretKeyExchange;

    public void connectServer(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        serverResponse.promptConnectionOpen();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public void handleClientStream(){
        initSecretKeyExchange();
        streamHandler = new StreamHandler(socket, serverResponse);
        streamHandler.setSecretKeyExchange(secretKeyExchange);
        streamHandler.start();
    }
    @Override
    public void run() {

            try{
                connectServer(port);
                System.out.println("Server running on port " + port);
                acceptClientConnection();
            } catch (IOException e){
                System.out.println("Thread was interrupted " + e.getMessage());
            }
            serverResponse.promptConnectionEstablished();
            handleClientStream();

    }

    private void acceptClientConnection() throws IOException{
        this.socket = serverSocket.accept();
    }

    public void initSecretKeyExchange(){
        this.secretKeyExchange = new SecretKeyExchange();
        secretKeyExchange.init();
    }

    public void encryptMessage(String decryptedMessage){

        String encryptedMessage;
        try {
            encryptedMessage = encrypt(decryptedMessage,streamHandler.getSecretKeyExchange().getAESKey());
            serverResponse.showEncryptedMessage(encryptedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void decryptMessage(String encryptedMessage){
        try{
            String decryptedMessage;
            decryptedMessage = decrypt(encryptedMessage, streamHandler.getSecretKeyExchange().getAESKey());
            serverResponse.showDecryptedMessage(decryptedMessage);
        } catch (Exception e){
            System.out.println(e);
        }
    }


}
