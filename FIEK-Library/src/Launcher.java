import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage arg0) {
        GridPane grid = new GridPane();
        Button admin = new Button("Admin");
        Button libraryStuff = new Button("Library Stuff");
        Button member = new Button("Member");
        Label welcome= new Label("FIEK Library");
        Button close =new Button();
        char sign=10005;
        char minsign=9866;
        char db=(char)Integer.parseInt("26C3",16);
        Button minimize=new Button(""+minsign);
        HBox hb= new HBox();
        Scene sc = new Scene(grid, 500, 600);
        Button dbBtn= new Button();

        grid.setPadding(new Insets(0, 0, 20, 20));
        grid.setVgap(20);
        grid.setHgap(30);

        admin.setPrefSize(300,55);
        libraryStuff.setPrefSize(300,55);
        member.setPrefSize(300,55);
        welcome.setPrefSize(400,55);
        welcome.setMinSize(400,55);

        close.setText(""+sign);
        close.setMinSize(30,20);
        close.setPrefSize(30,20);
        close.setMaxSize(30,20);
        minimize.setMinSize(30,20);
        minimize.setPrefSize(30,20);
        minimize.setMaxSize(30,20);
        minimize.getStyleClass().add("buttonClose");
        close.getStyleClass().add("buttonClose");
        minimize.getStyleClass().add("buttonMinimize");

        hb.getChildren().addAll(minimize,close);
        hb.setAlignment(Pos.CENTER_LEFT);

        grid.getChildren().addAll(hb,welcome,admin, libraryStuff, member);
        GridPane.setConstraints(hb,4,0,2,1);
        GridPane.setConstraints(welcome,2,5,3,1);
        GridPane.setConstraints(admin, 3, 7);
        GridPane.setConstraints(libraryStuff, 3, 8);
        GridPane.setConstraints(member, 3, 9);

        grid.autosize();

        sc.getStylesheets().add("style.css");
        admin.setOnAction(event -> {
            Admin a=new Admin();
            a.start(arg0,sc,"Admin");});
        libraryStuff.setOnAction(event -> {
            Admin a=new Admin();
            a.start(arg0,sc,"Employee");});
        member.setOnAction(event -> {
            Admin a=new Admin();
            a.start(arg0,sc, "Member");});

        close.setOnAction(event -> arg0.close());
        minimize.setOnAction(event -> arg0.setIconified(true));

        arg0.initStyle(StageStyle.UNDECORATED);
        arg0.setScene(sc);
        arg0.show();



    }

}

