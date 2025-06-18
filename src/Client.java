import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client extends Thread{
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    Scanner scanner;

    public Client() {
        try{
            this.socket = new Socket("localhost", 1609);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.scanner = new Scanner(System.in);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // I want this method to read from the input socket and print it on screen
    // public readMessages() {
    @Override
    public void run() {
        try {
            String messageRecieved = bufferedReader.readLine();// this method is blocking call..the program just waits forever untill this process is completed so use thread
            System.out.println(messageRecieved);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessages() {
        String msgToSend = scanner.nextLine();
        try {
            bufferedWriter.write(msgToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        Thread thread = new Thread();
        thread.start();
        client.sendMessages();
    }
}
