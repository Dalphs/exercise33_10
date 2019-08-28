package sample;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientController implements ConToServer.ConToServerListener {
    public TextArea clientLog;
    public TextField senderTextField;
    public TextField messageTextField;
    private ConToServer con;

    public void initialize(){
        clientLog.setEditable(false);
        messageTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    con.sendMessage(senderTextField.getText(), messageTextField.getText());
                    messageTextField.clear();
                }
            }
        });
        con = new ConToServer(this);
        new Thread(con).start();
    }

    @Override
    public void messageReceived(String sender, String text) {
        clientLog.appendText(sender + ": " + text + "\n");
    }
}
