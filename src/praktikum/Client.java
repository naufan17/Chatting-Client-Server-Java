package praktikum;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable {
    private PrintWriter outputStream;
    private Scanner inputStream;
    private final Scanner keyboard;
    private final int port;
    private final String username;
    private final String hostname;

    public Client() throws IOException {
        keyboard = new Scanner(System.in);

        // input hostname
        System.out.print("Masukkan hostname : ");
        hostname = keyboard.next();

        // input port
        System.out.print("Masukkan port : ");
        port = keyboard.nextInt();

        // input username
        System.out.print("Masukkan Username : ");
        username = keyboard.next();
    }

    private void initialize() throws IOException {
        // connect to host
        InetAddress host = null;
        try {
            host = InetAddress.getByName(hostname);
        } catch (UnknownHostException ex) {
            System.out.println("Host tidak ditemukan");
            ex.printStackTrace();
        }

        // connect to server
        Socket link = null;
        try {
            link = new Socket(host, port);
            link.setReuseAddress(true);
        } catch (IOException ex) {
            System.out.println("Server tidak ditemukan");
            ex.printStackTrace();
        }
        System.out.println("Anda telah terhubung");

        // variable input & output
        inputStream = new Scanner(link.getInputStream());
        outputStream = new PrintWriter(link.getOutputStream());
        // send username to server
        outputStream.println(username);

        Chat();
    }

    private void Chat(){
        // start new thread massage
        Thread t = new Thread(this);
        t.start();

        // input client massage & send to server
        while (keyboard.hasNextLine()) {
            String msg = keyboard.nextLine();
            outputStream.println(username + " : " + msg);
            outputStream.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        new Client().initialize();
    }

    public void run() {
        // read & show broadcast massage
        while (true) {
            if (inputStream.hasNextLine())
                System.out.println(inputStream.nextLine());
        }
    }
}
