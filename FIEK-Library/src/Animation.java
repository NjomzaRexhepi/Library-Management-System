import javafx.scene.control.Button;

import java.util.concurrent.TimeUnit;

public class Animation implements Runnable {
    public static Animation obj=null;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    private static boolean stretched1,stretched2, running;
    private Animation(){

    }
    public static Animation getInstance(){
        stretched1=false;
        stretched2=false;
        if(obj==null){
            obj=new Animation();
        }
        return obj;
    }
    public void setButtonAnimation(Button btn1,Button btn2,Button btn3){
        this.btn1=btn1;
        this.btn2=btn2;
        this.btn3=btn3;
        running=false;
    }

    public void setButtonAnimation(Button btn1,Button btn2,Button btn3, Button btn4, Button btn5){
        this.btn4=btn1;
        this.btn5=btn2;
        this.btn6=btn3;
        this.btn7=btn4;
        this.btn8=btn5;
        running=true;
    }
    @Override
    public void run() {

        if (!running) {
            if (!stretched1) {
                stretched1 = true;
                int i = 0;
                while (i < 31) {
                    btn1.setPrefSize(300, i);
                    btn2.setPrefSize(300, i);
                    btn3.setPrefSize(300, i);


                    i++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                stretched1 = false;
                int i = 30;
                while (i > -1) {
                    btn1.setPrefSize(300, i);
                    btn2.setPrefSize(300, i);
                    btn3.setPrefSize(300, i);

                    i--;
                    try {
                        TimeUnit.MILLISECONDS.sleep(7);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }else{
            if (!stretched2) {
                stretched2 = true;
                int i = 0;
                while (i < 31) {
                    btn4.setPrefSize(300, i);
                    btn5.setPrefSize(300, i);
                    btn6.setPrefSize(300, i);
                    btn7.setPrefSize(300, i);
                    btn8.setPrefSize(300, i);


                    i++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                stretched2 = false;
                int i = 30;
                while (i > -1) {
                    btn4.setPrefSize(300, i);
                    btn5.setPrefSize(300, i);
                    btn6.setPrefSize(300, i);
                    btn7.setPrefSize(300, i);
                    btn8.setPrefSize(300, i);

                    i--;
                    try {
                        TimeUnit.MILLISECONDS.sleep(7);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
