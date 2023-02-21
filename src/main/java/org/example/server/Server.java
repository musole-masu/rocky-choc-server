package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private boolean executing;
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ServerResponse serverResponse;
    private StreamHandler streamHandler;

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
        streamHandler = new StreamHandler(socket, serverResponse);
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


}
