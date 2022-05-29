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
        
            setDelayFee.setOnAction(event -> {

            ConnectionClass con=ConnectionClass.getInstance();
            String []vals=new String[4];
            try {
                vals=con.getMisc("Delay Fee");
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
            Label time= new Label();
            time.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            time.setText("Allowed Time: "+vals[1]);
            Label date= new Label();
            date.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            date.setText("Last updated on: "+vals[3]);
            Label updateID= new Label();
            updateID.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            updateID.setText("Last updated by: "+vals[2]);


            TextField delayFee=new TextField();
            delayFee.setPromptText("Enter Delay Fee per Day");
            delayFee.setPrefSize(300,55);
            delayFee.setMaxSize(300,55);
            TextField allowedDays=new TextField();
            allowedDays.setPromptText("Allowed Days for Borrowing");
            allowedDays.setPrefSize(300,55);
            allowedDays.setMaxSize(300,55);

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

            VBox setDelayBox= new VBox();
            setDelayBox.setAlignment(Pos.CENTER);
            setDelayBox.getChildren().addAll(current, fee, time, date, updateID, delayFee, allowedDays, hbox);
            setDelayBox.setSpacing(30);
            setDelayBox.setPrefWidth(330);
            setDelayBox.setMinWidth(330);

            fullBox.getChildren().clear();
            fullBox.getChildren().add(setDelayBox);

            back.setOnAction(event1 -> {
                fullBox.getChildren().clear();
                fullBox.getChildren().addAll(leftBox, rightBox);
                fullBox.setAlignment(Pos.CENTER);
                fullBox.setSpacing(100);
            });
            confirm.setOnAction(event1 -> {
                try {
                    con.setMisc("Delay Fee", delayFee.getText(), allowedDays.getText(), id);
                    delayFee.setStyle("-fx-background-color: #98FB98");
                    allowedDays.setStyle("-fx-background-color: #98FB98");
                } catch (SQLException e) {
                    e.printStackTrace();
                    delayFee.setStyle("-fx-background-color: #FFB6C1");
                    allowedDays.setStyle("-fx-background-color: #FFB6C1");
                }
            });
            });
        
         employeePayment.setOnAction(event -> {
                TextField employeeID=new TextField();
                employeeID.setPromptText("Enter Employee ID");
                employeeID.setPrefSize(300,55);
                employeeID.setMaxSize(300,55);
                ComboBox<String> type= new ComboBox<String>();
                type.setPrefSize(300,55);
                type.setMaxSize(300,55);
                type.getItems().addAll("Salary", "Bonus", "Medical Fee");
                type.setValue("Salary");
                TextField occasion=new TextField();
                occasion.setPromptText("Month");
                occasion.setPrefSize(300,55);
                occasion.setMaxSize(300,55);
                TextField amount=new TextField();
                amount.setPromptText("Amount (Only For Medical Fee)");
                amount.setPrefSize(300,55);
                amount.setMaxSize(300,55);
                amount.setEditable(false);
                type.setOnAction(event1 -> {
                    switch (type.getValue()){
                        case "Salary":
                            occasion.setPromptText("Month");
                            amount.setEditable(false);
                            amount.clear();
                            break;
                        case "Bonus":
                            occasion.setPromptText("Festive");
                            amount.setEditable(false);
                            amount.clear();
                            break;
                        case "Medical Fee":
                            occasion.setPromptText("Issue");
                            amount.setEditable(true);
                            break;
                    }
                });


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

                VBox employeePaymentBox= new VBox();
                employeePaymentBox.setAlignment(Pos.CENTER);
                employeePaymentBox.getChildren().addAll(employeeID, type, occasion,amount, hbox);
                employeePaymentBox.setSpacing(30);
                employeePaymentBox.setPrefWidth(330);
                employeePaymentBox.setMinWidth(330);

                fullBox.getChildren().clear();
                fullBox.getChildren().add(employeePaymentBox);

                back.setOnAction(event1 -> {
                    fullBox.getChildren().clear();
                    if(emp.equals("Admin")) {
                        fullBox.getChildren().addAll(leftBox, rightBox);
                    }else{
                        fullBox.getChildren().addAll(rightBox);
                    }
                    fullBox.setAlignment(Pos.CENTER);
                    fullBox.setSpacing(100);
                });
                confirm.setOnAction(event1 -> {
                ConnectionClass con=ConnectionClass.getInstance();
                    try {
                        con.employeePayment(employeeID.getText(),type.getValue(), occasion.getText(), id, amount.getText());
                        employeeID.setStyle("-fx-background-color: #98FB98");
                        occasion.setStyle("-fx-background-color: #98FB98");
                        if(type.getValue().equals("Medical Fee")){
                            amount.setStyle("-fx-background-color: #98FB98");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        employeeID.setStyle("-fx-background-color: #FFB6C1");
                        occasion.setStyle("-fx-background-color: #FFB6C1");
                        if(type.getValue().equals("Medical Fee")){
                            amount.setStyle("-fx-background-color: #FFB6C1");

                        }
                    }
                });

            });

            receiveMembershipFee.setOnAction(event -> {
                Label label= new Label();
                label.setText("Membership Fee: "+ membershipFee);
                label.setStyle("-fx-font-size: 14;" +
                        " -fx-text-fill: green;");

                TextField memberID=new TextField();
                memberID.setPromptText("Enter Member ID");
                memberID.setPrefSize(300,55);
                memberID.setMaxSize(300,55);
                TextField month=new TextField();
                month.setPromptText("Enter Month");
                month.setPrefSize(300,55);
                month.setMaxSize(300,55);

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

                VBox membershipReceive= new VBox();
                membershipReceive.setAlignment(Pos.CENTER);
                membershipReceive.getChildren().addAll(label, memberID, month, hbox);
                membershipReceive.setSpacing(30);
                membershipReceive.setPrefWidth(330);
                membershipReceive.setMinWidth(330);

                fullBox.getChildren().clear();
                fullBox.getChildren().add(membershipReceive);

                back.setOnAction(event1 -> {
                    fullBox.getChildren().clear();
                    if(emp.equals("Admin")) {
                        fullBox.getChildren().addAll(leftBox, rightBox);
                    }else{
                        fullBox.getChildren().addAll(rightBox);
                    }
                    fullBox.setAlignment(Pos.CENTER);
                    fullBox.setSpacing(100);
                });

                confirm.setOnAction(event1 -> {
                    ConnectionClass con=ConnectionClass.getInstance();
                    try {
                        con.memberPayment(memberID.getText(), "Membership Fee", month.getText(), id, ""+membershipFee);
                        memberID.setStyle("-fx-background-color: #98FB98");
                        month.setStyle("-fx-background-color: #98FB98");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        memberID.setStyle("-fx-background-color: #FFB6C1");
                        month.setStyle("-fx-background-color: #FFB6C1");
                    }
                });

            });
          receiveDelayFee.setOnAction(event -> {
                Label label= new Label();
                label.setText("After "+lateDays+" days, Late fee per Day: "+ lateFee);
                label.setStyle("-fx-font-size: 14;" +
                        " -fx-text-fill: green;");

                TextField memberID=new TextField();
                memberID.setPromptText("Enter Member ID");
                memberID.setPrefSize(300,55);
                memberID.setMaxSize(300,55);
                TextField issueID=new TextField();
                issueID.setPromptText("Enter Issue ID");
                issueID.setPrefSize(300,55);
                issueID.setMaxSize(300,55);

                Button confirm = new Button("Check");
                confirm.setPrefSize(120, 25);
                confirm.setMaxSize(120, 25);
                Button back = new Button("Back");
                back.setPrefSize(120, 25);
                back.setMaxSize(120, 25);
                HBox hbox=new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(20);
                hbox.getChildren().addAll(back, confirm);

                VBox membershipReceive= new VBox();
                membershipReceive.setAlignment(Pos.CENTER);
                membershipReceive.getChildren().addAll(label, memberID, issueID, hbox);
                membershipReceive.setSpacing(30);
                membershipReceive.setPrefWidth(330);
                membershipReceive.setMinWidth(330);

                fullBox.getChildren().clear();
                fullBox.getChildren().add(membershipReceive);


                back.setOnAction(event1 -> {
                    if(confirm.getText().equals("Check")) {
                        fullBox.getChildren().clear();
                        if (emp.equals("Admin")) {
                            fullBox.getChildren().addAll(leftBox, rightBox);
                        } else {
                            fullBox.getChildren().addAll(rightBox);
                        }
                        fullBox.setAlignment(Pos.CENTER);
                        fullBox.setSpacing(100);
                    }else{
                        memberID.clear();
                        issueID.clear();
                        label.setText("After "+lateDays+" days, Late fee per Day: "+ lateFee);
                        confirm.setText("Check");
                        membershipReceive.getChildren().clear();
                        membershipReceive.getChildren().addAll(label, memberID, issueID, hbox);
                    }
                });

                confirm.setOnAction(event1 -> {
                    ConnectionClass con = ConnectionClass.getInstance();

                    if(confirm.getText().equals("Check")) {
                        try {
                            int over=0;
                            over = (con.getDelayedAmount(issueID.getText()) - lateDays) * lateFee;
                            label.setText("Amount: " + over);
                            membershipReceive.getChildren().clear();
                            membershipReceive.getChildren().addAll(label, hbox);
                            confirm.setText("Confirm");
                            setVal(over);
                        } catch (SQLException e) {
                            e.printStackTrace();

                        }
                    }else{
                        try {
                            con.memberPayment(memberID.getText(), "Delay Fee","Issue ID: "+ issueID.getText(), id, "" + val);
                            memberID.setStyle("-fx-background-color: #98FB98");
                            issueID.setStyle("-fx-background-color: #98FB98");
                            memberID.clear();
                            issueID.clear();
                            label.setText("After "+lateDays+" days, Late fee per Day: "+ lateFee);
                            confirm.setText("Check");
                            membershipReceive.getChildren().clear();
                            membershipReceive.getChildren().addAll(label, memberID, issueID, hbox);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

            });

        receiveMiscFee.setOnAction(event1 -> {
            TextField miscMemberID = new TextField();
            miscMemberID.setPromptText("Enter Member ID");
            miscMemberID.setPrefSize(300, 55);
            miscMemberID.setMaxSize(300, 55);
            TextField occasion = new TextField();
            occasion.setPromptText("Enter Purpose");
            occasion.setPrefSize(300, 55);
            occasion.setMaxSize(300, 55);
            TextField amount = new TextField();
            amount.setPromptText("Enter Amount");
            amount.setPrefSize(300, 55);
            amount.setMaxSize(300, 55);


            Button confirm = new Button("Confirm");
            confirm.setPrefSize(120, 25);
            confirm.setMaxSize(120, 25);
            Button back = new Button("Back");
            back.setPrefSize(120, 25);
            back.setMaxSize(120, 25);
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(20);
            hbox.getChildren().addAll(back, confirm);

            VBox employeePaymentBox = new VBox();
            employeePaymentBox.setAlignment(Pos.CENTER);
            employeePaymentBox.getChildren().addAll(miscMemberID, occasion, amount, hbox);
            employeePaymentBox.setSpacing(30);
            employeePaymentBox.setPrefWidth(330);
            employeePaymentBox.setMinWidth(330);

            fullBox.getChildren().clear();
            fullBox.getChildren().add(employeePaymentBox);

            back.setOnAction(event -> {
                fullBox.getChildren().clear();
                if (emp.equals("Admin")) {
                    fullBox.getChildren().addAll(leftBox, rightBox);
                } else {
                    fullBox.getChildren().addAll(rightBox);
                }
                fullBox.setAlignment(Pos.CENTER);
                fullBox.setSpacing(100);
            });
            confirm.setOnAction(event -> {
                ConnectionClass con = ConnectionClass.getInstance();
                try {
                    con.memberPayment(miscMemberID.getText(), "Misc Fee", occasion.getText(), id,  amount.getText());
                    miscMemberID.setStyle("-fx-background-color: #98FB98");
                    occasion.setStyle("-fx-background-color: #98FB98");
                    amount.setStyle("-fx-background-color: #98FB98");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            });

        });

    }
    
      private Finance(){


    }
    public static Finance getInstance(){
        if(obj==null){
            obj=new Finance();
        }
        return obj;
    }
    private static void setVal(int value){
        val=value;
    }
    public static void refresh() throws SQLException {
        ConnectionClass con=ConnectionClass.getInstance();
        int [] vals = con.getamounts();
        membershipFee = vals[0];
        lateFee = vals[1];
        lateDays = vals[2];
    }
    public HBox getBox(String ability, int Employee){
        this.id= Employee;
        emp=ability;
        if(ability.equals("Admin")){
            fullBox.getChildren().clear();
            fullBox.getChildren().addAll(leftBox, rightBox);
            fullBox.setAlignment(Pos.CENTER);
            fullBox.setSpacing(100);
        }else{
            fullBox.getChildren().clear();
            fullBox.getChildren().addAll(rightBox);
            fullBox.setAlignment(Pos.CENTER);
            fullBox.setSpacing(100);
        }
        return fullBox;
    }


}

