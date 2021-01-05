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
        // get hostname
        keyboard = new Scanner(System.in);
        System.out.print("Masukkan hostname : ");
        hostname = keyboard.next();

        // get port
        System.out.print("Masukkan port : ");
        port = keyboard.nextInt();

        // get username
        System.out.print("Masukkan Username : ");
        username = keyboard.next();

        initialize();
    }

    private void initialize() throws IOException {
        // connect to host
        InetAddress host = null;
        try {
            host = InetAddress.getByName(hostname);
        } catch (UnknownHostException ex) {
            System.out.println("Host tidak ditemukan");
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

        inputStream = new Scanner(link.getInputStream());
        outputStream = new PrintWriter(link.getOutputStream());

        // start new thread
        Thread t = new Thread(this);
        t.start();

        // continuously listen your user input
        while (keyboard.hasNextLine()) {
            String msg = keyboard.nextLine();
            outputStream.println(username + " : " + msg);
            outputStream.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        new Client();
    }

    public void run() {
        while (true) {
            if (inputStream.hasNextLine())
                System.out.println(inputStream.nextLine());
        }
    }
}
