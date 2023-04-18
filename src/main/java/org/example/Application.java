package org.example;


import com.formdev.flatlaf.FlatLightLaf;
import org.example.server.Server;
import org.example.ui.MainFrame;
import org.example.ui.TerminalApplication;
import org.example.util.LoggingMessage;

import javax.swing.*;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        LoggingMessage.printWelcomeText("WELCOME TO ROCKY ROC SERVER!");

        Server server = new Server();
        new TerminalApplication(server);

        //SwingUtilities.invokeLater(() -> new MainFrame(server));

    }
}