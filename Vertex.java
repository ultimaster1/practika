package objects;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
/**
 * Created by User on 26.06.2017.
 */
public class Vertex {
    public static int i = 0;
    public static int j = 0;
    public ImageView circle;
    public boolean isChecked;
    private int number;
    public Text txt;
    private  int nonenumber;
    public Text path;
    public Vertex(ImageView circle)
    {
        this.nonenumber=j;
        this.number = i;
        this.circle=circle;
        isChecked=false;
        i++;
        //txt = new Text(this.circle.getX() - 20,this.circle.getY() - 20,""+getNumber());
    }
    public int getPath () {
        return this.nonenumber;
    }
    public void showPath (double u){
 this.path = new Text(this.circle.getX() + 40  , this.circle.getY() + 8,"" + u);
}

    public void setNumber (){
        this.txt = new Text(this.circle.getX() + 8, this.circle.getY() + 8,"" + getNumber());
    }

    public void setNumberAfterDelete (int number) {
        this.number = number;
    }

    public void syncronize () {
        this.txt.setX(this.circle.getX() + 8);
        this.txt.setY(this.circle.getY() + 8);
    }

    public int getNumber () {
        return this.number;
    }

}