import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client extends Thread{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    private Scanner scanner = new Scanner(System.in);

    public Client() {
        try{
            this.socket = new Socket("localhost", 1609);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
            while(socket.isConnected()) {
                String messageRecieved = bufferedReader.readLine();// this method is blocking call..the program just waits forever untill this process is completed so use thread
                System.out.println(messageRecieved);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void userNamePrompt(){
        try {
            String prompt = bufferedReader.readLine();
            System.out.println(prompt);
            String userName = scanner.nextLine();
            this.userName = userName;
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessages() {
        try {
            userNamePrompt();
            while(socket.isConnected()) {
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(userName + ": " + msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        client.sendMessages();
    }
}
