import java.util.Date;

public class AddRemBookHistory {
    private String title;
    private String author;
    private Date date;
    private String time;
    private String act;
    private int addedByID;
    private String addedByName;
    private int amount;

    public AddRemBookHistory(String title,String author, Date date, String time, String act, int addedByID, String addedByName, int amount){
        this.title=title;
        this.author=author;
        this.date=date;
        this.time=time;
        this.act=act;
        this.addedByID=addedByID;
        this.addedByName=addedByName;
        this.amount=amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public int getAddedByID() {
        return addedByID;
    }

    public void setAddedByID(int addedByID) {
        this.addedByID = addedByID;
    }

    public String getAddedByName() {
        return addedByName;
    }

    public void setAddedByName(String addedByName) {
        this.addedByName = addedByName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
