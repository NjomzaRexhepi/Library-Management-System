import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;

public class BooksWindow {

    static private VBox centerVBox;
    static private Connection myCon;
    static private TableView<Books> table;
    static private ConnectionClass conn;
    static private TableColumn<Books, String> title;
    static private TableColumn<Books, String> author;
    static private TableColumn<Books, String> category;
    static private TableColumn<Books, String> shelfID;
    static private TableColumn<Books, Integer> available;
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
    static private Label booksum;

    static{
        title = new TableColumn<>("Title");
        title.setPrefWidth(230);
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author = new TableColumn<>("Author");
        author.setPrefWidth(230);
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category = new TableColumn<>("Category");
        category.setPrefWidth(230);
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        shelfID = new TableColumn<>("Shelf-ID");
        shelfID.setPrefWidth(230);
        shelfID.setCellValueFactory(new PropertyValueFactory<>("shelfID"));
        available = new TableColumn<>("Available");
        available.setPrefWidth(230);
        available.setCellValueFactory(new PropertyValueFactory<>("available"));

        table=null;
        conn=ConnectionClass.getInstance();
        try {
            table = new TableView<Books>(conn.getAllBooks("All"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.getColumns().addAll(title, author, category, shelfID, available);
        table.setPrefSize(1150, 510);
        table.setMaxSize(1150, 510);

        table.setRowFactory(tab -> new TableRow<Books>() {
            @Override
            public void updateItem(Books item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getAvailable() == 0) {
                    setStyle("-fx-background-color: linear-gradient(white,red);");
                } else {
                    setStyle("");
                }
            }
        });

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

        searchOption = new ChoiceBox<String>();
        searchOption.getItems().addAll("Search by Title", "Search By Author");
        searchOption.setMinSize(150, 35);
        searchOption.setPrefSize(150, 35);
        searchOption.setMaxSize(150, 35);


        label = new Label("Category:");
        label.setMinSize(110, 35);
        label.setPrefSize(110, 35);
        label.setMaxSize(110, 35);
        label.getStyleClass().add("label-search");
        label.setAlignment(Pos.CENTER_RIGHT);

        lowestCenter=new HBox();

        rowSum =new Label("[Row Selected: "+"]");
        rowSum.setStyle("-fx-font-style:italic;" +
                " -fx-text-fill: #D3D3D3;" +
                " -fx-font-size: 16;");
        booksum =new Label("[Total Book: "+"]");
        booksum.setStyle("-fx-font-style:italic;" +
                " -fx-text-fill: #D3D3D3;" +
                " -fx-font-size: 16;");
        lowestCenter.getChildren().addAll(rowSum, booksum);
        lowestCenter.setAlignment(Pos.CENTER_RIGHT);
        lowestCenter.setSpacing(10);

        cat = new ComboBox<String>();
        cat.setMinSize(150, 35);
        cat.setPrefSize(150, 35);
        cat.setMaxSize(150, 35);
        try {
            for (String s:conn.getBooksCategory()){
                cat.getItems().add(s);
            }
            cat.getItems().add("All");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cat.setOnAction(event -> {
            table.getItems().clear();
            try {
                table.getItems().addAll(conn.getAllBooks(cat.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            rowSum.setText("[Row Selected: "+table.getItems().size()+"]");
            int sum=0;
            for(int c=0; c<table.getItems().size();c++){
                sum+=table.getItems().get(c).getAvailable();
            }
            booksum.setText("[Total Book: "+sum+"]");

        });

        searchBtn.setOnAction(event -> {
            table.getItems().clear();
            try {
                table.getItems().addAll(conn.getSearchBooks(searchBox.getText(),searchOption.getValue(),cat.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rowSum.setText("[Row Selected: "+table.getItems().size()+"]");
            int sum=0;
            for(int c=0; c<table.getItems().size();c++){
                sum+=table.getItems().get(c).getAvailable();
            }
            booksum.setText("[Total Book: "+sum+"]");
        });

        centerTop = new HBox();
        centerTop.getChildren().addAll(search, searchOption, label, cat);
        centerTop.setPrefSize(1150, 90);
        centerTop.setMaxSize(1150, 90);
        centerTop.setMinSize(1150, 90);
        centerTop.setSpacing(30);
        centerTop.setAlignment(Pos.CENTER);

        refreshBtn.setOnAction(event -> {
            refresh();
        });

        centerVBox=new VBox();
        centerVBox.setPadding(new Insets(20, 20, 20, 20));
        centerVBox.maxHeight(100);
        centerVBox.getChildren().addAll(centerTop, table,lowestCenter);
        centerVBox.setSpacing(10);


    }

   

}
