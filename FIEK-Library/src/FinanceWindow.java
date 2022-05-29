import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class FinanceWindow {

    static private VBox centerVBox;
    static private VBox centerVBoxMember;
    static private TableView<EmployeeFinanceTable> table;
    static private TableView<MemberFinanceTable> tableMember;
    static private ConnectionClass conn;
    static private TableColumn<EmployeeFinanceTable, String> transactionType;
    static private TableColumn<EmployeeFinanceTable, String> occasion;
    static private TableColumn<EmployeeFinanceTable, Integer> id;
    static private TableColumn<EmployeeFinanceTable, String> name;
    static private TableColumn<EmployeeFinanceTable, String> time;
    static private TableColumn<EmployeeFinanceTable, Date> date;
    static private TableColumn<EmployeeFinanceTable, Integer> amount;
    static private TableColumn<EmployeeFinanceTable, Integer> enteredBy;
    static private TextField searchBox;
    static private Button searchBtn;
    static private Button refreshBtn;
    static private HBox search;
    static private ComboBox<String> searchOption;
    static private HBox centerTop;
    static private HBox dateBox;
    static private HBox lowestCenter;
    static private TextField total;
    static private Label totalLabel;
    static private Label rowSum;
    static private TableColumn<MemberFinanceTable, String> transactionTypeMember;
    static private TableColumn<MemberFinanceTable, String> occasionMember;
    static private TableColumn<MemberFinanceTable, Integer> idMember;
    static private TableColumn<MemberFinanceTable, String> nameMember;
    static private TableColumn<MemberFinanceTable, String> timeMember;
    static private TableColumn<MemberFinanceTable, Date> dateMember;
    static private TableColumn<MemberFinanceTable, Integer> amountMember;
    static private TableColumn<MemberFinanceTable, Integer> enteredByMember;
    static private TextField searchBoxMember;
    static private Button searchBtnMember;
    static private Button refreshBtnMember;
    static private HBox searchMember;
    static private ComboBox<String> searchOptionMember;
    static private HBox centerTopMember;
    static private HBox dateBoxMember;
    static private HBox lowestCenterMember;
    static private TextField totalMember;
    static private Label totalLabelMember;
    static private Label rowSumMember;

    static{
        transactionType = new TableColumn<>("Transaction Type");
        transactionType.setPrefWidth(230);
        transactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        occasion = new TableColumn<>("Occasion");
        occasion.setPrefWidth(230);
        occasion.setCellValueFactory(new PropertyValueFactory<>("occasion"));

        id = new TableColumn<>(" ID");
        id.setPrefWidth(115);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        name = new TableColumn<>("Name");
        name.setPrefWidth(230);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        time = new TableColumn<>("Time");
        time.setPrefWidth(115);
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        date = new TableColumn<>("Date");
        date.setPrefWidth(115);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        amount = new TableColumn<>("Amount");
        amount.setPrefWidth(115);
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        enteredBy = new TableColumn<>("Entered By");
        enteredBy.setPrefWidth(115);
        enteredBy.setCellValueFactory(new PropertyValueFactory<>("enteredBy"));

        searchBox = new TextField();
        searchBox.setPromptText("search by ID");
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

        searchOption = new ComboBox<String>();
        searchOption.getItems().addAll("Salary", "Bonus","Medical Fee","All");
        searchOption.setValue("All");
        searchOption.setMinSize(150, 35);
        searchOption.setPrefSize(150, 35);
        searchOption.setMaxSize(150, 35);

        centerTop = new HBox();
        dateBox= getTime();
        centerTop.getChildren().addAll(search, searchOption, dateBox);
        centerTop.setPrefSize(1150, 90);
        centerTop.setMaxSize(1150, 90);
        centerTop.setSpacing(10);
        centerTop.setAlignment(Pos.CENTER_RIGHT);

        lowestCenter=new HBox();

        total=new TextField();
        total.setStyle("-fx-background-color: #D3D3D3;" +
                " -fx-font-weight:bold;");
        total.setAlignment(Pos.CENTER_RIGHT);
        total.setMinSize(230,25);
        total.setPrefSize(230,25);
        total.setMaxSize(230,25);

        totalLabel =new Label("Total");
        totalLabel.setStyle("-fx-font-style:italic;" +
                " -fx-text-fill: #D3D3D3;" +
                " -fx-font-size: 16;");

        rowSum =new Label("[Row Selected: "+"]");
        rowSum.setStyle("-fx-font-style:italic;" +
                " -fx-text-fill: #D3D3D3;" +
                " -fx-font-size: 16;");
        lowestCenter.getChildren().addAll(rowSum,totalLabel, total);
        lowestCenter.setAlignment(Pos.CENTER_RIGHT);
        lowestCenter.setSpacing(10);

        table=null;
        conn=ConnectionClass.getInstance();
        try {
            table = new TableView<EmployeeFinanceTable>(conn.getEmployeeFinanceHistory(searchBox.getText(), searchOption.getValue(),
                    ((ComboBox) dateBox.getChildren().get(2)).getValue()+"-"+
                    ((ComboBox) dateBox.getChildren().get(1)).getValue()+"-"+
                    ((ComboBox) dateBox.getChildren().get(0)).getValue(),
                    ((ComboBox) dateBox.getChildren().get(6)).getValue()+"-"+
                    ((ComboBox) dateBox.getChildren().get(5)).getValue()+"-"+
                    ((ComboBox) dateBox.getChildren().get(4)).getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchBtn.setOnAction(event -> {
            try {
                table.getItems().clear();
                table.getItems().addAll(conn.getEmployeeFinanceHistory(searchBox.getText(), searchOption.getValue(),
                        ((ComboBox) dateBox.getChildren().get(2)).getValue()+"-"+
                                ((ComboBox) dateBox.getChildren().get(1)).getValue()+"-"+
                                ((ComboBox) dateBox.getChildren().get(0)).getValue(),
                        ((ComboBox) dateBox.getChildren().get(6)).getValue()+"-"+
                                ((ComboBox) dateBox.getChildren().get(5)).getValue()+"-"+
                                ((ComboBox) dateBox.getChildren().get(4)).getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            rowSum.setText("[Row Selected: "+table.getItems().size()+"]");
            int sum=0;
            for(int c=0; c<table.getItems().size();c++){
                sum+=table.getItems().get(c).getAmount();
            }
            total.setText(""+sum);
            total.setAlignment(Pos.CENTER_RIGHT);
        });

        table.getColumns().addAll(id, name, date, time, transactionType, occasion, amount, enteredBy);
        table.setPrefSize(1150, 510);
        table.setMaxSize(1150, 510);

        centerVBox=new VBox();
        centerVBox.setPadding(new Insets(20, 20, 10, 20));
        centerVBox.maxHeight(100);
        centerVBox.getChildren().addAll(centerTop, table, lowestCenter);
        centerVBox.setSpacing(10);

        occasionMember = new TableColumn<>("Occasion");
        occasionMember.setPrefWidth(230);
        occasionMember.setCellValueFactory(new PropertyValueFactory<>("occasion"));

        idMember = new TableColumn<>(" ID");
        idMember.setPrefWidth(115);
        idMember.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameMember = new TableColumn<>("Name");
        nameMember.setPrefWidth(230);
        nameMember.setCellValueFactory(new PropertyValueFactory<>("name"));

        timeMember = new TableColumn<>("Time");
        timeMember.setPrefWidth(115);
        timeMember.setCellValueFactory(new PropertyValueFactory<>("time"));

        dateMember = new TableColumn<>("Date");
        dateMember.setPrefWidth(115);
        dateMember.setCellValueFactory(new PropertyValueFactory<>("date"));

        amountMember = new TableColumn<>("Amount");
        amountMember.setPrefWidth(115);
        amountMember.setCellValueFactory(new PropertyValueFactory<>("amount"));

        enteredByMember = new TableColumn<>("Entered By");
        enteredByMember.setPrefWidth(115);
        enteredByMember.setCellValueFactory(new PropertyValueFactory<>("enteredBy"));

        transactionTypeMember = new TableColumn<>("Transaction Type");
        transactionTypeMember.setPrefWidth(230);
        transactionTypeMember.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
