import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Member;
import java.sql.SQLException;

public class Issue {
    private static Issue obj=null;
    private Issue(){

    }
    public static Issue getInstance(){
        if(obj==null){
            obj=new Issue();
        }
        return obj;
    }
    public VBox getIssueWindow(int ID, String password){

        TextField title = new TextField();
        title.setPrefSize(300, 55);
        title.setMaxSize(300, 55);
        title.setPromptText("Title");
        title.setFocusTraversable(false);
        TextField author = new TextField();
        author.setPrefSize(300, 55);
        author.setMaxSize(300, 55);
        author.setPromptText("Author");
        author.setFocusTraversable(false);
        TextField shelfID = new TextField();
        shelfID.setPrefSize(300, 55);
        shelfID.setMaxSize(300, 55);
        shelfID.setPromptText("Shelf-ID");
        shelfID.setFocusTraversable(false);
        TextField memberID = new TextField();
        TextField amount = new TextField();
        amount.setPrefSize(300, 55);
        amount.setMaxSize(300, 55);
        amount.setPromptText("Amount");
        amount.setFocusTraversable(false);
        memberID.setPrefSize(300, 55);
        memberID.setMaxSize(300, 55);
        memberID.setPromptText("Member ID");
        memberID.setFocusTraversable(false);

        Button confirm = new Button("Confirm");
        confirm.setPrefSize(120, 25);
        confirm.setMaxSize(120, 25);
        Button clear = new Button("Clear");
        clear.setPrefSize(120, 25);
        clear.setMaxSize(120, 25);
        HBox hbox=new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(clear, confirm);

        VBox issueBox= new VBox();
        issueBox.setAlignment(Pos.CENTER);
        issueBox.getChildren().addAll(title, author, shelfID, amount, memberID, hbox);
        issueBox.setSpacing(30);
        issueBox.setPrefWidth(330);
        issueBox.setMinWidth(330);
        
        clear.setOnAction(event -> {
            title.clear();
            author.clear();
            shelfID.clear();
            amount.clear();
            memberID.clear();
        });
    
}
