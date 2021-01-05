package praktikum;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final Server server;

    public ClientHandler(Socket client, Server server) {
        this.client = client;
        this.server = server;
    }

    public void run() {
        try {
            Scanner inputStream = new Scanner(client.getInputStream());
            while(true) {
                if(!inputStream.hasNext()) {
                    return;
                }
                String chatLine = inputStream.nextLine();
                System.out.println(chatLine);
                server.broadcastMassage(chatLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}