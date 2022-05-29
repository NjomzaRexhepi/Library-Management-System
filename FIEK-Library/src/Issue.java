import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Member;
import java.sql.SQLException;

public class Issue {
    private static Issue obj=null;
    private Issue(){

    }
    public static Issue getInstance(){
        if(obj==null){
            obj=new Issue();
        }
        return obj;
    }
    
}
