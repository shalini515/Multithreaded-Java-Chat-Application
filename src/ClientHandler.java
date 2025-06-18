import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private static HashMap<Socket, String> clientList;

    ClientHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void getUserName() throws IOException{
        bufferedWriter.write("Please enter your user name to chat with the group");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        String userName = bufferedReader.readLine();
        clientList.put(this.socket, userName);
    }

    @Override
    public void run() {
        try {
            String msgFromClient = bufferedReader.readLine();
            broadcastMessage(msgFromClient); // if i do broadcast message here it wont be multi threaded right???
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcastMessage(String msg) throws IOException {
        for(Socket socket : clientList.keySet()) {
            if(socket != this.socket) {
                bufferedWriter.write(clientList.get(this.socket) + ": " + msg);
            }
        }
    }

}
