
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class AddRemoveWindow {

    static private VBox centerVBox;
    static Connection myCon;
    static TableView<AddRemBookHistory> table;
    static ConnectionClass conn;
    static private TableColumn<AddRemBookHistory, String> title;
    static private TableColumn<AddRemBookHistory, String> author;
    static private TableColumn<AddRemBookHistory, String> actedBy;
    static private TableColumn<AddRemBookHistory, Date> date;
    static private TableColumn<AddRemBookHistory, String> time;
    static private TableColumn<AddRemBookHistory, String> action;
    static private TableColumn<AddRemBookHistory, Integer> actedByID;
    static private TableColumn<AddRemBookHistory, String> actedByName;
    static private TableColumn<AddRemBookHistory, Integer> copies;
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

        date = new TableColumn<>("Date");
        date.setPrefWidth(230);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        time = new TableColumn<>("Time");
        time.setPrefWidth(230);
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        action = new TableColumn<>("Action");
        action.setPrefWidth(230);
        action.setCellValueFactory(new PropertyValueFactory<>("act"));

        actedBy = new TableColumn<>("Acted By");
        actedBy.setPrefWidth(230);
        actedByID = new TableColumn<>("ID");
        actedByName = new TableColumn<>("Name");
        actedByID.setPrefWidth(115);
        actedByName.setPrefWidth(115);
        actedByID.setCellValueFactory(new PropertyValueFactory<>("addedByID"));
        actedByName.setCellValueFactory(new PropertyValueFactory<>("addedByName"));
        actedBy.getColumns().add(actedByID);
        actedBy.getColumns().add(actedByName);

        copies = new TableColumn<>("Copies");
        copies.setPrefWidth(230);
        copies.setCellValueFactory(new PropertyValueFactory<>("amount"));

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
        searchOption.getItems().addAll("Title", "Author","Acted by");
        searchOption.setValue("Title");
        searchOption.setMinSize(150, 35);
        searchOption.setPrefSize(150, 35);
        searchOption.setMaxSize(150, 35);
        searchOption.setOnAction(event -> {
            switch(searchOption.getValue()){
                case "Title":
                    searchBox.setPromptText("search by Book Title");
                    break;
                case "Author":
                    searchBox.setPromptText("search by Book Author");
                    break;
                case "Acted by":
                    searchBox.setPromptText("search by Book acted by ID");
                    break;
            }
        });
  refreshBtn.setOnAction(event -> refresh());

        table=null;
        conn=ConnectionClass.getInstance();
        table = new TableView<AddRemBookHistory>();
        table.getColumns().addAll(title, author,date,time, action, actedBy,copies);
        table.setPrefSize(1150, 510);
        table.setMaxSize(1150, 510);

        centerTop = new HBox();
        centerTop.getChildren().addAll(search, searchOption);
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
    private static void refresh(){

        searchBox.clear();
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
        table.getItems().clear();
        try {
            table.getItems().addAll(conn.getAddRemBookHistory(searchBox.getText(),""+year.getValue()+"-"+month.getValue()+"-"+day.getValue(),
                    ""+year2.getValue()+"-"+month2.getValue()+"-"+day2.getValue(),searchOption.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        searchBtn.setOnAction(event -> {
            try {

                table.getItems().clear();
                table.getItems().addAll(conn.getAddRemBookHistory(searchBox.getText(),""+year.getValue()+"-"+month.getValue()+"-"+day.getValue(),
                        ""+year2.getValue()+"-"+month2.getValue()+"-"+day2.getValue(),searchOption.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        centerTop.getChildren().clear();
        centerTop.getChildren().addAll(search, searchOption, dateBox);


    }

    public static  VBox getAddRemBookWindow() {
        refresh();
        return centerVBox;
    }

}
