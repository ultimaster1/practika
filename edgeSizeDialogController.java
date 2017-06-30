package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Edge;
import objects.Vertex;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by User on 26.06.2017.SS
 */

public class edgeSizeDialogController {

    @FXML
    TextField edgeSize;
    public static double edgeWeight = -1;


     private boolean isDigit(String text)
    {
        try{
            int number = Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
   final EventHandler<KeyEvent> actionEvent =
            new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        System.out.println("Ok was pressed");
                        System.out.println("Text " + edgeSize.getText() + " was entered.");
                        String text = edgeSize.getText();
                        int weight;
                        if (isDigit(text)) {
                            weight = Integer.parseInt(text);
                            if (weight !=0) {
                                edgeWeight = (int) weight;
                                System.out.println("Ok was pressed");
                                createEdgeFixedSize();
                                System.out.println("Ok was pressed");
                                mainController.smth.shouldGenerate = false;
                                mainController.smth.generationType = true;
                                if (mainController.smth.DelBut.isDisable())
                                    mainController.smth.DelBut.setDisable(false);
                                if (mainController.smth.ClearBut.isDisable())
                                    mainController.smth.ClearBut.setDisable(false);
                                if (!mainController.smth.AddArmBut.isDisable())
                                    mainController.smth.AddArmBut.setDisable(true);
                                if (!mainController.smth.RandGenBut.isDisable())
                                    mainController.smth.RandGenBut.setDisable(true);
                                if (mainController.smth.GotovoBut.isDisable())
                                    mainController.smth.GotovoBut.setDisable(false);
                                if (!mainController.smth.FatherBut.isDisable())
                                    mainController.smth.FatherBut.setDisable(true);
                                if (!mainController.smth.StepBut.isDisable())
                                    mainController.smth.StepBut.setDisable(true);
                                mainController.smth.activePane.getChildren().remove(mainController.smth.edges.get(mainController.smth.edges.size() - 1).txtWeight);
                                mainController.smth.shouldBuild = true;
                                mainController.smth.activePane.getChildren().add(mainController.smth.edges.get(mainController.smth.edges.size() - 1).txtWeight);
                                ((Stage) ((Node) keyEvent.getSource()).getScene().getWindow()).close();
                                //return n;
                            }
                            else {
                                edgeSize.clear();
                                edgeSize.setPromptText(" incorrect value ");
                            }
                        }
                        else
                        {

                            edgeSize.clear();
                            edgeSize.setPromptText(" incorrect value ");
                        }
                    }

                    keyEvent.consume();
                    }
            };
    public void edgeSizeOK(ActionEvent actionEvent) {
        System.out.println("Ok was pressed");
        System.out.println("Text " + edgeSize.getText() + " was entered.");
        String text = edgeSize.getText();
        int weight;
        if (isDigit(text)) {
            weight = Integer.parseInt(text);
            if (weight !=0) {
                edgeWeight = (int) weight;
                System.out.println("Ok was pressed");
                createEdgeFixedSize();
                System.out.println("Ok was pressed");
                mainController.smth.shouldGenerate = false;
                mainController.smth.generationType = true;
                if (mainController.smth.DelBut.isDisable())
                    mainController.smth.DelBut.setDisable(false);
                if (mainController.smth.ClearBut.isDisable())
                    mainController.smth.ClearBut.setDisable(false);
                if (!mainController.smth.AddArmBut.isDisable())
                    mainController.smth.AddArmBut.setDisable(true);
                if (!mainController.smth.RandGenBut.isDisable())
                    mainController.smth.RandGenBut.setDisable(true);
                if (mainController.smth.GotovoBut.isDisable())
                    mainController.smth.GotovoBut.setDisable(false);
                if (!mainController.smth.FatherBut.isDisable())
                    mainController.smth.FatherBut.setDisable(true);
                if (!mainController.smth.StepBut.isDisable())
                    mainController.smth.StepBut.setDisable(true);
                mainController.smth.activePane.getChildren().remove(mainController.smth.edges.get(mainController.smth.edges.size() - 1).txtWeight);
                mainController.smth.shouldBuild = true;
                mainController.smth.activePane.getChildren().add(mainController.smth.edges.get(mainController.smth.edges.size() - 1).txtWeight);
                ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                //return n;
            }
            else {
                edgeSize.clear();
                edgeSize.setPromptText(" incorrect value ");
            }
            }
            else
            {

                edgeSize.clear();
                edgeSize.setPromptText(" incorrect value ");
            }
    }

    @FXML




    public void edgeSizeCancel(ActionEvent actionEvent) {
        System.out.println("Cancle was pressed");
        mainController.smth.edges.get(mainController.smth.edges.size() - 1).isChecked = true;
        mainController.smth.deleteEdge();
        //mainController.deleteEdge();
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    private void createEdgeFixedSize()  {
        System.out.println("Create edge");
        double weight = edgeWeight;
        mainController.smth.activePane.getChildren().remove(mainController.smth.edges.get(mainController.smth.edges.size() - 1).txtWeight);
        //mainController.activePane.getChildren().remove(mainController.edges.get(mainController.edges.size() - 1).txtWeight);
        mainController.smth.edges.get(mainController.smth.edges.size() - 1).setWeight(weight);
        mainController.smth.activePane.getChildren().add(mainController.smth.edges.get(mainController.smth.edges.size() - 1).txtWeight);
        // Media sound = new Media(new File("src/content/Edge.wav").toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        // mediaPlayer.play();
    }

}
