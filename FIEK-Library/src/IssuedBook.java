
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.Calendar;

public class IssuedBook {

    static private VBox centerVBox;
    static private TableView<IssueHistory> table;
    static private ConnectionClass conn;
    static private TableColumn<IssueHistory, String> title;
    static private TableColumn<IssueHistory, String> issued;
    static private TableColumn<IssueHistory, Date> issuedDate;
    static private TableColumn<IssueHistory, String> issuedTime;
    static private TableColumn<IssueHistory, String> returned;
    static private TableColumn<IssueHistory, Date> returnDate;
    static private TableColumn<IssueHistory, String> returnTime;
    static private TableColumn<IssueHistory, String> author;
    static private TableColumn<IssueHistory, Integer> receivedBy;
    static private TableColumn<IssueHistory, Integer> receivedByID;
    static private TableColumn<IssueHistory, String> receivedByName;
    static private TableColumn<IssueHistory, String> issuedBy;
    static private TableColumn<IssueHistory, Integer> issuedByID;
    static private TableColumn<IssueHistory, String> issuedByName;
    static private TableColumn<IssueHistory, String> issuedTo;
    static private TableColumn<IssueHistory, Integer> issuedToID;
    static private TableColumn<IssueHistory, String> issuedToName;
    static private TableColumn<IssueHistory, Integer> issueID;
    static private TextField searchBox;
    static private Button searchBtn;
    static private Button refreshBtn;
    static private HBox search;
    static private ComboBox<String> searchOption;
    static private HBox centerTop;

    static{
        title = new TableColumn<>("Title");
        title.setPrefWidth(230);
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author = new TableColumn<>("Author");
        author.setPrefWidth(230);
        author.setCellValueFactory(new PropertyValueFactory<>("author"));

        issued = new TableColumn<>("Issued");
        issuedDate = new TableColumn<>("Date");
        issuedTime = new TableColumn<>("Time");
        issuedDate.setPrefWidth(115);
        issuedTime.setPrefWidth(115);
        issuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        issuedTime.setCellValueFactory(new PropertyValueFactory<>("issuedTime"));
        issued.getColumns().add(issuedDate);
        issued.getColumns().add(issuedTime);

        returned = new TableColumn<>("Returned");
        returned.setPrefWidth(230);
        returnDate = new TableColumn<>("Date");
        returnTime = new TableColumn<>("Time");
        returnDate.setPrefWidth(115);
        returnTime.setPrefWidth(115);
        returnDate.setCellValueFactory(new PropertyValueFactory<>("retDate"));
        returnTime.setCellValueFactory(new PropertyValueFactory<>("retTime"));
        returned.getColumns().add(returnDate);
        returned.getColumns().add(returnTime);

        receivedBy = new TableColumn<>("Received By");
        receivedBy.setPrefWidth(230);
        receivedByID = new TableColumn<>("ID");
        receivedByName = new TableColumn<>("Name");
        receivedByID.setPrefWidth(115);
        receivedByName.setPrefWidth(115);
        receivedByID.setCellValueFactory(new PropertyValueFactory<>("receiverID"));
        receivedByName.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        receivedBy.getColumns().add(receivedByID);
        receivedBy.getColumns().add(receivedByName);

        issuedBy = new TableColumn<>("Issued By");
        issuedBy.setPrefWidth(230);
        issuedByID = new TableColumn<>("ID");
        issuedByName = new TableColumn<>("Name");
        issuedByID.setPrefWidth(115);
        issuedByName.setPrefWidth(115);
        issuedByID.setCellValueFactory(new PropertyValueFactory<>("issuedByID"));
        issuedByName.setCellValueFactory(new PropertyValueFactory<>("issuedByName"));
        issuedBy.getColumns().add(issuedByID);
        issuedBy.getColumns().add(issuedByName);

        issuedTo = new TableColumn<>("Issued To");
        issuedTo.setPrefWidth(230);
        issuedToID = new TableColumn<>("ID");
        issuedToName = new TableColumn<>("Name");
        issuedToID.setPrefWidth(115);
        issuedToName.setPrefWidth(115);
        issuedToID.setCellValueFactory(new PropertyValueFactory<>("issuedToID"));
        issuedToName.setCellValueFactory(new PropertyValueFactory<>("issuedToName"));
        issuedTo.getColumns().add(issuedToID);
        issuedTo.getColumns().add(issuedToName);

        issueID = new TableColumn<>("Issue ID");
        issueID.setPrefWidth(115);
        issueID.setCellValueFactory(new PropertyValueFactory<>("issuedID"));

        searchBox = new TextField();
        searchBox.setPromptText("search by Book Title");
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
        searchOption.setValue("Title");
        searchOption.setMinSize(150, 35);
        searchOption.setPrefSize(150, 35);
        searchOption.setMaxSize(150, 35);
        searchOption.getItems().addAll("Title", "Author", "Issued by", "Issued To", "Received by");

        searchOption.setOnAction(event -> {
            if(!searchOption.getItems().isEmpty()) {
                switch (searchOption.getValue()) {
                    case "Title":
                        searchBox.setPromptText("search by Book Title");
                        break;
                    case "Author":
                        searchBox.setPromptText("search by Book Author");
                        break;
                    case "Issued by":
                        searchBox.setPromptText("search by Book issued by ID");
                        break;
                    case "Issued to":
                        searchBox.setPromptText("search by Book issued to ID");
                        break;
                    case "Received by":
                        searchBox.setPromptText("search by Book Received by ID");
                        break;
                }
            }
        });

        table=null;
        conn=ConnectionClass.getInstance();
        table = new TableView<IssueHistory>();
        table.getColumns().addAll(title, author,issuedBy,issuedTo, issued, returned, receivedBy, issueID);
        table.setPrefSize(1150, 510);
        table.setMaxSize(1150, 510);

        centerTop = new HBox();
        centerTop.setPrefSize(1150, 90);
        centerTop.setMaxSize(1150, 90);
        centerTop.setSpacing(10);
        centerTop.setAlignment(Pos.CENTER_RIGHT);

        centerVBox=new VBox();
        centerVBox.setPadding(new Insets(20, 20, 20, 20));
        centerVBox.maxHeight(100);
        centerVBox.getChildren().addAll(centerTop, table);
        centerVBox.setSpacing(10);


    }

    private static void refresh(String ability, int id){

        searchBox.clear();
        searchOption.getItems().clear();
        if(ability.equals("Member")) {
            searchOption.getItems().addAll("Title", "Author", "Issued By", "Received By");
        }else{
            searchOption.getItems().addAll("Title", "Author", "Issued By", "Issued To", "Received By");
        }
        searchOption.setValue("Title");

        Label label = new Label("to");
        label.setMinSize(20, 35);
        label.setPrefSize(20, 35);
        label.setMaxSize(20, 35);
        label.getStyleClass().add("label-search");
        label.setAlignment(Pos.CENTER);

        ComboBox<String> day=new ComboBox<String>();
        ComboBox<String> month=new ComboBox<String>();
        ComboBox<String> year=new ComboBox<String>();

        day.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","Any");
        month.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","Any");
        year.getItems().addAll("1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","Any");
        day.setPrefSize(80,25);
        day.setMaxSize(80,25);
        month.setPrefSize(80,25);
        month.setMaxSize(80,25);
        year.setPrefSize(80,25);
        year.setMaxSize(80,25);
        day.getStyleClass().add("combo-box-edited");
        month.getStyleClass().add("combo-box-edited");
        year.getStyleClass().add("combo-box-edited");
        day.setValue("01");
        month.setValue("01");
        year.setValue("1995");

        ComboBox<String> day2=new ComboBox<String>();
        ComboBox<String> month2=new ComboBox<String>();
        ComboBox<String> year2=new ComboBox<String>();

        day2.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","Any");
        month2.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","Any");
        year2.getItems().addAll("1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","Any");
        day2.setPrefSize(80,25);
        day2.setMaxSize(80,25);
        month2.setPrefSize(80,25);
        month2.setMaxSize(80,25);
        year2.setPrefSize(80,25);
        year2.setMaxSize(80,25);
        day2.getStyleClass().add("combo-box-edited");
        month2.getStyleClass().add("combo-box-edited");
        year2.getStyleClass().add("combo-box-edited");

        HBox dateBox=new HBox();
        dateBox.setSpacing(2);
        dateBox.setMinSize(480,35);
        dateBox.setPrefSize(480,35);
        dateBox.setMaxSize(480,35);
        dateBox.setAlignment(Pos.CENTER_RIGHT);
        dateBox.setStyle("-fx-border-color: linear-gradient(#7FFF00,#32CD32)");

        Calendar cal = Calendar.getInstance();
        day2.setValue(cal.get(Calendar.DAY_OF_MONTH)<10? ("0"+cal.get(Calendar.DAY_OF_MONTH)):""+cal.get(Calendar.DAY_OF_MONTH));
        month2.setValue((cal.get(Calendar.MONTH)+1)<10? ("0"+(cal.get(Calendar.MONTH)+1)):""+(cal.get(Calendar.MONTH)+1));
        year2.setValue(""+cal.get(Calendar.YEAR));

        dateBox.getChildren().addAll(day,month,year,label,day2,month2,year2);

        try {
            table.getItems().clear();
            table.getItems().addAll(conn.getIssuedHistory(searchBox.getText(),""+year.getValue()+"-"+month.getValue()+"-"+day.getValue(),
                                         ""+year2.getValue()+"-"+month2.getValue()+"-"+day2.getValue(), searchOption.getValue(), ability, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        searchBtn.setOnAction(event -> {
            table.getItems().clear();
            try {
                table.getItems().addAll(conn.getIssuedHistory(searchBox.getText(),""+year.getValue()+"-"+month.getValue()+"-"+day.getValue(),
                        ""+year2.getValue()+"-"+month2.getValue()+"-"+day2.getValue(), searchOption.getValue(), ability, id));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        refreshBtn.setOnAction(event -> refresh(ability, id));

        centerTop.getChildren().clear();
        centerTop.getChildren().addAll(search, searchOption, dateBox);




    }
    public static  VBox getIssueWindow(String s, int id){
        refresh(s, id);
        return centerVBox;
    }

}
