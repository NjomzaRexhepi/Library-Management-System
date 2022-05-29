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
