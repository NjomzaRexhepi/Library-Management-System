import java.util.Date;

public class MemberFinanceTable {
    private int id;
    private String name;
    private String transactionType;
    private String occasion;
    private int amount;
    private int enteredBy;
    private Date date;
    private String time;

    public MemberFinanceTable(int id, String name, String transactionType, String occasion, int amount, int enteredBy, Date date, String time) {
        this.id = id;
        this.name = name;
        this.transactionType = transactionType;
        this.occasion = occasion;
        this.amount = amount;
        this.enteredBy = enteredBy;
        this.date = date;
        this.time = time;
    }
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
    
      public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(int enteredBy) {
        this.enteredBy = enteredBy;
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

  

   
}
