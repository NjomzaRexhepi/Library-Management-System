import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;

public class MemberWindow {

    static private VBox centerVBox;
    static Connection myCon;
    static TableView<EmployeeTable> table;
    static ConnectionClass conn;
    static TableColumn<EmployeeTable, Integer> id;
    static TableColumn<EmployeeTable, String> firstName;
    static TableColumn<EmployeeTable, String> lastName;
    static TableColumn<EmployeeTable, String> email;
    static TableColumn<EmployeeTable, String> phoneNumber;
    static private TextField searchBox;
    static private Button searchBtn;
    static private Button refreshBtn;
    static private HBox search;
    static private ChoiceBox<String> searchOption;
    static private Label label;
    static private ComboBox<String> cat;
    static private HBox centerTop;
    static private HBox lowestCenter;
    static private Label rowSum;

    static{
        id = new TableColumn<>("ID");
        id.setPrefWidth(230);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName = new TableColumn<>("First Name");
        firstName.setPrefWidth(230);
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName = new TableColumn<>("Last Name");
        lastName.setPrefWidth(230);
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email = new TableColumn<>("email");
        email.setPrefWidth(230);
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumber = new TableColumn<>("Phone Number");
        phoneNumber.setPrefWidth(230);
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        searchBox = new TextField();
        searchBox.setPromptText("search books");
        searchBox.setPrefSize(400, 35);
        searchBox.setMinSize(400, 35);
        searchBox.setMaxSize(400, 35);

        searchBtn = new Button();
        searchBtn.setMinSize(40, 35);
        searchBtn.setPrefSize(40, 35);
        searchBtn.setMaxSize(40, 35);
        char[] searchSign= Character.toChars(128269);
        searchBtn.setText(String.valueOf(searchSign));
        searchBtn.getStyleClass().add("btn-menu");
        searchBtn.setStyle(" -fx-font-size:16;");

        refreshBtn = new Button();
        refreshBtn.setMinSize(40, 35);
        refreshBtn.setPrefSize(40, 35);
        refreshBtn.setMaxSize(40, 35);
        char[] refreshSign= Character.toChars(128472);
        refreshBtn.setText(String.valueOf(refreshSign));
        refreshBtn.getStyleClass().add("btn-menu");
        refreshBtn.setStyle(" -fx-font-size:16;");


        search = new HBox();
        search.getChildren().addAll(searchBox, searchBtn, refreshBtn);
        search.setAlignment(Pos.CENTER);

        label = new Label("Search By:");
        label.setMinSize(110, 35);
        label.setPrefSize(110, 35);
        label.setMaxSize(110, 35);
        label.getStyleClass().add("label-search");
        label.setAlignment(Pos.CENTER_RIGHT);

        cat = new ComboBox<String>();
        cat.setMinSize(150, 35);
        cat.setPrefSize(150, 35);
        cat.setMaxSize(150, 35);
        cat.getItems().addAll("ID", "First Name", "Last Name", "email");
        cat.setValue("ID");

        lowestCenter=new HBox();

        rowSum =new Label("[Row Selected: "+"]");
        rowSum.setStyle("-fx-font-style:italic;" +
                " -fx-text-fill: #D3D3D3;" +
                " -fx-font-size: 16;");
        lowestCenter.getChildren().add(rowSum);
        lowestCenter.setAlignment(Pos.CENTER_RIGHT);
        lowestCenter.setSpacing(10);

        table=null;
        conn=ConnectionClass.getInstance();
        try {
            table = new TableView<EmployeeTable>(conn.getMembers(searchBox.getText(), cat.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.getColumns().addAll(id, firstName, lastName, email, phoneNumber);
        table.setPrefSize(1150, 510);
        table.setMaxSize(1150, 510);

        searchBtn.setOnAction(event -> {
            table.getItems().clear();
            try {
                table.getItems().addAll(conn.getMembers(searchBox.getText(), cat.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rowSum.setText("[Row Selected: "+table.getItems().size()+"]");
        });

        refreshBtn.setOnAction(event -> {
            refresh();
        });

        centerTop = new HBox();
        centerTop.getChildren().addAll(search, label, cat);
        centerTop.setPrefSize(1150, 90);
        centerTop.setMaxSize(1150, 90);
        centerTop.setMinSize(1150, 90);
        centerTop.setSpacing(30);
        centerTop.setAlignment(Pos.CENTER);

        centerVBox=new VBox();
        centerVBox.setPadding(new Insets(20, 20, 20, 20));
        centerVBox.maxHeight(100);
        centerVBox.getChildren().addAll(centerTop, table,lowestCenter);
        centerVBox.setSpacing(10);

    }

    private static void refresh(){
        cat.setValue("ID");
        searchBox.clear();
        table.getItems().clear();
        try {
            table.getItems().addAll(conn.getMembers(searchBox.getText(), cat.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rowSum.setText("[Row Selected: "+table.getItems().size()+"]");
    }

    public static  VBox getBookWindow() {
        refresh();
        return centerVBox;
    }
}
