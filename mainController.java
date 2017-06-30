package controllers;
import javafx.scene.text.Text;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Vertex;
import objects.Edge;
import start.Main;

//import fxml.*;
public class mainController {

    public mainController () {

}
    public final int sizeof2=25;
    public final int MYCONST1 = 1000;
    public final int MYCONST2 = 1000;
    public final double INFINITE = (pow(MYCONST1,MYCONST2));
    public static mainController smth;



    public static ArrayList<Vertex> buttons = new ArrayList<Vertex>();

    public static ArrayList<Edge> edges = new ArrayList<Edge>();

    public static ArrayList<Double> vec = new ArrayList<Double>(Vertex.i);

    public static ArrayList<Integer> p = new ArrayList<Integer>();

    public static boolean shouldBuild = false;
    public static boolean shouldGenerate = false;
    public static boolean fordInWork = false;
    public static boolean nextStep = false;

    public static int shpEdgesCounter = 0;
    public static int relaxCounter = 0;

    public static boolean last = false;
    public static int counter = 0;
    public static  int cycleCounter = 0;
    public static Vertex startVert;
    public  static  boolean cycle = false;

    public static boolean fixedSizeType = false;

    public static boolean pressed;

    public static boolean generationType = false;


    @FXML
    public void initialize () {
        smth = this;
    }

    @FXML
    Pane activePane;

    @FXML
    Button StepBut;

    @FXML
    Button DelBut;

    @FXML
    Button ClearBut;

    @FXML
    Button RandGenBut;

    @FXML
    Button AddArmBut;

    @FXML
    Button GotovoBut;

    @FXML
    Button FatherBut;

    @FXML
    Button ResultBut;

    @FXML
    Button FixedEdgesBut;

    public void createButton (MouseEvent event) throws IOException  {
        if (!pressed) {
            if (event.getClickCount() == 2)
                twoMouseClick(event);
            else
                oneMouseClick(event);
            }
        else
            pressed = false;
    }

    private void twoMouseClick(MouseEvent event)
    {
        if (createOnCorrectPlace(event)) {
            if (Vertex.i == 0)
                mainController.smth.GotovoBut.setDisable(false);
            ImageView image = new ImageView("content/drt1.png");
             Vertex vertex = new Vertex(image);
             vertex.circle.setX(event.getX() - sizeof2);
             vertex.circle.setY(event.getY() - sizeof2);
             vertex.setNumber();
            buttons.add(vertex);
            mainController.smth.activePane.getChildren().addAll(vertex.circle, vertex.txt);
            vertex.circle.toFront();
            System.out.println("created vertex");

        }

    }

    private boolean createOnCorrectPlace(MouseEvent event)
    {
        for (Vertex vertex: buttons) {
            int fromCenterToEvent = (int)sqrt((event.getX() - vertex.circle.getX()-sizeof2) * (event.getX() - vertex.circle.getX()-sizeof2)
                    + (event.getY() - vertex.circle.getY()-sizeof2) * (event.getY() - vertex.circle.getY()-sizeof2));
            if (fromCenterToEvent <= 40) {
                return false;
            }
        }
        return  true;
    }
    ////////////////////////////////////////////////
    public static Vertex[] vertexes = new Vertex[2];
    public static int countOfCheckedVertex = 0;
///////////////////////////////////////////////////
    public void oneMouseClick(MouseEvent event) throws IOException {
        boolean shouldContinue = true;
        checkButton(event);
        for (Vertex vertex : buttons)
        {
            if (vertex.isChecked)
            {
                if (countOfCheckedVertex != 0){
                    if(vertex.getNumber() != vertexes[0].getNumber()) {
                        vertexes[countOfCheckedVertex] = vertex;
                        countOfCheckedVertex++;
                    }
                }
                else{
                    vertexes[countOfCheckedVertex] = vertex;
                    countOfCheckedVertex++;
                }
            }
            if (countOfCheckedVertex == 2)
            {
                shouldContinue = checkEdge(event, vertexes);
                countOfCheckedVertex = setNormalView();
                if(shouldContinue) {
                    createEdge(vertexes, event);
                    vertexes[0]=null;
                    vertexes[1]=null;
                    vertexes = new Vertex[2];
                    break;
                }
            }
        }
    }

    private void createEdge(Vertex[] vertexes, MouseEvent event) throws IOException {
        System.out.println("Create edge");
        int weight = 0;
        if (fixedSizeType)
            callEdgeSizeWindow(event);
            weight = (int) sqrt(((vertexes[0].circle.getX() - vertexes[1].circle.getX()) * (vertexes[0].circle.getX() - vertexes[1].circle.getX()))
                    + ((vertexes[0].circle.getY() - vertexes[1].circle.getY()) * (vertexes[0].circle.getY() - vertexes[1].circle.getY())));
            Edge edge = new Edge(vertexes[0], vertexes[1], weight);
            edges.add(edge);
            // Media sound = new Media(new File("src/content/Edge.wav").toURI().toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(sound);
            // mediaPlayer.play();
            for (int i = 0; i < 3; i++)
                mainController.smth.activePane.getChildren().addAll(edge.allLine[i]);
            mainController.smth.activePane.getChildren().add(edges.get(edges.size() - 1).txtWeight);
        if (fixedSizeType)
             mainController.smth.activePane.getChildren().remove(edges.get(edges.size() - 1).txtWeight);
            edge.line.toBack();
            System.out.println(edge.getWeight());

    }

    private static int setNormalView()
    {
        for (Vertex vertex : buttons)
        {
            vertex.circle.setStyle("-fx-image:url('/content/drt1.png');");
            vertex.isChecked = false;
            countOfCheckedVertex = 0;
        }
        return 0;
    }

    private void checkButton (MouseEvent event) {
        for (Vertex vertex : buttons)
        {
            if (onCorrectPlace(event,vertex))
            {
                if (vertex.isChecked)
                {
                    vertex.circle.setStyle("-fx-image:url('/content/drt1.png');");
                    vertex.isChecked = false;
                }
                else
                {
                    vertex.circle.setStyle("-fx-image:url('/content/drt2.png');");
                    vertex.isChecked = true;
                }
            }

        }
    }

    private boolean onCorrectPlace(MouseEvent event,Vertex vertex)
    {
        boolean inX = event.getX() < vertex.circle.getX() + 40 && event.getX() > vertex.circle.getX() + 8;
        boolean inY = event.getY() < vertex.circle.getY() + 40 && event.getY() > vertex.circle.getY() + 8;
        return inX&&inY;
    }

    private boolean checkEdge(MouseEvent event, Vertex[] vertexes) {
        boolean shouldContinue = true;
        for (int i = 0; i < edges.size(); i++)
        {
            if (twoVertexChecked(i,vertexes))
            {
                if ((!edges.get(i).isChecked) && shpEdgesCounter == 0)
                {
                // mainController.edges.get(i).v2.showPath(vec.get(i));
                    //mainController.smth.activePane.getChildren().addAll(mainController.edges.get(i).v2.path);
                    edges.get(i).line.setStyle("-fx-stroke-width:4;-fx-stroke:red");
                    edges.get(i).drawAllLines("red",3);
                    vertexesToFront();
                    //edges.get(i).line.setStyle("-fx-stroke:red");
                    System.out.println("Weight of this edge = " + edges.get(i).getWeight());
                    shpEdgesCounter++;
                    System.out.println(shpEdgesCounter);
                    edges.get(i).isChecked = true;
                }
                else
                {
                    edges.get(i).line.setStyle("-fx-stroke-width:2;-fx-stroke:black");
                    edges.get(i).drawAllLines("black", 2);
                    shpEdgesCounter--;
                    vertexesToFront();
                    edges.get(i).isChecked = false;
                }
                shouldContinue = false;
            }
        }
        return shouldContinue;
    }

    private boolean twoVertexChecked(int i,Vertex[] vertexes)
    {
        return (edges.get(i).getV1().equals(vertexes[0])&&edges.get(i).getV2().equals(vertexes[1]));
    }

    public void buildGraph(ActionEvent actionEvent) {
        fixedSizeType = false;
        generationType = false;
         if (!mainController.smth.shouldGenerate)
             mainController.smth.shouldBuild = true;
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
        if (Vertex.i > 0) {
            if (mainController.smth.GotovoBut.isDisable())
                mainController.smth.GotovoBut.setDisable(false);
        }
        if (!mainController.smth.ResultBut.isDisable())
            mainController.smth.ResultBut.setDisable(true);
        if (!mainController.smth.FatherBut.isDisable())
            mainController.smth.FatherBut.setDisable(true);
        if (!mainController.smth.StepBut.isDisable())
            mainController.smth.StepBut.setDisable(true);
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).line.setStyle("-fx-stroke:black;-fx-stroke-width:2");
            edges.get(i).drawAllLines("black", 2);
        }
        if (generationType) {
            refreshBut(actionEvent);
            generationType = false;
            shouldBuild = true;
            if (!mainController.smth.AddArmBut.isDisable())
                mainController.smth.AddArmBut.setDisable(true);
            if (!mainController.smth.RandGenBut.isDisable())
                mainController.smth.RandGenBut.setDisable(true);
            if (!mainController.smth.FixedEdgesBut.isDisable())
                mainController.smth.FixedEdgesBut.setDisable(true);
        }
    }

    public void tryToBuild(MouseEvent event) throws IOException {
        if (shouldBuild && !nextStep) {
            if (Vertex.i > 0) {
                if (mainController.smth.GotovoBut.isDisable())
                    mainController.smth.GotovoBut.setDisable(false);
            }
            else
                mainController.smth.GotovoBut.setDisable(true);
            createButton(event);
            System.out.println("shouldBuild = true!");
        }
        else
        if (fordInWork)
        {
            if (!mainController.smth.DelBut.isDisable())
                mainController.smth.DelBut.setDisable(true);
            if (!mainController.smth.ClearBut.isDisable())
                mainController.smth.ClearBut.setDisable(true);
            if (!mainController.smth.AddArmBut.isDisable())
                mainController.smth.AddArmBut.setDisable(true);
            if (!mainController.smth.RandGenBut.isDisable())
                mainController.smth.RandGenBut.setDisable(true);
            if (!mainController.smth.FixedEdgesBut.isDisable())
                mainController.smth.FixedEdgesBut.setDisable(true);
            if (Vertex.i > 0) {
                if (!mainController.smth.GotovoBut.isDisable())
                    mainController.smth.GotovoBut.setDisable(true);
            }
            if (!mainController.smth.ResultBut.isDisable())
                mainController.smth.ResultBut.setDisable(true);
            if (!mainController.smth.FatherBut.isDisable())
                mainController.smth.FatherBut.setDisable(true);
            if (mainController.smth.StepBut.isDisable())
                mainController.smth.StepBut.setDisable(false);
            if (mainController.smth.ResultBut.isDisable())
                mainController.smth.ResultBut.setDisable(false);
            checkStartVert(event);

        }
        else
            System.out.println("shouldBuild = false!");
    }

    public void endBuilding(ActionEvent actionEvent) {
        shouldBuild = false;
        fixedSizeType = false;
        if (!mainController.smth.DelBut.isDisable())
            mainController.smth.DelBut.setDisable(true);
        if (!mainController.smth.ClearBut.isDisable())
            mainController.smth.ClearBut.setDisable(true);
        if (!mainController.smth.AddArmBut.isDisable())
            mainController.smth.AddArmBut.setDisable(true);
        if (!mainController.smth.RandGenBut.isDisable())
            mainController.smth.RandGenBut.setDisable(true);
        if (!mainController.smth.FixedEdgesBut.isDisable())
            mainController.smth.FixedEdgesBut.setDisable(true);
        if (!mainController.smth.GotovoBut.isDisable())
            mainController.smth.GotovoBut.setDisable(true);
        if (mainController.smth.FatherBut.isDisable())
            mainController.smth.FatherBut.setDisable(false);
        if (!mainController.smth.StepBut.isDisable())
            mainController.smth.StepBut.setDisable(true);
        if (!mainController.smth.ResultBut.isDisable())
            mainController.smth.ResultBut.setDisable(true);
    }


    public void generateGraph(ActionEvent actionEvent) throws Exception {

        if (!(shouldBuild || fordInWork))
        shouldGenerate = true;
        if (shouldGenerate) {
                generationDialogController.vertexNumber = 0;
              refreshBut(actionEvent);
            generation(actionEvent);
            shouldGenerate = false;
            if (!DelBut.isDisable())
                DelBut.setDisable(true);
            if (!ClearBut.isDisable())
                ClearBut.setDisable(true);
            if (AddArmBut.isDisable())
                AddArmBut.setDisable(true);
            if (RandGenBut.isDisable())
                RandGenBut.setDisable(true);
            if (FixedEdgesBut.isDisable())
                FixedEdgesBut.setDisable(true);
            if (!GotovoBut.isDisable())
                GotovoBut.setDisable(true);
            if (!FatherBut.isDisable())
                FatherBut.setDisable(true);
            if (!StepBut.isDisable())
                StepBut.setDisable(true);
            if (!ResultBut.isDisable())
                ResultBut.setDisable(true);
            System.out.println("shouldGenerate = true!");
        }
        else
            System.out.println("shouldGenerate = false!");
    }
    public void generation(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        Parent panel = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/generationDialog.fxml"));
        stage.setTitle("введите количество вершин");
        stage.setMinWidth(200);
        stage.setMinHeight(150);
        stage.setResizable(false);
        stage.setScene(new Scene(panel));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
        //graphGenerationRandom();
    }


    public void fordInWork(ActionEvent actionEvent) {
        if (!shouldGenerate && !shouldBuild && !nextStep) {
            setNormalView();
            fordInWork = true;
            //generationType = false;
            if (!mainController.smth.ResultBut.isDisable())
                mainController.smth.ResultBut.setDisable(true);
            mainController.smth.FatherBut.setDisable(true);
        }
    }

    public void checkStartVert(MouseEvent event) {
        if (fordInWork && !nextStep && buttons.size() > 0) {
           /* for(int y = 0; y < buttons.size(); y++) {
                if (mainController.edges.get(y).v2 != startVert) {
                    mainController.edges.get(y).v2.showPath(INFINITE);
                    mainController.smth.activePane.getChildren().addAll(mainController.edges.get(y).v2.path);
                   mainController.edges.get(y).v1.showPath(INFINITE);
                    mainController.smth.activePane.getChildren().addAll(mainController.edges.get(y).v1.path);
                }
            }*/
            mainController.smth.StepBut.setDisable(true);
            mainController.smth.ResultBut.setDisable(true);
            checkButton(event);
            for (Vertex vertex : buttons) {
                if (vertex.isChecked) {
                    mainController.smth.StepBut.setDisable(false);
                    mainController.smth.ResultBut.setDisable(false);
                    //double INFINITE = 10000;
                    startVert = vertex;
                   // for(int y = 0; y < buttons.size(); y++) {
                        //if (mainController.edges.get(y).v2 != startVert) {
                            //Text txtic = new Text(this.circle.getX() + 8, this.circle.getY() + 8,"" + getNumber());
                            //mainController.edges.get(y).v2.showPath(INFINITE);
                            //Text x = new Text (mainController.edges.get(y).v1.path.getX(), mainController.edges.get(y).v1.path.getY(), "INF");
                            //mainController.edges.get(y).v1.path.getX();
                            //mainController.smth.activePane.getChildren().addAll(x);
                            //mainController.edges.get(y).v1.showPath(INFINITE);
                            //mainController.smth.activePane.getChildren().addAll(mainController.edges.get(y).v1.path);
                       // }
                  //  }
                    /*for(int y = 0; y < buttons.size(); y++) {
                        if(mainController.edges.get(y).v2 != startVert) {
                            mainController.edges.get(y).v2.showPath(INFINITE);
                            mainController.smth.activePane.getChildren().addAll(mainController.edges.get(y).v2.path);
                        }
                    }*/
                    for (int i = 0; i < Vertex.i; i++) {
                        vec.add(i, INFINITE);
                        p.add(i,-1);
                     //   mainController.edges.get(i).v2.showPath(INFINITE);
                       // mainController.smth.activePane.getChildren().addAll(mainController.edges.get(i).v2.path);

                    }
                    vec.set(vertex.getNumber(), 0.0);
                    //cycleFord(vertex.getNumber(), Vertex.i, vec);
                    //System.out.println("Ford for " + vertex.getNumber() + " was done!");
                    break;
                }
            }
            mainController.smth.DelBut.setDisable(true);
            mainController.smth.ClearBut.setDisable(true);
            mainController.smth.AddArmBut.setDisable(true);
            mainController.smth.RandGenBut.setDisable(true);
            mainController.smth.FixedEdgesBut.setDisable(true);
            mainController.smth.GotovoBut.setDisable(true);
            mainController.smth.FatherBut.setDisable(true);
            //ResultBut.setDisable(false);
           // StepBut.setDisable(true);
        }
        //fordInWork=false;
    }


    public void nextStep(ActionEvent actionEvent) throws IOException {

        if (fordInWork) {
            nextStep = true;
           // StepBut.setDisable(true);
            System.out.println("calling cycleFord");
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).line.setStyle("-fx-stroke:black");
                edges.get(i).drawAllLines("black",2);
            }
            cycleFord(startVert.getNumber(), Vertex.i, vec);

            if (last) {
                for (int i = 0; i < edges.size(); i++) {
                    edges.get(i).line.setStyle("-fx-stroke:black");
                    edges.get(i).drawAllLines("black",2);
                }
                ArrayList<Integer> path = new ArrayList<Integer>();
                for (int i = 0; i < Vertex.i; i++) {
                    if (startVert.getNumber() != i) {
                        for (int cur = i; cur != -1; cur = p.get(cur)) {
                            path.add(cur);
                        }
                        reverse(path);
                        for (int j = 0; j < path.size() - 1; j++) {
                            edges.get(findEdge(path.get(j), path.get(j + 1))).line.setStyle("-fx-stroke:red;-fx-stroke-width:4");
                            edges.get(findEdge(path.get(j), path.get(j + 1))).line.toFront();
                            vertexesToFront();
                            edges.get(findEdge(path.get(j), path.get(j + 1))).drawAllLines("red", 2);
                        }
                        path.clear();
                    }
                }
                path.clear();
                p.clear();
                nextStep = false;
                fordInWork = false;
                last = false;
                counter = 0;
                mainController.smth.AddArmBut.setDisable(false);
                mainController.smth.RandGenBut.setDisable(false);
                mainController.smth.FixedEdgesBut.setDisable(false);
                mainController.smth. ResultBut.setDisable(true);
                mainController.smth.StepBut.setDisable(true);
                startVert.circle.setStyle("-fx-image:url('/content/drt1.png');");
                startVert.isChecked = false;
                cycleCounter = 0;
                vec.clear();
            }
        }
    }

    public void vertexesToFront () {
        for (Vertex vertex: buttons) {
            vertex.circle.toFront();
        }
    }

    public int findEdge (int v1, int v2) {
        for (int i = 0; i < edges.size(); i++) {
            if ((edges.get(i).v1.getNumber() == v1) && (edges.get(i).v2.getNumber() == v2)) {
                return i;
            }

        }
        return 0;
    }
    public void cycleFord (int s, int n, ArrayList<Double> vec) throws IOException
    {
        if(fordInWork && !cycle)
        {
            if (!last) {
                if (counter < edges.size())
                    algFord(s, n, counter++, vec);
                else {
                    relaxCounter = 0;
                    counter = 0;
                    cycleCounter++;
                    System.out.println(cycleCounter + " - cycleCounter");
                    cycle = true;
                }
            }
        }
        if (fordInWork && cycle) {
                last = true;
                if (counter < edges.size()) {
                    algFord(s, n, counter++, vec);
                    cycle = true;
                    last = false;
                }
                else {
                    if (relaxCounter != 0)
                        last = false;
                    else
                    last = true;
                    relaxCounter = 0;
                    counter = 0;
                    cycleCounter++;
                    cycle = false;
                }
        }
    }

    public void reverse(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() / 2; i++) {
            int tmp = list.get(i);
            list.set(i,list.get(list.size() - i - 1));
            list.set(list.size() - i - 1, tmp);
        }
    }

    public void algFord(int s,int n, int i, ArrayList<Double> vec) throws IOException {
       // double INFINITE = 10000;
                if (vec.get(mainController.edges.get(i).v1.getNumber()) < INFINITE) {

                    if (vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight() < vec.get(mainController.edges.get(i).v2.getNumber())) {
                        Rectangle r = new Rectangle();
                        r.setFill(Color.LIGHTSTEELBLUE);
                        r.setX((mainController.edges.get(i).v2.circle.getX())+40);
                        r.setY((mainController.edges.get(i).v2.circle.getY()));
                        r.setWidth(42);
                        r.setHeight(10);
                        mainController.smth.activePane.getChildren().add(r);
                       mainController.edges.get(i).v2.showPath(vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                       mainController.smth.activePane.getChildren().addAll(mainController.edges.get(i).v2.path);
                        edges.get(i).line.setStyle("-fx-stroke:green");
                        edges.get(i).drawAllLines("green", 2);
                        edges.get(i).line.toFront();
                        vertexesToFront();
                        vec.set((mainController.edges.get(i).v2.getNumber()), vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                        p.set(edges.get(i).v2.getNumber(), edges.get(i).v1.getNumber());
                        last = false;
                        relaxCounter++;
                        cycle = false;
                    }
                    else {
                        /*Rectangle r = new Rectangle();
                        r.setFill(Color.LIGHTSTEELBLUE);
                        r.setX((mainController.edges.get(i).v2.circle.getX())+40);
                        r.setY((mainController.edges.get(i).v2.circle.getY()));
                        r.setWidth(42);
                        r.setHeight(10);
                        mainController.smth.activePane.getChildren().add(r);
                        mainController.edges.get(i).v2.showPath(vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                        mainController.smth.activePane.getChildren().addAll(mainController.edges.get(i).v2.path);*/
                        //if(mainController.edges.get(y).v2 != startVert) {
                        edges.get(i).line.setStyle("-fx-stroke:#2831ff");
                        edges.get(i).line.toFront();
                        vertexesToFront();
                        edges.get(i).drawAllLines("blue", 2);
                        cycle = false;
                    }

                    for (int y = 0; y < n; y++)
                    {

                        System.out.println(s + "->" + y + ":" + (vec.get(y)!= INFINITE ?(vec.get(y)): " none"));
                    }
                }
        else {
                    /*Rectangle r = new Rectangle();
                    r.setFill(Color.LIGHTSTEELBLUE);
                    r.setX((mainController.edges.get(i).v2.circle.getX())+40);
                    r.setY((mainController.edges.get(i).v2.circle.getY()));
                    r.setWidth(42);
                    r.setHeight(10);
                    mainController.smth.activePane.getChildren().add(r);
                  mainController.edges.get(i).v2.showPath(vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                   mainController.smth.activePane.getChildren().addAll(mainController.edges.get(i).v2.path);*/
                    edges.get(i).line.setStyle("-fx-stroke:orange");
                    edges.get(i).line.toFront();
                    vertexesToFront();
                    edges.get(i).drawAllLines("orange", 2);
                    //last = false;
                    cycle = false;
                }

    }

    public void deleteButton(ActionEvent actionEvent) {
        if(buttons.size()==0){
            System.out.println("No Vertexes!");
            return;
        }
        deleteVertex();
        deleteEdge();
    }

    private void deleteVertex()
    {
        int deletedNumb = 0;
        for(int i=0; i<buttons.size();i++)
        {
            deletedNumb = 0;
            if(buttons.get(i).isChecked)
            {
                setNormalView();
                deletedNumb = i;
                mainController.smth.activePane.getChildren().remove(buttons.get(i).circle);
                buttons.get(i).isChecked = false;
                for (int j = edges.size()-1; j >= 0; j--)
                {
                    if (edges.get(j).getV1().equals(buttons.get(i)) || edges.get(j).getV2().equals(buttons.get(i)))
                    {
                        mainController.smth.activePane.getChildren().removeAll(edges.get(j).allLine);
                        mainController.smth.activePane.getChildren().remove(buttons.get(i).txt);
                        mainController.smth.activePane.getChildren().removeAll(edges.get(j).txtWeight);
                        edges.remove(edges.get(j));

                    }
                }
                mainController.smth.activePane.getChildren().remove(buttons.get(i).txt);
                setNormalView();
                buttons.remove(buttons.get(i));
            }
        }
        Vertex.i--;
        if (Vertex.i > 0) {
            for (int i = buttons.size(); i > deletedNumb; i--) {
                mainController.smth.activePane.getChildren().remove(buttons.get(i-1).txt);
                buttons.get(i-1).setNumberAfterDelete(i-1);
                buttons.get(i-1).setNumber();
                mainController.smth.activePane.getChildren().remove(buttons.get(i-1).txt);
                mainController.smth.activePane.getChildren().add(buttons.get(i-1).txt);
            }
        }
    }

    public static void deleteEdge() {
        for(int i=edges.size()-1; i>=0;i--)
        {
            if(edges.get(i).isChecked)
            {
                mainController.smth.activePane.getChildren().removeAll(edges.get(i).allLine);
                mainController.smth.activePane.getChildren().removeAll(edges.get(i).txtWeight);
                edges.remove(edges.get(i));
                setNormalView();
            }
        }
        shpEdgesCounter=0;
    }

    public void refreshBut(ActionEvent actionEvent) {
        activePane.getChildren().clear();
        if (!DelBut.isDisable())
            DelBut.setDisable(true);
        if (!ClearBut.isDisable())
           ClearBut.setDisable(true);
        if (AddArmBut.isDisable())
            AddArmBut.setDisable(false);
        if (RandGenBut.isDisable())
           RandGenBut.setDisable(false);
        if (FixedEdgesBut.isDisable())
            FixedEdgesBut.setDisable(false);
        if (!GotovoBut.isDisable())
            GotovoBut.setDisable(true);
        if (!FatherBut.isDisable())
            FatherBut.setDisable(true);
        if (!StepBut.isDisable())
            StepBut.setDisable(true);
        if (!ResultBut.isDisable())
            ResultBut.setDisable(true);
        shouldBuild = false;
        shouldGenerate = false;
        nextStep = false;
        //myGridPane.getChildren().remove(imageSadCat);
        edges.clear();
        buttons.clear();
        shpEdgesCounter=0;
        countOfCheckedVertex = 0;
        generationType = false;
        fixedSizeType = false;
        Vertex.i = 0;
    }


    private Vertex checkButtonToReplace (MouseEvent event) {
        for (Vertex vertex : buttons)
        {
            if (onCorrectPlace(event,vertex)) {

                pressed = true;
                return vertex;
            }

        }
        return null;
    }


    public void changeVertPlace(Event event) throws IOException {
        if (shouldBuild) {
            MouseEvent mouseEvent = (MouseEvent) event;
            Vertex currentVertex = checkButtonToReplace(mouseEvent);
            if (currentVertex != null) {
                currentVertex.circle.setX(mouseEvent.getX() - sizeof2);
                currentVertex.circle.setY(mouseEvent.getY() - sizeof2);
                currentVertex.syncronize();

                for (int i = 0; i < edges.size(); i++) {
                    if (edges.get(i).getV1().circle.getX() == currentVertex.circle.getX() && edges.get(i).getV1().circle.getY() == currentVertex.circle.getY()) {
                        edges.get(i).syncronize(currentVertex, edges.get(i).getV2(),generationType);
                        System.out.println("Edge" + i + "was syncronized");
                        System.out.println(edges.get(i).getV1().circle.getX() + " = x1, " + edges.get(i).getV2().circle.getX() + " = x2"
                        + " "+ edges.get(i).getWeight());
                    }
                    else {
                        if (edges.get(i).getV2().circle.getX() == currentVertex.circle.getX() && edges.get(i).getV2().circle.getY() == currentVertex.circle.getY()) {
                            edges.get(i).syncronize(edges.get(i).getV1(), currentVertex, generationType);
                            System.out.println("Edge" + i + "was syncronized");
                            System.out.println(edges.get(i).getV1().circle.getX() + " = x1, " + edges.get(i).getV2().circle.getX() + " = x2"
                                    + " " + edges.get(i).getWeight());
                        }
                    }
                    System.out.println("numb of edges = " + edges.size());
                }
            }
        }
    }

    public static void graphGenerationRandom () {
        int vertexNumber = generationDialogController.vertexNumber;
        double[][] genMatrix = new double[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; i++) {
            for (int j = 0; j < vertexNumber; j++) {
                int resolution = (int) (Math.random() * 8);
                if (resolution > 5) {
                    genMatrix[i][j] = Math.random()*99 + 1;
                }
                else
                    genMatrix[i][j] = 0;
            }
            Vertex vertex = generationDialogController.smth.vertGeneration();
            mainController.smth.activePane.getChildren().addAll(vertex.circle,vertex.txt);
            vertex.circle.toFront();
            buttons.add(vertex);
        }
        int currentEdge = 0;
        for (int i = 0; i < vertexNumber; i++) {
            for (int j = 0; j < vertexNumber; j++) {
                if (genMatrix[i][j] != 0 && i != j) {
                    Edge edge = new Edge(buttons.get(i), buttons.get(j),genMatrix[i][j]);
                    //Edge edge = generationDialogController.edgeGeneration(buttons.get(i), buttons.get(j),genMatrix[i][j]);
                    edges.add(edge);
                    currentEdge++;
                    //activePane.getChildren().add(edge.line);
                    for (int k = 0; k < 3; k++)
                    mainController.smth.activePane.getChildren().add(edge.allLine[k]);
                    mainController.smth.activePane.getChildren().add(edges.get(currentEdge - 1).txtWeight);
                    edge.line.toBack();
                }
            }
        }

    }

    public void fastResult(ActionEvent actionEvent) {
        algorithFastResult(startVert.getNumber(), Vertex.i);

    }

    public void algorithFastResult (int s, int n) {
     //   double INFINITE = 10000;
        int o=0;
        while (true) {
            o++;
            boolean last = true;
            for (int i = 0; i < edges.size(); i++) {
                if (vec.get(mainController.edges.get(i).v1.getNumber()) < INFINITE) {

                    if (vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight() < vec.get(mainController.edges.get(i).v2.getNumber())) {
                        vec.set((mainController.edges.get(i).v2.getNumber()), vec.get(mainController.edges.get(i).v1.getNumber()) + mainController.edges.get(i).getWeight());
                        p.set(edges.get(i).v2.getNumber(), edges.get(i).v1.getNumber());
                       /* Rectangle r = new Rectangle();
                        r.setFill(Color.BLUE);
                        r.setX((mainController.edges.get(i).v1.circle.getX()));
                        r.setY((mainController.edges.get(i).v1.circle.getX()));
                        r.setWidth(20);
                        r.setHeight(10);
                        mainController.smth.activePane.getChildren().add(r);
                        mainController.edges.get(i).v1.showPath(vec.get(i));
                        mainController.smth.activePane.getChildren().addAll(mainController.edges.get(i).v1.path);*/
                        last = false;
                    }
                }
            }
            if(o > n) {
                Rectangle r = new Rectangle();
                r.setX(50);
                r.setY(50);
                r.setWidth(200);
                r.setHeight(100);
                Text txte = new Text(100, 100,"Цикл!!!!1");
                txte.setFill(Color.RED);
                mainController.smth.activePane.getChildren().addAll(r, txte);
                break;
            }
            if (last)
                break;
        }

        for (int y = 0; y < n; y++)
        {
            System.out.println(s + "->" + y + ":" + (vec.get(y)!= INFINITE ?(vec.get(y)): " none"));
        }

        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).line.setStyle("-fx-stroke:black");
            edges.get(i).drawAllLines("black",2);
        }
        ArrayList<Integer> path = new ArrayList<Integer>();
        for (int i = 0; i < Vertex.i; i++) {
            if (startVert.getNumber() != i) {
                for (int cur = i; cur != -1; cur = p.get(cur)) {
                    path.add(cur);
                }
                reverse(path);
                for (int j = 0; j < path.size() - 1; j++){
                    /*(Rectangle r = new Rectangle();
                    r.setFill(Color.LIGHTSTEELBLUE);
                    r.setX((mainController.edges.get(i).v1.circle.getX())+40);
                    r.setY((mainController.edges.get(i).v1.circle.getY()));
                    r.setWidth(38);
                    r.setHeight(13);
                    mainController.smth.activePane.getChildren().add(r);*/
                    edges.get(findEdge(path.get(j),path.get(j+1))).line.setStyle("-fx-stroke:red;-fx-stroke-width:4");
                    edges.get(findEdge(path.get(j),path.get(j+1))).line.toFront();
                    vertexesToFront();
                    edges.get(findEdge(path.get(j), path.get(j+1))).drawAllLines("red",2);
                }
                path.clear();
            }
        }
        path.clear();
        p.clear();
        nextStep = false;
        fordInWork = false;
        last = false;
        counter = 0;
        mainController.smth.AddArmBut.setDisable(false);
        mainController.smth.RandGenBut.setDisable(false);
        mainController.smth.FixedEdgesBut.setDisable(false);
        mainController.smth.ResultBut.setDisable(true);
        mainController.smth.StepBut.setDisable(true);
        startVert.circle.setStyle("-fx-image:url('/content/drt1.png');");
        startVert.isChecked = false;
        cycleCounter = 0;
        vec.clear();
    }

    public void fixedEdges(ActionEvent actionEvent) throws IOException{//
     //   refreshBut(actionEvent);
        shouldBuild = true;
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
        if (Vertex.i > 0) {
            if (mainController.smth.GotovoBut.isDisable())
                mainController.smth.GotovoBut.setDisable(false);
        }
        if (!mainController.smth.ResultBut.isDisable())
            mainController.smth.ResultBut.setDisable(true);
        if (!mainController.smth.FatherBut.isDisable())
            mainController.smth.FatherBut.setDisable(true);
        if (!mainController.smth.StepBut.isDisable())
            mainController.smth.StepBut.setDisable(true);
        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).line.setStyle("-fx-stroke:black;-fx-stroke-width:2");
            edges.get(i).drawAllLines("black", 2);
        }
        if (generationType) {
            //refreshBut(actionEvent);
            generationType = false;
            shouldBuild = true;
            if (!mainController.smth.AddArmBut.isDisable())
                mainController.smth.AddArmBut.setDisable(true);
            if (!mainController.smth.RandGenBut.isDisable())
                mainController.smth.RandGenBut.setDisable(true);
            if (!mainController.smth.FixedEdgesBut.isDisable())
                mainController.smth.FixedEdgesBut.setDisable(true);
        }
        fixedSizeType = true;
        generationType = true;
    }

    public void callEdgeSizeWindow (Event actionEvent)throws IOException{
        Stage stage = new Stage();
        Parent panel = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/edgeSizeDialog.fxml"));
        stage.setTitle("введите вес ребра");
        stage.setMinWidth(200);
        stage.setMinHeight(150);
        stage.setResizable(false);
        stage.setScene(new Scene(panel));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                event.consume();

            }

        });
    }
}



