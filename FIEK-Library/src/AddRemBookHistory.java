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

  
}
