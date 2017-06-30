package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import objects.Edge;
import objects.Vertex;

/**
 * Created by User on 29.06.2016.
 */
public class generationDialogController {

    public static generationDialogController smth;
    public generationDialogController () {

    }

    public static int vertexNumber = 0;

    @FXML
    TextField generationText;

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
    public void gdOK(ActionEvent actionEvent) {
        System.out.println("Ok was pressed");
        System.out.println("Text " + generationText.getText() + " was entered.");
        String text = generationText.getText();
        int n;
        if (isDigit(text)) {
            n = Integer.parseInt(text);
            if (n > 0 && n < 700) {
                vertexNumber = n;
                mainController.smth.shouldGenerate = false;
                mainController.smth.graphGenerationRandom();
                mainController.smth.generationType = true;
                if (mainController.smth.DelBut.isDisable())
                    mainController.smth.DelBut.setDisable(false);
                if (mainController.smth.ClearBut.isDisable())
                    mainController.smth.ClearBut.setDisable(false);
                if (!mainController.smth.AddArmBut.isDisable())
                    mainController.smth.AddArmBut.setDisable(true);
                if (!mainController.smth.RandGenBut.isDisable())
                    mainController.smth.RandGenBut.setDisable(true);
                if (!mainController.smth.FixedEdgesBut.isDisable())
                    mainController.smth.FixedEdgesBut.setDisable(true);
                if (mainController.smth.GotovoBut.isDisable())
                    mainController.smth.GotovoBut.setDisable(false);
                if (!mainController.smth.FatherBut.isDisable())
                    mainController.smth.FatherBut.setDisable(true);
                if (!mainController.smth.StepBut.isDisable())
                    mainController.smth.StepBut.setDisable(true);
                mainController.smth.shouldBuild = true;
                ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                //return n;
            }
            else
            {

                generationDialogController.smth.generationText.clear();
                generationDialogController.smth.generationText.setPromptText(" 1 < n < 8 ");
                //return 0;
            }
        }
        else
        {
            generationDialogController.smth.generationText.clear();
            generationDialogController.smth.generationText.setPromptText(" 1 < n < 8 ");
            //return 0;
        }
    }

    @FXML
    public void gdCancel(ActionEvent actionEvent) {
        System.out.println("Cancle was pressed");
        mainController.smth.shouldGenerate = false;
        mainController.smth.generationType = false;
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public static Vertex vertGeneration () {
        ImageView image = new ImageView("content/drt1.png");
        Vertex vertex = new Vertex(image);
        double x;
        double y;
        boolean OK;
        do {
            OK = true;
            x = Math.random() * 450;
            y = Math.random() * 450;
            for (Vertex item : mainController.smth.buttons) {
                double path = Math.sqrt((item.circle.getX() - x) * (item.circle.getX() - x)
                        + (item.circle.getY() - y) * (item.circle.getY() - y));
                if (path < 65)
                    OK = false;
            }
        }
        while (OK == false);
        vertex.circle.setX(x);
        vertex.circle.setY(y);
        vertex.setNumber();
        //mainController.buttons.add(vertex);
        return vertex;
    }

}
