import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Finance {
    private static Finance obj=null;
    private static HBox fullBox=new HBox();
    private static VBox rightBox= new VBox();
    private static VBox leftBox= new VBox();
    private static int id, lateFee, lateDays, membershipFee, val;
    private static String emp;


    static {

            leftBox.setAlignment(Pos.CENTER);
            leftBox.setSpacing(30);
            Button setSalary=new Button("Set Salary");
            setSalary.setPrefSize(300, 55);
            setSalary.setMaxSize(300, 55);
            Button setMembershipFee=new Button("Set Membership Fee");
            setMembershipFee.setPrefSize(300, 55);
            setMembershipFee.setMaxSize(300, 55);
            Button setDelayFee=new Button("Set Delay Fee");
            setDelayFee.setPrefSize(300, 55);
            setDelayFee.setMaxSize(300, 55);
            leftBox.getChildren().addAll(setSalary, setMembershipFee, setDelayFee);


            rightBox.setAlignment(Pos.CENTER);
            rightBox.setSpacing(30);
            Button employeePayment=new Button("Employee Payment");
            employeePayment.setPrefSize(300, 55);
            employeePayment.setMaxSize(300, 55);
            Button receiveMembershipFee=new Button("Receive Membership Fee");
            receiveMembershipFee.setPrefSize(300, 55);
            receiveMembershipFee.setMaxSize(300, 55);
            Button receiveDelayFee=new Button("Receive Delay Fee");
            receiveDelayFee.setPrefSize(300, 55);
            receiveDelayFee.setMaxSize(300, 55);
            Button receiveMiscFee=new Button("Receive Misc. Fee");
            receiveMiscFee.setPrefSize(300, 55);
            receiveMiscFee.setMaxSize(300, 55);
            rightBox.getChildren().addAll(employeePayment,receiveMembershipFee,receiveDelayFee, receiveMiscFee);

        try {
            refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
         setSalary.setOnAction(event -> {
                TextField field=new TextField();
                field.setPromptText("Enter Employee ID");
                field.setPrefSize(300,55);
                field.setMaxSize(300,55);
                TextField salary=new TextField();
                salary.setPromptText("Enter Salary");
                salary.setPrefSize(300,55);
                salary.setMaxSize(300,55);

                Button confirm = new Button("Confirm");
                confirm.setPrefSize(120, 25);
                confirm.setMaxSize(120, 25);
                Button back = new Button("Back");
                back.setPrefSize(120, 25);
                back.setMaxSize(120, 25);
                HBox hbox=new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(20);
                hbox.getChildren().addAll(back, confirm);

                VBox setSalaryBox= new VBox();
                setSalaryBox.setAlignment(Pos.CENTER);
                setSalaryBox.getChildren().addAll(field, salary, hbox);
                setSalaryBox.setSpacing(30);
                setSalaryBox.setPrefWidth(330);
                setSalaryBox.setMinWidth(330);

                fullBox.getChildren().clear();
                fullBox.getChildren().add(setSalaryBox);

                back.setOnAction(event1 -> {
                    fullBox.getChildren().clear();
                    fullBox.getChildren().addAll(leftBox, rightBox);
                    fullBox.setAlignment(Pos.CENTER);
                    fullBox.setSpacing(100);
                });
                confirm.setOnAction(event1 -> {
                    try {
                        ConnectionClass con = ConnectionClass.getInstance();
                        con.setSalary(field.getText(), salary.getText());
                        field.setStyle("-fx-background-color: #98FB98");
                        salary.setStyle("-fx-background-color: #98FB98");
                    }catch(SQLException e){
                        field.setStyle("-fx-background-color: #FFB6C1");
                        salary.setStyle("-fx-background-color: #FFB6C1");
                    }

                });
            });
        
setMembershipFee.setOnAction(event -> {

            ConnectionClass con=ConnectionClass.getInstance();
            String []vals=new String[4];
            try {
                vals=con.getMisc("Membership Fee");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Label current= new Label("Current Status:");
            current.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            Label fee= new Label();
            fee.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            fee.setText("Membership Fee: "+vals[0]);
            Label date= new Label();
            date.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            date.setText("Last updated on: "+vals[3]);
            Label updateID= new Label();
            updateID.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            updateID.setText("Last updated by: "+vals[2]);
            TextField membershipFee=new TextField();
            membershipFee.setPromptText("Enter Membership Fee per Month");
            membershipFee.setPrefSize(300,55);
            membershipFee.setMaxSize(300,55);

            Button confirm = new Button("Confirm");
            confirm.setPrefSize(120, 25);
            confirm.setMaxSize(120, 25);
            Button back = new Button("Back");
            back.setPrefSize(120, 25);
            back.setMaxSize(120, 25);
            HBox hbox=new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(20);
            hbox.getChildren().addAll(back, confirm);

            VBox setSalaryBox= new VBox();
            setSalaryBox.setAlignment(Pos.CENTER);
            setSalaryBox.getChildren().addAll(current, fee, date, updateID, membershipFee, hbox);
            setSalaryBox.setSpacing(30);
            setSalaryBox.setPrefWidth(330);
            setSalaryBox.setMinWidth(330);

            fullBox.getChildren().clear();
            fullBox.getChildren().add(setSalaryBox);

            back.setOnAction(event1 -> {
                fullBox.getChildren().clear();
                fullBox.getChildren().addAll(leftBox, rightBox);
                fullBox.setAlignment(Pos.CENTER);
                fullBox.setSpacing(100);
            });
            confirm.setOnAction(event1 -> {
                try {
                    con.setMisc("Membership Fee", membershipFee.getText(), "null", id);
                    membershipFee.setStyle("-fx-background-color: #98FB98");
                } catch (SQLException e) {
                    e.printStackTrace();
                    membershipFee.setStyle("-fx-background-color: #FFB6C1");
                }
            });
            });
