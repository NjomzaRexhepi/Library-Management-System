import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Registration {

    private static Registration obj=null;

    private Registration(){

    }
    public static Registration getInstance(){
        if(obj==null){
            obj= new Registration();
        }
        return obj;
    }

    public void addBooks(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        boolean confirmed=false;
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("Confirm");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField title = new TextField();
        title.setPrefSize(300, 55);
        title.setPromptText("Title");
        title.setFocusTraversable(false);
        TextField author = new TextField();
        author.setPrefSize(300, 55);
        author.setPromptText("Author");
        author.setFocusTraversable(false);
        TextField category = new TextField();
        category.setPrefSize(300, 55);
        category.setPromptText("Category");
        category.setFocusTraversable(false);
        TextField shelfID = new TextField();
        shelfID.setPrefSize(300, 55);
        shelfID.setPromptText("Shelf-ID");
        shelfID.setFocusTraversable(false);
        TextField amount = new TextField();
        amount.setPrefSize(300, 55);
        amount.setPromptText("Amount");
        amount.setFocusTraversable(false);

        Label lbl= new Label("Adding Book");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);

        Button done = new Button("Done");
        done.setPrefSize(220, 35);
        done.setMaxSize(220, 35);
        done.setOnAction(event -> {
            arg0.close();
        });


        grid.getChildren().addAll(lbl,title,author, category, shelfID,amount,btn1);


        GridPane.setConstraints(lbl, 0, 4,2,1);
        GridPane.setConstraints(title, 0, 5,2,1);
        GridPane.setConstraints(author, 0, 6,2,1);
        GridPane.setConstraints(category, 0, 7,2,1);
        GridPane.setConstraints(shelfID, 0, 8,2,1);
        GridPane.setConstraints(amount, 0, 9,2,1);
        GridPane.setConstraints(btn1, 0, 10,1,1);
        grid.autosize();

        btn1.setOnAction(event -> {
            if(!confirmed) {
                ConnectionClass myCon = ConnectionClass.getInstance();
                try {
                    myCon.addBook(title.getText(), author.getText(), category.getText(), shelfID.getText(), amount.getText());
                    title.setEditable(false);
                    author.setEditable(false);
                    category.setEditable(false);
                    shelfID.setEditable(false);
                    amount.setEditable(false);
                    btn1.setDisable(true);
                    grid.getChildren().add(done);
                    GridPane.setConstraints(done, 1, 10, 1, 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }});


        Scene sc = new Scene(grid, 500, 600);


        sc.getStylesheets().add("style.css");
        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }

    public void addMember(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("Confirm");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField firstName = new TextField();
        firstName.setPrefSize(300, 55);
        firstName.setPromptText("First Name");
        firstName.setFocusTraversable(false);
        TextField lastName = new TextField();
        lastName.setPrefSize(300, 55);
        lastName.setPromptText("LastName");
        lastName.setFocusTraversable(false);
        TextField email = new TextField();
        email.setPrefSize(300, 55);
        email.setPromptText("email Address");
        email.setFocusTraversable(false);
        TextField pass = new TextField();
        pass.setPrefSize(300, 55);
        pass.setPromptText("Password");
        pass.setFocusTraversable(false);
        TextField phone = new TextField();
        TextField confirmPass = new TextField();
        confirmPass.setPrefSize(300, 55);
        confirmPass.setPromptText("Confirm Password");
        confirmPass.setFocusTraversable(false);
        phone.setPrefSize(300, 55);
        phone.setPromptText("Phone Number");
        phone.setFocusTraversable(false);

        Label lbl= new Label("AddingMember");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);


        grid.getChildren().addAll(lbl,firstName,lastName, email, pass,confirmPass,phone,btn1);


        GridPane.setConstraints(lbl, 0, 3,2,1);
        GridPane.setConstraints(firstName, 0, 4,2,1);
        GridPane.setConstraints(lastName, 0, 5,2,1);
        GridPane.setConstraints(email, 0, 6,2,1);
        GridPane.setConstraints(pass, 0, 7,2,1);
        GridPane.setConstraints(confirmPass, 0, 8,2,1);
        GridPane.setConstraints(phone, 0, 9,2,1);
        GridPane.setConstraints(btn1, 0, 10,1,1);
        grid.autosize();

        VBox box=new VBox();
        Label idLabel= new Label();
        idLabel.setStyle("-fx-font-size: 18;" +
                " -fx-text-fill: green;");
        Button done = new Button("Done");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);
        box.getChildren().addAll(idLabel, done);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        Scene scene=new Scene(box,500,600);


        btn1.setOnAction(event -> {
            if(pass.getText().equals(confirmPass.getText())){
          ConnectionClass mycon=  ConnectionClass.getInstance();
            try {
               int newID= mycon.addMember(firstName.getText(),lastName.getText(),email.getText(),pass.getText(),phone.getText());
               idLabel.setText("New Member ID: "+newID);
               arg0.setScene(scene);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            }else{
                pass.setText("");
                pass.setStyle("-fx-background-color:#FFB6C1");
                confirmPass.setText("");
                confirmPass.setStyle("-fx-background-color:#FFB6C1");
            }

        });

        done.setOnAction(event -> {arg0.close();});

        Scene sc = new Scene(grid, 500, 600);


        sc.getStylesheets().add("style.css");
        scene.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }

    public void addEmployee(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("Confirm");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField firstName = new TextField();
        firstName.setPrefSize(300, 55);
        firstName.setPromptText("First Name");
        firstName.setFocusTraversable(false);
        TextField lastName = new TextField();
        lastName.setPrefSize(300, 55);
        lastName.setPromptText("LastName");
        lastName.setFocusTraversable(false);
        TextField email = new TextField();
        email.setPrefSize(300, 55);
        email.setPromptText("email Address");
        email.setFocusTraversable(false);
        TextField pass = new TextField();
        pass.setPrefSize(300, 55);
        pass.setPromptText("Password");
        pass.setFocusTraversable(false);
        TextField confirmPass = new TextField();
        confirmPass.setPrefSize(300, 55);
        confirmPass.setPromptText("Confirm Password");
        confirmPass.setFocusTraversable(false);
        TextField phone = new TextField();
        phone.setPrefSize(300, 55);
        phone.setPromptText("Phone Number");
        phone.setFocusTraversable(false);
        TextField salary = new TextField();
        salary.setPrefSize(300, 55);
        salary.setPromptText("Salary");
        salary.setFocusTraversable(false);

        Label lbl= new Label("Adding Employee");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);


        grid.getChildren().addAll(lbl,firstName,lastName, email, pass,confirmPass,phone,salary,btn1);


        GridPane.setConstraints(lbl, 0, 2,2,1);
        GridPane.setConstraints(firstName, 0, 3,2,1);
        GridPane.setConstraints(lastName, 0, 4,2,1);
        GridPane.setConstraints(email, 0, 5,2,1);
        GridPane.setConstraints(pass, 0, 6,2,1);
        GridPane.setConstraints(confirmPass, 0, 7,2,1);
        GridPane.setConstraints(phone, 0, 8,2,1);
        GridPane.setConstraints(salary, 0, 9,2,1);
        GridPane.setConstraints(btn1, 0, 10,1,1);
        grid.autosize();

        VBox box=new VBox();
        Label idLabel= new Label();
        idLabel.setStyle("-fx-font-size: 18;" +
                " -fx-text-fill: green;");
        Button done = new Button("Done");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);
        box.getChildren().addAll(idLabel, done);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        Scene scene=new Scene(box,500,600);


        btn1.setOnAction(event -> {
            if(pass.getText().equals(confirmPass.getText())){
                ConnectionClass mycon=  ConnectionClass.getInstance();
                try {
                    int newID= mycon.addEmployee(firstName.getText(),lastName.getText(),email.getText(),pass.getText(),phone.getText(),salary.getText());
                    idLabel.setText("New Employee ID: "+newID);
                    arg0.setScene(scene);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                pass.setText("");
                pass.setStyle("-fx-background-color:#FFB6C1");
                confirmPass.setText("");
                confirmPass.setStyle("-fx-background-color:#FFB6C1");
            }

        });

        done.setOnAction(event -> {arg0.close();});

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");
        scene.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }

    public void addAdmin(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("OK");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField empID = new TextField();
        empID.setPrefSize(300, 55);
        empID.setPromptText("Employee ID");
        empID.setFocusTraversable(false);

        Label lbl= new Label("Adding Admin");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(lbl,empID,btn1);

        GridPane.setConstraints(lbl, 0, 2,2,1);
        GridPane.setConstraints(empID, 0, 3,2,1);

        GridPane.setConstraints(btn1, 0, 5,1,1);
        grid.autosize();

        btn1.setOnAction(event -> {
                    ConnectionClass mycon = ConnectionClass.getInstance();
                    try {
                        mycon.addAdmin(empID.getText());
                        arg0.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }

    public void remAdmin(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("OK");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField empID = new TextField();
        empID.setPrefSize(300, 55);
        empID.setPromptText("Employee ID");
        empID.setFocusTraversable(false);

        Label lbl= new Label("Removing Admin");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(lbl,empID,btn1);

        GridPane.setConstraints(lbl, 0, 2,2,1);
        GridPane.setConstraints(empID, 0, 3,2,1);

        GridPane.setConstraints(btn1, 0, 5,1,1);
        grid.autosize();

        btn1.setOnAction(event -> {
            ConnectionClass mycon = ConnectionClass.getInstance();
            try {
                mycon.remAdmin(empID.getText(),"libraryadmin");
                arg0.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }

    public void remBooks(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("OK");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField title = new TextField();
        title.setPrefSize(300, 55);
        title.setPromptText("Title");
        title.setFocusTraversable(false);

        TextField author = new TextField();
        author.setPrefSize(300, 55);
        author.setPromptText("Author");
        author.setFocusTraversable(false);

        Label lbl= new Label("Removing Books");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(lbl,title,author,btn1);

        GridPane.setConstraints(lbl, 0, 2,2,1);
        GridPane.setConstraints(title, 0, 3,2,1);
        GridPane.setConstraints(author, 0, 4,2,1);

        GridPane.setConstraints(btn1, 0, 5,1,1);
        grid.autosize();

        btn1.setOnAction(event -> {
            ConnectionClass mycon = ConnectionClass.getInstance();
            try {
                mycon.remBooks(title.getText(),author.getText());
                arg0.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }
    public void remEmployee(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("OK");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField empID = new TextField();
        empID.setPrefSize(300, 55);
        empID.setPromptText("Employee ID");
        empID.setFocusTraversable(false);

        Label lbl= new Label("Removing Employee");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(lbl,empID,btn1);

        GridPane.setConstraints(lbl, 0, 2,2,1);
        GridPane.setConstraints(empID, 0, 3,2,1);

        GridPane.setConstraints(btn1, 0, 5,1,1);
        grid.autosize();

        btn1.setOnAction(event -> {
            ConnectionClass mycon = ConnectionClass.getInstance();
            try {
                mycon.remAdmin(empID.getText(),"librarystuff");
                arg0.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }
    public void remMember(int id, String password) {
        Stage arg0=new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 35));
        grid.setVgap(20);
        grid.setHgap(30);

        Button btn1 = new Button("OK");
        btn1.setPrefSize(220, 35);
        btn1.setMaxSize(220, 35);

        TextField empID = new TextField();
        empID.setPrefSize(300, 55);
        empID.setPromptText("Member ID");

        Label lbl= new Label("Removing Member");
        lbl.setPrefSize(430,55);
        lbl.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(lbl,empID,btn1);

        GridPane.setConstraints(lbl, 0, 2,2,1);
        GridPane.setConstraints(empID, 0, 3,2,1);

        GridPane.setConstraints(btn1, 0, 5,1,1);
        grid.autosize();

        btn1.setOnAction(event -> {
            ConnectionClass mycon = ConnectionClass.getInstance();
            try {
                mycon.remAdmin(empID.getText(),"librarymember");
                arg0.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Scene sc = new Scene(grid, 500, 600);

        sc.getStylesheets().add("style.css");

        arg0.initModality(Modality.APPLICATION_MODAL);
        arg0.setScene(sc);
        arg0.show();
    }
}
