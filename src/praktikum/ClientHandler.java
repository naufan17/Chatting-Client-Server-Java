package praktikum;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final Server server;
    private final Scanner inputStream;
    private final Vector<String> users;
    private final String name;

    public ClientHandler(Socket client, Server server) throws IOException {
        this.client = client;
        this.server = server;

        inputStream = new Scanner(client.getInputStream());
        users = new Vector<>();
        name = inputStream.nextLine();
        // add username client to vector
        users.add(name);
        System.out.println(users + " Telah bergabung dalam chat");
    }

    public void run() {
        try {
            while(true) {
                if(!inputStream.hasNext()) {
                    return;
                }
                // read & show massage client
                String chatLine = inputStream.nextLine();
                System.out.println(chatLine);
                // broadcast massage client
                server.broadcastMassage(chatLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}