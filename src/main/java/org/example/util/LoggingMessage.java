package org.example.util;

public class LoggingMessage {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String THUMBS_UP = "\uD83D\uDC4D";
    public static final String THUMBS_DOWN = "\uD83D\uDC4E";
    public static final String THINKING_FACE = "\uD83E\uDD14";
    public static final String COLLISION_SYMBOL = "\uD83D\uDCA5";
    public static final String POINT_DOWN = "\uD83D\uDC47";
    public static final String ROCKET = "\uD83D\uDE80";
    public static final String FAILED_CONNECTION_CROSS = "\u274C";
    public static final String CLOSED_LOCK_WITH_KEY = "\uD83D\uDD10";
    public static final String UNLOCKED_PADLOCK = "\uD83D\uDD13";
    public static final String RED_CIRCLE = "\uD83D\uDD34";
    public static final String GREEN_CIRCLE = "\uD83D\uDFE2";
    public static final String CHEER_BEER = "\uD83C\uDF7B";
    public static final String ENVELOP = "\u2709";
    public static final String CLOSED_MAILBOX = "\uD83D\uDCEB";
    public static final String PACKAGE = "\uD83D\uDCE6";


    public static void printWelcomeText(String s){
        System.out.println("*****************************************************************");
        System.out.println(ANSI_GREEN+s+ANSI_RESET + " \uD83D\uDE00");
        System.out.println("*****************************************************************");


        for(int i = 0; i < 31; i++){
            CipherUtils.wait(50);
            System.out.print(COLLISION_SYMBOL);
        }
        System.out.println("\n\n");

    }


    public static void printColoredText(String txt, String color){
        System.out.println(color+"[--] "+txt.toUpperCase()+ANSI_RESET);
    }

    public static void printInStream(String txt){
        System.out.println(ANSI_GREEN+"[--] " + GREEN_CIRCLE + " " + txt.toUpperCase()+ANSI_RESET);
    }
    public static void printInStream(String txt, String icon){
        System.out.println(ANSI_GREEN+"[--] " + GREEN_CIRCLE + " " + txt.toUpperCase()+ANSI_RESET + " " +icon);
    }
    public static void printOutStream(String txt){
        System.out.println(ANSI_GREEN+"[--] " + RED_CIRCLE + " " + txt.toUpperCase()+ANSI_RESET);
    }
    public static void printOutStream(String txt, String icon){
        System.out.println(ANSI_GREEN+"[--] " + RED_CIRCLE + " " + txt.toUpperCase()+ANSI_RESET + " " +icon);
    }
    public static void printSucceededMessage(String s){
        System.out.println(ANSI_GREEN+"[--] "+s.toUpperCase()+ANSI_RESET + " " +THUMBS_UP);
    }
    public static void printFailedMessage(String s){
        System.out.println("[--] "+ANSI_RED+s.toUpperCase()+ANSI_RESET + " " +THUMBS_DOWN);
    }
    public static void printFailedConnect(String s){
        System.out.println(ANSI_RED+"[--] "+s.toUpperCase()+ANSI_RESET + " " + FAILED_CONNECTION_CROSS);
    }
    public static void printProgressiveAction(String s) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(ANSI_BLUE+"[--PROGRESS--] "+s.toUpperCase()+ " " + ANSI_RESET + THINKING_FACE + " " + THINKING_FACE);
        Thread.sleep(1000);
    }
    public static void printProgress(String msg, String icon){
        System.out.println("\n"+ msg.toUpperCase());
        for (int i = 0; i < 31; i++){
            CipherUtils.wait(50);
            System.out.print(icon);
        }
        System.out.println("\n");
    }
}

