import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class User {
    private static int nbUser = 0;
    private int userId;
    private PrintStream streamOut;
    private InputStream streamIn;
    private String nickname;
    private Socket client;
    private String color;

    public User(){}

    public User(String name) {
        this.nickname=name;
    }
    // constructor
    public User(Socket client, String name) throws IOException {
        this.streamOut = new PrintStream(client.getOutputStream());
        this.streamIn = client.getInputStream();
        this.client = client;
        this.nickname = name;
        this.userId = nbUser;
        this.color = "";
        nbUser += 1;
    }

    public PrintStream getOutStream(){
        return this.streamOut;
    }

    public InputStream getInputStream(){
        return this.streamIn;
    }

    public String getNickname(){
        return this.nickname;
    }

    public String toString(){
        return this.nickname;
    }
}
