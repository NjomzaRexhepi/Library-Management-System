public class Books {
    private String title;
    private String author;
    private String category;
    private String shelfID;
    private int available;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

public void setShelfID(String shelfID) {
        this.shelfID = shelfID;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
public String getCategory() {
        return category;
    }

    public String getShelfID() {
        return shelfID;
    }

    public int getAvailable() {
        return available;
    }

    public Books(String title, String author, String category, String shelfID, int available){
        this.title=title;
        this.author=author;
        this.available=available;
        this.shelfID=shelfID;
        this.category=category;
    }
}