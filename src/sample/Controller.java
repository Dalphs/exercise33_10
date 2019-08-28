package sample;

import javafx.scene.control.TextArea;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Controller implements ConToClient.ConToClientListener {

    public TextArea serverLog;

    public void initialize(){
        serverLog.setEditable(false);
        serverLog.appendText(new Date() + ": Server started");
        newConnection();
    }

    public void newConnection(){
        ConToClient con = new ConToClient(this);
        new Thread(con).start();
    }

    @Override
    public void connectionEstablished(String address) {
        serverLog.appendText(new Date() +" Connection established to: " + address);
    }
}
