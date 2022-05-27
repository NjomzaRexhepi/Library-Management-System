import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class Admin {

    public void start(Stage arg0,Scene prevSc, String s) {

        GridPane grid = new GridPane();
        Button logIn = new Button("Log in");
        Button back = new Button("Back");
        Button forgotPassword = new Button("Forgot password!");
        TextField email = new TextField();
        PasswordField password = new PasswordField();
        Label welcome= new Label();
        Button close =new Button();
        char sign=10005;
        char minsign=9866;
        Button minimize=new Button(""+minsign);
        Label empty= new Label();                                                      //used to create empty space
        HBox hb= new HBox();
        Label wrong=new Label();
        VBox wrongBox=new VBox();

        grid.setPadding(new Insets(0, 0, 20, 20));
        grid.setVgap(20);
        grid.setHgap(30);

        logIn.setPrefSize(220, 35);
        logIn.setMaxSize(220, 35);

        back.setPrefSize(220, 35);
        back.setMaxSize(220, 35);
        back.setOnAction(event -> arg0.setScene(prevSc));

        forgotPassword.setMinSize(150, 30);
        forgotPassword.setPrefSize(150, 30);
        forgotPassword.getStyleClass().add("buttonForget");
        forgotPassword.setAlignment(Pos.CENTER_RIGHT);

        email.setPrefSize(300, 55);
        email.setPromptText("Enter email address");
        email.setFocusTraversable(false);

        password.setPrefSize(300, 55);
        password.setPromptText("Password");
        password.setFocusTraversable(false);

        welcome.setPrefSize(460,55);
        welcome.setMinSize(460,55);
        welcome.setAlignment(Pos.CENTER);

        empty.setMinSize(50,35);
        switch (s){
            case "Admin":
                welcome.setText("SmartShelf - Admin Portal");
                break;
            case "Employee":
                welcome.setText("SmartShelf - Employee Portal");
                welcome.setStyle(" -fx-font-size: 30");
                break;
            case "Member":
                welcome.setText("SmartShelf - Member Portal");
                break;
        }

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
        hb.setAlignment(Pos.CENTER_RIGHT);

        wrong.setText("Wrong email address or password");
        wrong.setStyle(" -fx-text-fill: white;" +
                " -fx-font-size: 16;");
        wrongBox.getChildren().add(wrong);
        wrongBox.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(hb, welcome, email, password, logIn, forgotPassword, empty, back, wrongBox);

        GridPane.setConstraints(hb, 2, 0,2,1);
        GridPane.setConstraints(welcome, 0, 5,4,1);
        GridPane.setConstraints(email, 0, 6,3,1);
        GridPane.setConstraints(password, 0, 7,3,1);
        GridPane.setConstraints(logIn, 0, 8,1,1);
        GridPane.setConstraints(forgotPassword, 2, 8,1,1);
        GridPane.setConstraints(empty, 1, 8,1,1);
        GridPane.setConstraints(back, 0, 9,1,1);
        GridPane.setConstraints(wrongBox, 0, 10,4,1);
        grid.autosize();

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");
        logIn.setOnAction(event -> {
            ConnectionClass conn = ConnectionClass.getInstance();
            try {
                AdminWindow a;
                switch (s) {
                    case "Admin":
                        if ((a = conn.adminCheck(email.getText(), password.getText())) != null) {
                            arg0.close();
                            a.start(arg0, sc);
                            email.clear();
                            password.clear();
                    }else{
                            wrong.setStyle(" -fx-text-fill: lightPink;" +
                                    "  -fx-font-size: 16;");
                            password.clear();
                        }
                        break;
                    case "Employee":
                        if ((a = conn.employeeCheck(email.getText(), password.getText())) != null) {
                            arg0.close();
                            a.start(arg0, sc);
                            email.clear();
                            password.clear();
                        }else{
                            wrong.setStyle(" -fx-text-fill: lightPink;" +
                                    "  -fx-font-size: 16;");
                            password.clear();
                        }
                        break;
                    case "Member":
                        if ((a = conn.memberCheck(email.getText(), password.getText())) != null) {
                            arg0.close();
                            a.start(arg0, sc);
                            email.clear();
                            password.clear();
                        }else{
                            wrong.setStyle(" -fx-text-fill: lightPink;" +
                                    "  -fx-font-size: 16;");
                            password.clear();
                        }
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        password.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case ENTER:
                    ConnectionClass conn = ConnectionClass.getInstance();
                    try {
                        AdminWindow a;
                        switch (s) {
                            case "Admin":
                                if ((a = conn.adminCheck(email.getText(), password.getText())) != null) {
                                    arg0.close();
                                    a.start(arg0, sc);
                                    email.clear();
                                    password.clear();
                                }else{
                                    wrong.setStyle(" -fx-text-fill: lightPink;" +
                                            "  -fx-font-size: 16;");
                                    password.clear();
                                }
                                break;
                            case "Employee":
                                if ((a = conn.employeeCheck(email.getText(), password.getText())) != null) {
                                    arg0.close();
                                    a.start(arg0, sc);
                                    email.clear();
                                    password.clear();
                                }else{
                                    wrong.setStyle(" -fx-text-fill: lightPink;" +
                                            "  -fx-font-size: 16;");
                                    password.clear();
                                }
                                break;
                            case "Member":
                                if ((a = conn.memberCheck(email.getText(), password.getText())) != null) {
                                    arg0.close();
                                    a.start(arg0, sc);
                                    email.clear();
                                    password.clear();
                                }else{
                                    wrong.setStyle(" -fx-text-fill: lightPink;" +
                                            "  -fx-font-size: 16;");
                                    password.clear();
                                }
                                break;
                        }
                        } catch (SQLException e) {
                        e.printStackTrace();
                    }

            }
        });
        email.setOnMouseClicked(event -> wrong.setStyle(" -fx-text-fill: white;"));
        password.setOnMouseClicked(event -> wrong.setStyle(" -fx-text-fill: white;"));
        close.setOnAction(event -> arg0.close());
        minimize.setOnAction(event -> arg0.setIconified(true));
        password.setOnAction(event -> {
            Stage alarm=new Stage();
            Label doom= new Label();
            char[] ch= Character.toChars(128541);
            doom.setText("Oops...You are Doomed! "+String.valueOf(ch));
            Button okey=new Button("You have to be okey");
            VBox vBox=new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(doom,okey);
            Scene dooming=new Scene(vBox,430,200);
            dooming.getStylesheets().add("style.css");
            alarm.setScene(dooming);
            alarm.initStyle(StageStyle.UNDECORATED);
            alarm.initModality(Modality.APPLICATION_MODAL);
            okey.setOnAction(event1 -> alarm.close());
            alarm.show();
        });

        arg0.setScene(sc);
        arg0.show();
    }
}
