package org.example;


import org.example.server.Server;
import org.example.ui.MainFrame;

import javax.swing.*;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("WELCOME TO ROCKY CHOC ! :)");

        Server server = new Server();
        SwingUtilities.invokeLater(() -> new MainFrame(server));

    }
}