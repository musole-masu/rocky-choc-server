package org.example;


import com.formdev.flatlaf.FlatLightLaf;
import org.example.server.Server;
import org.example.ui.MainFrame;

import javax.swing.*;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("WELCOME TO ROCKY CHOC ! :)");

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex){
            System.err.println("Failed to load UI Flat");
        }
        Server server = new Server();
        SwingUtilities.invokeLater(() -> new MainFrame(server));

    }
}