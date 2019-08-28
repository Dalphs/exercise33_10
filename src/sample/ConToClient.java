package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConToClient implements Runnable, ChatClient.ChatClientListener{

    private int port = 8000;
    private ServerSocket serverSocket;
    private ConToClientListener listener;
    private ArrayList<ChatClient> chatClients = new ArrayList<>();

    @Override
    public void newMessage(Message msg) {
        System.out.println("Message: " + msg.getSender() + ": " + msg.getText());
        for (ChatClient cc: chatClients) {
            cc.sendMessage(msg);
        }
    }

    public interface ConToClientListener{
        void connectionEstablished(String address);
    }

    public ConToClient(ConToClientListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Venter på forbindelse");
                Socket socket = serverSocket.accept();
                System.out.println("Forbindelse oprettet");
                listener.connectionEstablished(socket.getInetAddress().getHostAddress());
                ChatClient chatClient = new ChatClient(socket, this);
                chatClients.add(chatClient);
                new Thread(chatClient).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
