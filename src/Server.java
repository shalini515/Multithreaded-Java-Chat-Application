import java.io.*;
import java.net.*;

public class Server {
    ServerSocket serverSocket;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(1609);
        this.start();
    }

    public void start() throws IOException{
        while(true) {
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}