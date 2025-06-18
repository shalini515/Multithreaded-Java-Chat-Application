import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private static ArrayList<ClientHandler> clientHandler = new ArrayList<>();
    private String userName;

    public ClientHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        clientHandler.add(this);
    }

    public void getUserName() throws IOException{
        bufferedWriter.write("Please enter your user name to chat with the group: ");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        String userName = bufferedReader.readLine();
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            getUserName();
            while(socket.isConnected()) {
                String msgFromClient = bufferedReader.readLine();
                broadcastMessage(msgFromClient); // if i do broadcast message here it wont be multi threaded right??? No
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String msg) throws IOException {
        for(ClientHandler client : clientHandler) {
            if(!client.userName.equals(this.userName)) {
                client.bufferedWriter.write(msg);
                client.bufferedWriter.newLine();
                client.bufferedWriter.flush();
            }
        }
    }

}
