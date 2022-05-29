
import java.util.Date;

public class IssueHistory {
    private String title;
    private String author;
    private int issuedID;
    private int issuedByID;
    private String issuedByName;
    private int issuedToID;
    private String issuedToName;
    private int receiverID;
    private String receiverName;
    private Date issuedDate;
    private String issuedTime;
    private Date retDate;
    private String retTime;

    public IssueHistory(String title, String author, int issuedID, int issuedByID, String issuedByName, int issuedToID, String issuedToName,
                        int receiverID, String receiverName, Date issuedDate, String issuedTime, Date retDate,String retTime){
        this.title=title;
        this.author=author;
        this.issuedID=issuedID;
        this.issuedByID=issuedByID;
        this.issuedByName=issuedByName;
        this.issuedToID=issuedToID;
        this.issuedToName=issuedToName;
        this.receiverID=receiverID;
        this.receiverName=receiverName;
        this.issuedDate=issuedDate;
        this.issuedTime=issuedTime;
        this.retDate=retDate;
        this.retTime=retTime;
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

    public int getIssuedID() {
        return issuedID;
    }

    public void setIssuedID(int issuedID) {
        this.issuedID = issuedID;
    }

    public int getIssuedByID() {
        return issuedByID;
    }

    public void setIssuedByID(int issuedByID) {
        this.issuedByID = issuedByID;
    }

    public String getIssuedByName() {
        return issuedByName;
    }

    public void setIssuedByName(String issuedByName) {
        this.issuedByName = issuedByName;
    }

    public int getIssuedToID() {
        return issuedToID;
    }

    public void setIssuedToID(int issuedToID) {
        this.issuedToID = issuedToID;
    }

    public String getIssuedToName() {
        return issuedToName;
    }

    public void setIssuedToName(String issuedToName) {
        this.issuedToName = issuedToName;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    public Date getRetDate() {
        return retDate;
    }

    public void setRetDate(Date retDate) {
        this.retDate = retDate;
    }

    public String getRetTime() {
        return retTime;
    }

    public void setRetTime(String retTime) {
        this.retTime = retTime;
    }



}
