package org.example.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StreamHandler extends Thread {

    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

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
            System.err.println("Shooting Down Server ...");
        }
    }

    private void receiveMessage(Socket client) throws IOException{
        while (isClientConnected){
            String clientData = in.readUTF();
            System.out.println("Message from client / "+client.getRemoteSocketAddress() + " => " + clientData);
        }
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
