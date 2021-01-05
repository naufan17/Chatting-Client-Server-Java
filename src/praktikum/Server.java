package praktikum;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private final ArrayList<Socket> clientList;

    public Server() throws IOException {
        // get port
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Masukkan port : ");
        int port = keyboard.nextInt();

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
           e.getStackTrace();
        }

        clientList = new ArrayList<Socket>();

        startServer();
    }

    public void startServer() throws IOException {
        System.out.println("Menunggu Client...");
        while(true) {
            Socket client = serverSocket.accept();
            clientList.add(client);
            System.out.println("Client baru telah bergabung");
            ClientHandler handler = new ClientHandler(client,this);
            Thread t = new Thread(handler);
            t.start();
        }
    }

    public synchronized void broadcastMassage(String msg) throws IOException {
        for(Iterator<Socket> it = clientList.iterator(); it.hasNext();) {
            Socket client = it.next();
            if(!client.isClosed()) {
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                pw.println(msg);
                pw.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}