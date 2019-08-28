package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConToServer implements Runnable {

    private int port = 8000;
    private String host = "localhost";
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ConToServerListener listener;

    public interface ConToServerListener{
        void messageReceived(String sender, String text);
    }

    public ConToServer(ConToServerListener listener) {
        this.listener = listener;
        try {
            socket = new Socket(host, port);
            System.out.println("Forbindelse til server oprettet");
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("O outputstream oprettet");
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("IO strams oprettet");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Message msg = (Message) in.readObject();
                listener.messageReceived(msg.getSender(), msg.getText());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String sender, String text){
        Message msg = new Message(sender, text);
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
