import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionClass {

    private static ConnectionClass obj = null;
    private Connection myCon;
    private HashMap<Integer, String> employees;
    private String url="jdbc:mysql://127.0.0.1:3306/library";
    private String username= "root";
    private String password= "";

    private ConnectionClass() {

        try {
            myCon = DriverManager.getConnection(url, username, password);
            ObservableList list = FXCollections.observableArrayList();
            Statement stat = myCon.createStatement();
            employees=new HashMap<Integer,String>();
            ResultSet resultSet= stat.executeQuery("select `ID`,`First Name`,`Last Name` FROM librarystuff");
            while (resultSet.next()){
                employees.put(resultSet.getInt("ID"), resultSet.getString("First Name")+ " "+resultSet.getString("Last name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionClass getInstance() {
        if (obj == null) {
            obj = new ConnectionClass();
        }

        return obj;
    }

    public boolean changeServer(String url, String username, String password, String softPass){
        boolean ret=false;
        if(softPass.equals("willPower")){
            this.url=url.trim();
            this.username= username.trim();
            this.password= password.trim();
            try {
                myCon = DriverManager.getConnection( this.url, this.username, this.password);
                ObservableList list = FXCollections.observableArrayList();
                Statement stat = myCon.createStatement();
                employees=new HashMap<Integer,String>();
                ResultSet resultSet= stat.executeQuery("select `ID`,`First Name`,`Last Name` FROM librarystuff");
                while (resultSet.next()){
                    employees.put(resultSet.getInt("ID"), resultSet.getString("First Name")+ " "+resultSet.getString("Last name"));
                }
              ret=true;
            } catch (SQLException e) {
                this.url="jdbc:mysql://127.0.0.1:3307/library";
                this.username= "root";
                this.password= "";
                e.printStackTrace();
                ret= false;
            }
        }
        return ret;
    }

    public ObservableList getAllBooks(String s) throws SQLException {
        ObservableList list = FXCollections.observableArrayList();
        Statement stat = myCon.createStatement();
        if (s.equals("All")) {
            s = "SELECT * FROM books;";
        } else {
            s = "SELECT * FROM books WHERE category=\"" + s + "\";";
        }
        ResultSet rSet = stat.executeQuery(s);
        while (rSet.next()) {
            list.add(new Books(rSet.getString("Title"), rSet.getString("Author"), rSet.getString("Category"), rSet.getString("Shelf-ID"), rSet.getInt("Available")));
        }
        rSet.close();
        stat.close();

        return list;
    }

    public ObservableList getEmployees(String search, String cat) throws SQLException {
        ObservableList list = FXCollections.observableArrayList();
        Statement stat = myCon.createStatement();
        search=search.trim();
        if(search.equals("")){
            search = "SELECT * FROM librarystuff ;";
        }else if(cat.equals("ID")){
            search = "SELECT * FROM librarystuff WHERE `"+cat+"` = " + search + ";";
        } else {
            search = "SELECT * FROM librarystuff WHERE `"+cat+"` Like \"%" + search + "%\";";
        }
        ResultSet rSet = stat.executeQuery(search);
        while (rSet.next()) {
            list.add(new EmployeeTable(rSet.getInt("ID"), rSet.getString("First Name"), rSet.getString("Last Name"), rSet.getString("email"), rSet.getString("Phone Number"), rSet.getInt("salary")));
        }
        rSet.close();
        stat.close();

        return list;
    }

    public ObservableList getMembers(String search, String cat) throws SQLException {
        ObservableList list = FXCollections.observableArrayList();
        Statement stat = myCon.createStatement();
        search=search.trim();
        if(search.equals("")){
            search = "SELECT * FROM librarymember ;";
        }else if(cat.equals("ID")){
            search = "SELECT * FROM librarymember WHERE `"+cat+"` = " + search + ";";
        } else {
            search = "SELECT * FROM librarymember WHERE `"+cat+"` Like \"%" + search + "%\";";
        }
        ResultSet rSet = stat.executeQuery(search);
        while (rSet.next()) {
            list.add(new MemberTable(rSet.getInt("ID"), rSet.getString("First Name"), rSet.getString("Last Name"), rSet.getString("email"), rSet.getString("Phone Number")));
        }
        rSet.close();
        stat.close();

        return list;
    }

    public ObservableList getSearchBooks(String search, String searchBy, String category) throws SQLException {
        ObservableList list = FXCollections.observableArrayList();
        Statement stat = myCon.createStatement();
        if (category.equals("All")) {
            if (searchBy.equals("Search by Title")) {
                search = "SELECT * FROM books WHERE Title LIKE \"%" + search + "%\";";
            } else {
                search = "SELECT * FROM books WHERE Author LIKE \"%" + search + "%\";";
            }
        } else {
            if (searchBy.equals("Search by Title")) {
                search = "SELECT * FROM books WHERE Title LIKE \"%" + search + "%\" AND category=\"" + category + "\";";
            } else {
                search = "SELECT * FROM books WHERE Author LIKE \"%" + search + "%\" AND category=\"" + category + "\";";
            }
        }
        ResultSet rSet = stat.executeQuery(search);
        while (rSet.next()) {
            list.add(new Books(rSet.getString("Title"), rSet.getString("Author"), rSet.getString("Category"), rSet.getString("Shelf-ID"), rSet.getInt("Available")));
        }
        rSet.close();
        stat.close();

        return list;
    }

    public ArrayList<String> getBooksCategory() throws SQLException {
        ArrayList<String> ret = new ArrayList<String>();
        Statement stat = myCon.createStatement();
        ResultSet rSet = stat.executeQuery("SELECT category" +
                " FROM books" +
                " GROUP BY category;");
        while (rSet.next()) {
            ret.add(rSet.getString("category"));
        }
        rSet.close();
        stat.close();

        return ret;
    }

    public AdminWindow adminCheck(String email, String enteredPassword) throws SQLException {
        String s = "SELECT * FROM libraryadmin WHERE email=\"" + email + "\";";
        AdminWindow ret = null;
        Statement stat = myCon.createStatement();
        ResultSet rs = stat.executeQuery(s);
        // System.out.println(rs.getString("password"));
        while (rs.next()) {
            s = rs.getString("password");
            if (s.equals(enteredPassword)) {
                ret = new AdminWindow(rs.getInt("ID"), rs.getString("First Name"), rs.getString("Last Name"), rs.getString("email"), s, rs.getInt("Phone Number"), "Admin");
            }
        }
        return ret;
    }

    public AdminWindow employeeCheck(String email, String enteredPassword) throws SQLException {
        String s = "SELECT * FROM librarystuff WHERE email=\"" + email + "\";";
        AdminWindow ret = null;
        Statement stat = myCon.createStatement();
        ResultSet rs = stat.executeQuery(s);
        // System.out.println(rs.getString("password"));
        while (rs.next()) {
            s = rs.getString("password");
            if (s.equals(enteredPassword)) {
                ret = new AdminWindow(rs.getInt("ID"), rs.getString("First Name"), rs.getString("Last Name"), rs.getString("email"), s, rs.getInt("Phone Number"), "Employee");
            }
        }
        return ret;
    }

    public AdminWindow memberCheck(String email, String enteredPassword) throws SQLException {
        String s = "SELECT * FROM librarymember WHERE email=\"" + email + "\";";
        AdminWindow ret = null;
        Statement stat = myCon.createStatement();
        ResultSet rs = stat.executeQuery(s);
        while (rs.next()) {
            s = rs.getString("password");
            if (s.equals(enteredPassword)) {
                ret = new AdminWindow(rs.getInt("ID"), rs.getString("First Name"), rs.getString("Last Name"), rs.getString("email"), s, rs.getInt("Phone Number"), "Member");
            }
        }
        return ret;
    }

    public int addMember(String firstName, String lastName, String email, String password, String phoneNumber) throws SQLException {
        Statement state = myCon.createStatement();
        int rs = state.executeUpdate("Insert Into librarymember(`First Name`, `Last Name`, email, `password`, `Phone Number`)" +
                " values (\"" + firstName + "\",\"" + lastName + "\",\"" + email + "\",\"" + password + "\",\"" + phoneNumber + "\")");

        int ret = 0;

        ResultSet rs1 = state.executeQuery("Select ID from librarymember order by id desc limit 1");
        while (rs1.next()) {
            ret = rs1.getInt("ID");
        }

        return ret;
    }

    public void addBook(String title, String author, String category, String shelfID, String amount) throws SQLException {
        Statement state = myCon.createStatement();
        ResultSet resultSet = state.executeQuery("select `Shelf-ID`,`Available` from books where Title=\"" + title + "\" AND Author=\"" + author + "\"");
        int avail = 0;
        boolean done = false;
        while (resultSet.next()) {
            if (resultSet.getString("Shelf-ID").equals(shelfID)) {
                avail = resultSet.getInt("Available");
                amount = "" + (Integer.parseInt(amount) + avail);
                int rs = state.executeUpdate("update books set Available = " + amount + " where Title=\"" + title + "\" AND Author=\"" + author + "\" AND `Shelf-ID`=\"" + shelfID + "\";");
                done = true;
                break;
            }
        }
        if (!done) {
            int rs = state.executeUpdate("Insert Into books(`Title`, `Author`, Category, `Shelf-ID`, `Available`)" +
                    " values (\"" + title + "\",\"" + author + "\",\"" + category + "\",\"" + shelfID + "\"," + amount + ")");
        }

    }

    public int addEmployee(String firstName, String lastName, String email, String password, String phoneNumber, String salary) throws SQLException {
        Statement state = myCon.createStatement();
        int rs = state.executeUpdate("Insert Into librarystuff(`First Name`, `Last Name`, email, `password`, `salary`, `Phone Number`)" +
                " values (\"" + firstName + "\",\"" + lastName + "\",\"" + email + "\",\"" + password + "\",\"" + salary + "\",\"" + phoneNumber + "\")");

        int ret = 0;

        ResultSet rs1 = state.executeQuery("Select ID from librarystuff order by id desc limit 1");
        while (rs1.next()) {
            ret = rs1.getInt("ID");
        }

        return ret;
    }

    public void addAdmin(String id) throws SQLException {

        Statement state = myCon.createStatement();
        ResultSet res = state.executeQuery("select * from librarystuff where id=" + id);

        String firstNameNF = "";
        String lastNameNF = "";
        String passwordNF = "";
        String emailNF = "";
        String phoneNumberNF = "";


        while (res.next()) {
            firstNameNF = res.getString("First Name");
            lastNameNF = res.getString("Last Name");
            passwordNF = res.getString("password");
            emailNF = res.getString("email");
            phoneNumberNF = res.getString("Phone Number");
        }

        final String firstName = firstNameNF;
        final String lastName = lastNameNF;
        final String password = passwordNF;
        final String email = emailNF;
        final String phoneNumber = phoneNumberNF;

        VBox box = new VBox();
        Label idLabel = new Label();
        idLabel.setStyle("-fx-font-size: 18;" +
                " -fx-text-fill: green;");
        idLabel.setText("Making " + firstName + " " + lastName + " an Admin");
        Button confirm = new Button("Confirm");
        confirm.setPrefSize(110, 25);
        confirm.setMaxSize(110, 25);
        Button back = new Button("Back");
        back.setPrefSize(110, 25);
        back.setMaxSize(110, 25);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        hbox.getChildren().addAll(back, confirm);
        box.getChildren().addAll(idLabel, hbox);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        Scene scene = new Scene(box, 250, 250);
        scene.getStylesheets().add("style.css");
        Stage arg = new Stage();
        arg.setScene(scene);
        arg.initModality(Modality.APPLICATION_MODAL);
        arg.show();

        back.setOnAction(event -> arg.close());
        confirm.setOnAction(event -> {
            try {
                state.executeUpdate("insert into libraryadmin(`ID`,`First Name`, `Last Name`, `email`, `password`, `Phone Number`) " +
                        " values(" + id + ",\"" + firstName + "\",\"" + lastName + "\",\"" + email + "\",\"" + password + "\"," + phoneNumber + ")");
                arg.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void remAdmin(String id, String table) throws SQLException {

        Statement state = myCon.createStatement();
        ResultSet res = state.executeQuery("select * from " + table + " where id=" + id);

        String firstNameNF = "";
        String lastNameNF = "";
        String passwordNF = "";
        String emailNF = "";
        String phoneNumberNF = "";


        while (res.next()) {
            firstNameNF = res.getString("First Name");
            lastNameNF = res.getString("Last Name");
            passwordNF = res.getString("password");
            emailNF = res.getString("email");
            phoneNumberNF = res.getString("Phone Number");
        }

        final String firstName = firstNameNF;
        final String lastName = lastNameNF;
        final String password = passwordNF;
        final String email = emailNF;
        final String phoneNumber = phoneNumberNF;

        VBox box = new VBox();
        Label idLabel = new Label();
        idLabel.setStyle("-fx-font-size: 18;" +
                " -fx-text-fill: green;");
        switch (table) {
            case "libraryadmin":
                idLabel.setText("Removing " + firstName + " " + lastName + " from Admin");
                break;
            case "librarymember":
                idLabel.setText("Removing " + firstName + " " + lastName + " from Member");
                break;
            case "librarystuff":
                idLabel.setText("Removing " + firstName + " " + lastName + " from Employee");
                break;
        }

        Button confirm = new Button("Confirm");
        confirm.setPrefSize(110, 25);
        confirm.setMaxSize(110, 25);
        Button back = new Button("Back");
        back.setPrefSize(110, 25);
        back.setMaxSize(110, 25);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        hbox.getChildren().addAll(back, confirm);
        box.getChildren().addAll(idLabel, hbox);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        Scene scene = new Scene(box, 250, 250);
        scene.getStylesheets().add("style.css");
        Stage arg = new Stage();
        arg.setScene(scene);
        arg.initModality(Modality.APPLICATION_MODAL);
        arg.show();

        back.setOnAction(event -> arg.close());
        confirm.setOnAction(event -> {
            try {
                int a = state.executeUpdate("Delete From " + table + " where id=" + id);
                arg.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void remBooks(String title, String author) throws SQLException {

        Statement state = myCon.createStatement();
        ResultSet res = state.executeQuery("select `Shelf-ID`,`Available` from books where Title= \"" + title + "\" AND Author= \"" + author + "\"");

        String label = "";
        HashMap<String, Integer> shelf = new HashMap<String, Integer>();
        String s;
        int t;

        while (res.next()) {
            s = res.getString("Shelf-ID");
            t = res.getInt("Available");
            shelf.put(s, t);
            label = label + "Shelf-Id: " + s + " Available: " + t;
            label += "\n";
        }
        if (label.length() == 0) {
            label = "Not found";
        }

        Label lab = new Label(label);
        lab.setStyle("-fx-font-size: 14;" +
                " -fx-text-fill: green;");

        VBox box = new VBox();
        Label idLabel = new Label();
        idLabel.setStyle("-fx-font-size: 18;" +
                " -fx-text-fill: green;");
        idLabel.setText("Removing " + title);
        idLabel.setAlignment(Pos.CENTER);

        TextField shelfText = new TextField();
        shelfText.setPromptText("Enter Shelf-ID");
        shelfText.setPrefSize(270, 35);
        shelfText.setMaxSize(270, 35);
        shelfText.setFocusTraversable(false);
        TextField available = new TextField();
        available.setPromptText("Enter Number of Books to Remove");
        available.setPrefSize(270, 35);
        available.setMaxSize(270, 35);
        available.setFocusTraversable(false);
        Button confirm = new Button("Confirm");
        confirm.setPrefSize(110, 25);
        confirm.setMaxSize(110, 25);
        Button back = new Button("Back");
        back.setPrefSize(110, 25);
        back.setMaxSize(110, 25);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(back, confirm);
        box.getChildren().addAll(idLabel, shelfText, available, hbox, lab);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        Scene scene = new Scene(box, 300, 500);
        scene.getStylesheets().add("style.css");
        Stage arg = new Stage();
        arg.setScene(scene);
        arg.initModality(Modality.APPLICATION_MODAL);
        arg.show();

        back.setOnAction(event -> arg.close());
        confirm.setOnAction(event -> {
            int rem = shelf.get(shelfText.getText()) - Integer.parseInt(available.getText());
            if (rem < 0) {
                shelfText.clear();
                available.clear();
                shelfText.setStyle("-fx-background-color: #FFB6C1");
                available.setStyle("-fx-background-color: #FFB6C1");
            } else {
                try {

                    int a = state.executeUpdate("update books set `Available`=" + rem + " where Title=\"" + title + "\" AND " +
                            "Author=\"" + author + "\" AND `Shelf-ID`=\"" + shelfText.getText() + "\"");
                    arg.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void IssueBook(int id, String title, String author, String shelfID, String amount, String memberID) throws SQLException {
        Statement stat = myCon.createStatement();
        String search = "SELECT Available FROM books WHERE Title=\"" + title + "\" AND Author=\"" + author + "\" AND `Shelf-ID`=\"" + shelfID + "\";";
        ResultSet rs = stat.executeQuery(search);
        int available = 0;
        while (rs.next()) {
            available = rs.getInt("Available");
        }
        rs.close();

        if (available >= Integer.parseInt(amount)) {
            ObservableList list = FXCollections.observableArrayList();
            String s = "";

            ResultSet rSet = stat.executeQuery("Select ID from issuedbooks order by ID DESC Limit 1");
            String startID = "";
            while (rSet.next()) {
                startID += (rSet.getInt("ID") + 1);
            }
            rSet.close();

            int counter = 0;
            while (counter < Integer.parseInt(amount)) {
                s = "Insert Into issuedbooks(`Title`,`Author`,`Issued Date`,`Issued Time`, `Issued To`, `Issued By`) " +
                        "values(\"" + title + "\",\"" + author + "\",current_date(),current_time()," + memberID + "," + id + ");";
                stat.executeUpdate(s);
                counter++;
            }
            if (counter > 1) {
                rSet = stat.executeQuery("Select ID from issuedbooks order by ID DESC Limit 1");
                while (rSet.next()) {
                    startID += "-" + rSet.getInt("ID");
                }
                rSet.close();
            }
            int done = stat.executeUpdate("update books set `Available`=" + (available - counter) + " where Title=\"" + title + "\" AND Author=\"" + author + "\" AND `Shelf-ID`=\"" + shelfID + "\";");

            Label lastLabel = new Label("Issue ID: " + (available - counter));
            lastLabel.setStyle("-fx-font-size: 14;" +
                    " -fx-text-fill: green;");
            Button ok = new Button("OK");
            VBox box = new VBox();
            box.setSpacing(20);
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(lastLabel, ok);
            Scene scene = new Scene(box, 250, 250);
            scene.getStylesheets().add("style.css");
            Stage arg = new Stage();
            arg.setScene(scene);
            arg.initModality(Modality.APPLICATION_MODAL);
            arg.initStyle(StageStyle.UNDECORATED);
            arg.show();
            ok.setOnAction(event -> arg.close());

        }

        stat.close();
    }

    public void receiveBook(int id, String issueID, String shelfID) throws SQLException {
        Statement stat = myCon.createStatement();

        ResultSet rs = stat.executeQuery("Select Title, Author from issuedbooks where ID= " + issueID + ";");
        String title = "";
        String author = "";
        while (rs.next()) {
            title = rs.getString("Title");
            author = rs.getString("Author");
        }
        rs.close();
        String search = "SELECT Available FROM books WHERE Title=\"" + title + "\" AND Author=\"" + author + "\" AND `Shelf-ID`=\"" + shelfID + "\";";
        rs = stat.executeQuery(search);
        int available = 0;
        while (rs.next()) {
            available = rs.getInt("Available");
        }
        stat.executeUpdate("update books " +
                " set `Available`=" + (available + 1) +
                " where Title=\"" + title + "\" AND author=\"" + author + "\" AND `Shelf-ID`=\"" + shelfID + "\"");
        stat.executeUpdate("update issuedbooks " +
                " set `Returned Date`= current_date(), `Returned Time`= current_time()" +
                " where ID= " + issueID + ";");

        stat.close();
    }

    public String[] getMisc(String field) throws SQLException {
        String[] ret = new String[4];
        Statement stat = myCon.createStatement();
        ResultSet resultSet = stat.executeQuery("SELECT * FROM Misc WHERE Field=\"" + field + "\";");
        while (resultSet.next()) {
            ret[0] = "" + resultSet.getInt("Amount");
            ret[1] = "" + resultSet.getInt("Type");
            ret[2] = "" + resultSet.getInt("Updated By(ID)");
            ret[3] = "" + resultSet.getDate("Update Date");
        }
        resultSet.close();
        stat.close();

        return ret;
    }

    public void setMisc(String field, String amount, String type, int id) throws SQLException {
        Statement stat = myCon.createStatement();
        stat.executeUpdate("Update Misc SET `Amount`=" + amount + ", `Type`=" + type + ", `Update Date`=current_date(), `Updated By(ID)`=" + id + " WHERE Field=\"" + field + "\";");
        stat.close();
    }

    public void setSalary(String id, String salary) throws SQLException {
        Statement stat = myCon.createStatement();
        stat.executeUpdate("Update librarystuff SET `salary`=\"" + salary + "\" WHERE `ID`=" + id + ";");
        stat.close();
    }

    public void employeePayment(String employeeID, String type, String occasion, int enteredBy, String amount) throws SQLException {
        Statement stat = myCon.createStatement();
        int salary = 0;
        if (!type.equals("Medical Fee")) {
            ResultSet resultSet = stat.executeQuery("SELECT salary FROM librarystuff");
            while (resultSet.next()) {
                salary = resultSet.getInt("salary");
            }
            resultSet.close();
        } else {
            salary = Integer.parseInt(amount);
        }
        stat.executeUpdate("INSERT INTO employeepaymenthistory(ID, Date, Time, `Transaction Type`, `Occasion`, Amount, `Entered By`) " +
                " values(" + employeeID + ", current_date(), current_time(), \"" + type + "\", \"" + occasion + "\" , " + salary + ", " + enteredBy + ")");
        stat.close();
    }

    public int[] getamounts() throws SQLException {
        Statement stat = myCon.createStatement();
        int[] ret = new int[3];
        ResultSet rs = stat.executeQuery("SELECT * FROM misc WHERE `Field`=\"Membership Fee\" ");
        while (rs.next()) {
            ret[0] = rs.getInt("Amount");
        }
        rs.close();
        rs = stat.executeQuery("SELECT * FROM misc WHERE `Field`=\"Delay Fee\" ");
        while (rs.next()) {
            ret[1] = rs.getInt("Amount");
            ret[2] = rs.getInt("Type");
        }
        rs.close();
        stat.close();
        return ret;
    }

    public void memberPayment(String memberID, String type, String occasion, int enteredBy, String amount) throws SQLException {
        Statement stat = myCon.createStatement();
        int salary = 0;
        if (!type.equals("Medical Fee")) {
            ResultSet resultSet = stat.executeQuery("SELECT salary FROM librarystuff");
            while (resultSet.next()) {
                salary = resultSet.getInt("salary");
            }
            resultSet.close();
        }
        stat.executeUpdate("INSERT INTO memberfeehistory(ID, Date, Time, `Transaction Type`, `Occasion`, Amount, `Entered By`) " +
                " values(" + memberID + ", current_date(), current_time(), \"" + type + "\", \"" + occasion + "\" , " + amount + ", " + enteredBy + ")");
        stat.close();
    }

    public int getDelayedAmount(String id) throws SQLException {
        Statement stat = myCon.createStatement();
        ResultSet rs = stat.executeQuery("SELECT `Issued Date` FROM issuedbooks WHERE ID = " + id);
        Date hist = new Date(System.currentTimeMillis());
        java.util.Date date = new java.util.Date();
        while (rs.next()) {
            date = rs.getDate("Issued Date");
        }
        java.util.Date now = new java.util.Date(System.currentTimeMillis());

        return (int) (now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
    }

    public ObservableList getIssuedHistory(String searchBox, String startDate, String endDate, String cat, String ability, int id) throws SQLException {
        ObservableList list = FXCollections.observableArrayList();
        Statement stat = myCon.createStatement();
        int Employee1=0;
        int Employee2=0;
        String s = "SELECT issuedbooks.* , librarymember.`First Name`, librarymember.`Last Name` FROM issuedbooks JOIN librarymember WHERE `Issued Date`>=\"" + startDate + "\" AND `Issued Date`<=\"" + endDate + "\" " +
                " AND issuedbooks.`Issued To`=librarymember.`ID` ";
        if(ability.equals("Member")){
            s = s + " AND `Issued To`=" + id;
        }
        if (!searchBox.equals("")) {
            s = s + " AND `" + cat + "` LIKE \"%" + searchBox + "%\"";
        }
        ResultSet rSet = stat.executeQuery(s);
        while (rSet.next()) {
            Employee2=rSet.getInt("Received By");
            Employee1=rSet.getInt("Issued By");
            list.add(new IssueHistory(rSet.getString("Title"), rSet.getString("Author"), rSet.getInt("ID"),Employee1, employees.get(Employee1),
                    rSet.getInt("Issued To"), rSet.getString("First Name")+" "+rSet.getString("Last Name"), Employee2, employees.get(Employee2), rSet.getDate("Issued Date"), rSet.getString("Issued Time"),
                    rSet.getDate("Returned Date"), rSet.getString("Returned Time")));
        }
        rSet.close();
        stat.close();

        return list;
    }

    public ObservableList getAddRemBookHistory(String search, String startDate, String endDate, String cat) throws SQLException {

        ObservableList list = FXCollections.observableArrayList();
        Statement stat = myCon.createStatement();
        String s = "SELECT addrembookhistory.* ,`First Name`,`Last Name` FROM addrembookhistory JOIN librarystuff WHERE `Date`>=\"" + startDate + "\" AND `Date`<=\"" + endDate + "\" " +
                " AND addrembookhistory.`Acted By`=librarystuff.ID ";

                s = s + "AND `" + cat + "` LIKE \"%" + search + "%\"";

        ResultSet rSet = stat.executeQuery(s);
        while (rSet.next()) {
            list.add(new AddRemBookHistory(rSet.getString("Title"), rSet.getString("Author"), rSet.getDate("Date"), rSet.getString("Time"),
                    rSet.getString("Action"), rSet.getInt("Acted By"), rSet.getString("First Name")+" "+rSet.getString("Last Name"), rSet.getInt("Amount")));
        }
        rSet.close();
        stat.close();

        return list;
    }

    public ObservableList getEmployeeFinanceHistory(String search, String cat, String startDate, String endDate) throws SQLException{
        ObservableList obs= FXCollections.observableArrayList();
        Statement stat= myCon.createStatement();
        String s= "select * FROM employeepaymenthistory WHERE `Date`>=\""+startDate+"\" AND `Date`<=\""+ endDate+"\" ";
        if(!cat.equals("All")){
            s=s+" AND `Transaction Type`=\""+cat+"\"";
        }
        if(!search.equals("")){
            s=s+" AND `ID`="+search+"";
        }
        ResultSet rs=stat.executeQuery(s);
        int id=0;
        while(rs.next()){
            id= rs.getInt("ID");
            obs.add(new EmployeeFinanceTable(id, employees.get(id), rs.getString("Transaction Type"), rs.getString("Occasion"), rs.getInt("Amount"),
                    rs.getInt("Entered By"), rs.getDate("Date"), rs.getString("Time")));

        }
        rs.close();
        stat.close();;

        return obs;
    }

    public ObservableList getMemberFeeHistory(String search, String cat, String startDate, String endDate) throws SQLException{
        ObservableList obs= FXCollections.observableArrayList();
        Statement stat= myCon.createStatement();
        String s= "select memberfeehistory.*, `First Name`,`Last Name` FROM memberfeehistory JOIN librarymember " +
                " WHERE memberfeehistory.ID=librarymember.ID AND " +
                "`Date`>=\""+startDate+"\" AND `Date`<=\""+ endDate+"\" ";
        if(!cat.equals("All")){
            s=s+" AND `Transaction Type`=\""+cat+"\"";
        }
        if(!search.equals("")){
            s=s+" AND `ID`="+search+"";
        }
        ResultSet rs=stat.executeQuery(s);
        while(rs.next()){
            obs.add(new MemberFinanceTable(rs.getInt("ID"), rs.getString("First Name")+" "+rs.getString("Last Name"), rs.getString("Transaction Type"), rs.getString("Occasion"), rs.getInt("Amount"),
                    rs.getInt("Entered By"), rs.getDate("Date"), rs.getString("Time")));

        }
        rs.close();
        stat.close();;

        return obs;
    }
}
