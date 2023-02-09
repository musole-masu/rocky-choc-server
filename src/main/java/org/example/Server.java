package org.example;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dataInputStream = null;
    public Server(int port){
        try{
            // running the server on port specified
            serverSocket = new ServerSocket(port);
            System.out.println("Server started and running on port " + port);
            // wait for client connection
            socket = serverSocket.accept();
            System.out.println("Client connected, " + "Client IP : " + socket.getRemoteSocketAddress());

            dataInputStream = new DataInputStream(new BufferedInputStream(
                    socket.getInputStream())
            );

            String line = "";

            while(!line.equals("yahoo")){
                try{
                    line = dataInputStream.readUTF();
                    System.out.println("Data from Client ("+socket.getRemoteSocketAddress()+") : " + line);
                }
                catch (EOFException e){
                    System.out.println(e);
                    return;
                }catch (IOException e){
                    System.out.println(e);
                    return;
                }

            }

            System.out.println("Server disconnecting!");

            socket.close();
            dataInputStream.close();

        } catch (IOException e) {
            System.out.println(e);
        }


    }
    public static void main(String[] args) {
        System.out.println("WELCOME TO STOCK PUPPY ! :)");

        Server server = new Server(5005);
    }
}