package org.example.server;

import org.example.d_hellman.SecretKeyExchange;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.example.util.CipherUtils.*;

/**
 * @author Winner Musole
 */
public class StreamHandler extends Thread {

    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

    private SecretKeyExchange secretKeyExchange;
    private ServerResponse serverResponse;
    private boolean isClientConnected;

    public StreamHandler(Socket client, ServerResponse serverResponse){
        this.client = client;
        this.serverResponse = serverResponse;
        this.isClientConnected = true;
    }

    private void initStream(Socket client){
        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        super.run();
        initStream(client);

        try{
            receiveMessage(client);
        } catch (IOException e){
            e.printStackTrace();
            closeConnection();
            System.err.println("Shooting Down Server ...".toUpperCase());
        }
    }

    private void receiveMessage(Socket client) throws IOException{
        while (isClientConnected){
            String dataFromClient = in.readUTF();
            System.out.println("Message from client ".toUpperCase() +client.getRemoteSocketAddress() + " => " + dataFromClient);
            performOperation(retrieveCommand(dataFromClient), retrieveMessage(dataFromClient));

        }
    }


    private void performOperation(int operation, String message){
        if (operation == ENCRYPTED_MESSAGE){
            serverResponse.showMessageReceived(message);
            serverResponse.promptMessageReceived();

            System.out.println("Message Received".toUpperCase());
        } else if (operation == PUBLIC_KEY){
            System.out.println("Receiving public key from client ...".toUpperCase());

            try {
                System.out.println("Public key received by socket: ".toUpperCase() + message);

                try {
                    secretKeyExchange.receivePBKFromClient(message);
                } catch (InvalidKeySpecException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                }

                serverResponse.showPrivateKey(encodeBase64(secretKeyExchange.getAESKey().getEncoded()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else if(operation == READY){
            System.out.println("Client is ready to receive the key ".toUpperCase());
            sharePublicKey(secretKeyExchange.getPublicKeyEncoded());
        }
    }

    // request a new key from client
    public void requestNewKey(){
        System.out.println(REQUEST_NEW_KEY+PROTOCOL_SEP+secretKeyExchange.getPublicKeyEncoded());
        sendMessage(REQUEST_NEW_KEY, PROTOCOL_SEP, secretKeyExchange.getPublicKeyEncoded());
    }
    public void sendMessage(int command, String sep, String message){
        try {
            String toClient = command + sep + message;
            out.writeUTF(toClient);
            System.out.println("Sending: ".toUpperCase()+toClient);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sharePublicKey(String key){
        System.out.println("[DEBUG] "+ REQUEST_PUBLIC_KEY + PROTOCOL_SEP + key);
        sendMessage(REQUEST_PUBLIC_KEY, PROTOCOL_SEP, key);

    }

    public void setSecretKeyExchange(SecretKeyExchange secretKeyExchange){
        this.secretKeyExchange = secretKeyExchange;
    }
    public SecretKeyExchange getSecretKeyExchange(){
        return secretKeyExchange;
    }

    public void closeConnection(){
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
