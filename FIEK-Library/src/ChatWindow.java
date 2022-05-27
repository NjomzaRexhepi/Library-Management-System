
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatWindow extends Thread{

    static private TextField searchBox;
    static private Button sendMsgButton;
    static private TextArea msgBox;
    static private VBox centerVBox;
    static private Thread read;

    static public BufferedReader input;
    static public PrintWriter output;

    static private int port = 12345;
    static private Socket server;
    static private String host = "127.0.0.1";

    static{

        searchBox = new TextField();
        searchBox.setPromptText("Send message");
        searchBox.setPrefSize(400, 35);
        searchBox.setMinSize(400, 35);
        searchBox.setMaxSize(400, 35);

        sendMsgButton = new Button();
        sendMsgButton.setText("Send Message");
        sendMsgButton.setMinSize(100, 30);
        sendMsgButton.setPrefSize(120, 35);
        sendMsgButton.setMaxSize(140, 40);
        sendMsgButton.getStyleClass().add("btn-menu");
        sendMsgButton.setStyle(" -fx-font-size:16;");
        sendMsgButton.setOnAction(event -> {
            if (searchBox.getText().isEmpty()){
                return;
            }
            sendMessage(searchBox.getText());
            searchBox.setText("");
        });

        msgBox = new TextArea();
        msgBox.setPrefSize(400, 400);
        msgBox.setMinSize(300, 300);
        msgBox.setMaxSize(500, 500);
        msgBox.setEditable(false);

        centerVBox=new VBox();
        centerVBox.setPadding(new Insets(20, 20, 20, 20));
        centerVBox.maxHeight(100);
        centerVBox.getChildren().addAll(searchBox, sendMsgButton, msgBox);
        centerVBox.setSpacing(10);
    }
    private static void connect() throws IOException {
        server = new Socket(host, port);
        appendToPane(msgBox, "Connected to " + server.getRemoteSocketAddress());

        input = new BufferedReader(new InputStreamReader(server.getInputStream()));
        output = new PrintWriter(server.getOutputStream(), true);

        output.println(firstName);
        read = new Read();
        read.start();
    }

    private static void sendMessage(String msg){
        output.println(msg);
    }
    private static String firstName = "";

    public static  VBox getDisplay(String first_name) {
        firstName = first_name;
        msgBox.setText("");
        try {
            connect();
        }catch (IOException ioex){

        }
        return centerVBox;
    }

    public static void appendToPane(TextArea ta, String msg){
        if (msg.equals(firstName)) return;
        ta.appendText("\n" + msg);
    }

    public static void appendMSG(String msg){
        if (msg.equals(firstName)) return;
        msgBox.appendText("\n" + msg);
    }

}

class Read extends Thread {
    public void run() {
        String message;
        while(!Thread.currentThread().isInterrupted()){
            try {
                message = ChatWindow.input.readLine();
                if(message != null){
                    if (message.charAt(0) == '[') {
                        message = message.substring(1, message.length()-1);
                        ArrayList<String> ListUser = new ArrayList<String>(
                                Arrays.asList(message.split(", "))
                        );
                        for (String user : ListUser) {
                            ChatWindow.appendMSG(user);
                        }
                    }else{
                        ChatWindow.appendMSG(message);
                    }
                }
            }
            catch (IOException ex) {
                System.err.println("Failed to parse incoming message");
            }
        }
    }
}