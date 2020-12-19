package World;

import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {
    public Pane root = new Pane();
    public Scene scene1 = new Scene(root,1600,25);
    public VBox cover = new VBox();
    public VBox cover2 = new VBox();
    public VBox text = new VBox();
    public VBox text2 = new VBox();
    public VBox chart1 = new VBox();
    public VBox chart2 = new VBox();
    public VBox statistics = new VBox();
    public VBox statistics2 = new VBox();
    public SimulationEngine engine1;
    public SimulationEngine engine2;
    public int b = 0;
    public long time = (int) 5e9;

    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Symulacja świata");
        Canvas canvas1 = new Canvas(600, 400);
        Canvas canvas2 = new Canvas(600, 400);
        GraphicsContext gc1 = canvas1.getGraphicsContext2D();
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        canvas1.relocate(0,25);
        canvas2.relocate(605,25);
        Scene startingScene = startingScene(stage);
        stage.setScene(startingScene);
        final Image zaba = new Image( "zaba.png",b,b,false,false );
        final Image jabko = new Image( "jabko.png",b,b,false,false );
        final Image jungle = new Image( "jungle.png",b,b,false,false );
        final Image step = new Image( "step.png",b,b,false,false );
        stage.setWidth(1216);
        stage.setHeight(1406);
        root.getChildren().add(canvas1);
        root.getChildren().add(canvas2);
        AnimationTimer timer = new AnimationTimer() {
           private long prevTime = 0;
            public void handle(long now) {
                long dt = now - prevTime;
                if(dt > time ){
                    gc1.clearRect(0, 0, 600,400);
                    engine1.run();
                    prevTime = now;
                    root.getChildren().remove(chart1);
                    root.getChildren().remove(statistics);
                    root.getChildren().remove(text);
                    cover = new VBox();
                    cover.setMinSize(600,1000);
                    cover.setStyle("-fx-background-color: #CED1D2");
                    root.getChildren().add(cover);
                    cover.relocate(0,425);
                    drawMap2(gc1,engine1.map,b,zaba,jabko,jungle,step);
                    statistics = Statistics(engine1);
                    chart1 = LineChart(engine1);
                    text = mapData(engine1);
                    root.getChildren().add(statistics);
                    root.getChildren().add(chart1);
                    root.getChildren().add(text);
                    text.relocate(100,425);
                    statistics.relocate(0,950);
                    chart1.relocate(0,550);
                }
            }

        };
        AnimationTimer timer2 = new AnimationTimer() {
            private long prevTime = 0;
            public void handle(long now) {
                long dt = now - prevTime;
                if(dt > time ){
                    engine2.run();
                    gc2.clearRect(0, 0, 600,400);
                    prevTime = now;
                    root.getChildren().remove(chart2);
                    root.getChildren().remove(statistics2);
                    root.getChildren().remove(text2);
                    cover2 = new VBox();
                    cover2.setMinSize(600,1000);
                    cover2.setStyle("-fx-background-color: #9CA2A4");
                    root.getChildren().add(cover2);
                    cover2.relocate(605,425);
                    drawMap2(gc2,engine2.map,b,zaba,jabko,jungle,step);
                    statistics2 = Statistics(engine2);
                    chart2 = LineChart(engine2);
                    text2 = mapData(engine2);
                    //root.getChildren().add(box2);
                    root.getChildren().add(statistics2);
                    root.getChildren().add(chart2);
                    root.getChildren().add(text2);
                    //box2.relocate(605,25);
                    text2.relocate(725,425);
                    statistics2.relocate(605,950);
                    chart2.relocate(605,550);
                }
            }

        };
        MenuBar menuBar = menu(timer,timer2);
        menuBar.relocate(0,0);
        root.getChildren().add(menuBar);
        stage.show();

    }

    public static VBox Statistics(SimulationEngine engine1) {
        TableView tableView = new TableView();
        tableView.setPrefSize( 600, 400 );
        TableColumn<Animal, String> column1 = new TableColumn<>("Animal position");
        column1.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Animal, String> column2 = new TableColumn<>("Animal Energy");
        column2.setCellValueFactory(new PropertyValueFactory<>("energy"));

        TableColumn<Animal, String> column3 = new TableColumn<>("Animal Age");
        column3.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Animal, String> column4 = new TableColumn<>("Animals Children");
        column4.setCellValueFactory(new PropertyValueFactory<>("childrenNumber"));

        TableColumn<Animal, String> column5 = new TableColumn<>("Animals Offsprings");
        column5.setCellValueFactory(new PropertyValueFactory<>("offspringNumber"));

        TableColumn<Animal, String> column6 = new TableColumn<>("Animals genome");
        column6.setCellValueFactory(new PropertyValueFactory<>("genomeList"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        for(Animal i : engine1.animals) {
            tableView.getItems().add(i);
        }
        VBox vbox = new VBox(tableView);

        return vbox;
    }

    public MenuBar menu(AnimationTimer timer1,AnimationTimer timer2) {
        Menu app = new Menu("App");
        Menu simulationControl1 = new Menu("Simulation 1 Control");
        Menu simulationControl2 = new Menu("Simulation 2 Control");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(app);
        menuBar.getMenus().add(simulationControl1);
        menuBar.getMenus().add(simulationControl2);
        MenuItem menuItem1 = new MenuItem("Close App");
        MenuItem menuItem2 = new MenuItem("Speed up");
        MenuItem menuItem3 = new MenuItem("Slow");
        MenuItem menuItem4 = new MenuItem("Stop both");
        MenuItem menuItem5 = new MenuItem("Start both");
        MenuItem menuItem6 = new MenuItem("Report");
        MenuItem menuItem7 = new MenuItem("Start");
        MenuItem menuItem8 = new MenuItem("Stop");
        MenuItem menuItem9 = new MenuItem("Start");
        MenuItem menuItem10 = new MenuItem("Stop");

        app.getItems().add(menuItem1);
        app.getItems().add(menuItem2);
        app.getItems().add(menuItem3);
        app.getItems().add(menuItem4);
        app.getItems().add(menuItem5);
        app.getItems().add(menuItem6);
        simulationControl1.getItems().add(menuItem7);
        simulationControl1.getItems().add(menuItem8);
        simulationControl2.getItems().add(menuItem9);
        simulationControl2.getItems().add(menuItem10);
        menuItem1.setOnAction(e -> {
            System.exit(0);
        });
        menuItem2.setOnAction(e -> {
            time = time / 2;
        });
        menuItem3.setOnAction(e -> {
            time = time * 2;
        });
        menuItem4.setOnAction(e -> {
            timer1.stop();
            timer2.stop();
        });
        menuItem5.setOnAction(e -> {
            timer1.start();
            timer2.start();
        });
        menuItem6.setOnAction(e -> {
            timer1.stop();
            timer2.stop();
            try {
                engine1.observer.createReport("report1.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                engine2.observer.createReport("report2.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(0);
        });
        menuItem7.setOnAction(e -> {
            timer1.start();
        });
        menuItem8.setOnAction(e -> {
            timer1.stop();
        });
        menuItem9.setOnAction(e -> {
            timer2.start();
        });
        menuItem10.setOnAction(e -> {
            timer2.stop();
        });
        return menuBar;
    }

    public static VBox LineChart(SimulationEngine engine1){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Age");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of animals");
        LineChart lineChart = new LineChart(xAxis, yAxis);
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Number of animal per Day");
        int a = engine1.animalsNumber.size()/50;
        a++;
        for(int i=0;i< engine1.age;i += a){
            dataSeries1.getData().add(new XYChart.Data( i, engine1.animalsNumber.get(i)));
        }
        lineChart.getData().add(dataSeries1);
        VBox vbox = new VBox(lineChart);
        return vbox;
    }

    public static VBox mapData(SimulationEngine engine){
        VBox text = new VBox();
        float a = 0;
        Text line1 = new Text();
        Text line2 = new Text();
        Text line3 = new Text();
        Text line4 = new Text();
        Text line5 = new Text();
        Text line6 = new Text();
        //Age
        a = engine.observer.getAge();
        line1.setText("Age: " + a);
        text.getChildren().add(line1);
        //Animals number
        a = engine.observer.getNumberOfAnimals();
        line2.setText("Animals: " + a);
        text.getChildren().add(line2);
        //Grasses number
        a = engine.observer.getNumberOfGrasses();
        line3.setText("Grasses: " + a);
        text.getChildren().add(line3);
        //Best genome
        ArrayList<Integer> bestG = engine.observer.getBestGenome();
        line4.setText("Best Genome: " + bestG);
        text.getChildren().add(line4);
        //Average energy
        a = engine.observer.averageEnergyOfAnimals();
        line5.setText("Average Energy: " + a);
        text.getChildren().add(line5);
        //Average Offsprings
        a = engine.observer.averageNumberOfChildren();
        line6.setText("Average Offsprings: " + a);
        text.getChildren().add(line6);
        return text;
    }

    public void drawMap2(GraphicsContext gc,RectangularMap map,int a,Image animal,Image grass,Image jungle,Image step) {
        for (int i = 0; i < map.height; i += 1) {
            for (int j = 0; j < map.width; j += 1) {
                Vector2d vector = new Vector2d(j, i);
                if (map.isOccupiedByAnimal(vector)) {
                    gc.drawImage(animal, j*a, a*map.height - (i+1)*a,a,a);
                } else if (map.isOccupiedByGrass(vector)) {
                    gc.drawImage(grass, j*a, a*map.height -(i+1)*a,a,a);
                } else if (map.jungle.isInJungle(vector)) {
                    gc.drawImage(jungle, j*a, a*map.height -(i+1)*a,a,a);
                } else {
                    gc.drawImage(step, j*a, a*map.height -(i+1)*a,a,a);
                }
            }
        }
    }

    public VBox startingData(Stage stage){
        TextField textField1 = new TextField();
        textField1.setMaxSize(300,20);
        TextField textField2 = new TextField();
        textField2.setMaxSize(300,20);
        TextField textField3 = new TextField();
        textField3.setMaxSize(300,20);
        TextField textField4 = new TextField();
        textField4.setMaxSize(300,20);
        TextField textField5 = new TextField();
        textField5.setMaxSize(300,20);
        TextField textField6 = new TextField();
        textField6.setMaxSize(300,20);
        TextField textField7 = new TextField();
        textField7.setMaxSize(300,20);
        Text info1 = new Text("Map Width:");
        Text info2 = new Text("Map Height:");
        Text info3 = new Text("Jungle Ratio:");
        Text info7 = new Text("Animal Energy:");
        Text info4 = new Text("Animals lost energy per day:");
        Text info5 = new Text("Plants Energy:");
        Text info6 = new Text("Start Number of Animals:");
        VBox textBox = new VBox();
        textBox.setMinSize(300,300);
        textBox.getChildren().add(info1);
        textBox.getChildren().add(textField1);
        textBox.getChildren().add(info2);
        textBox.getChildren().add(textField2);
        textBox.getChildren().add(info3);
        textBox.getChildren().add(textField3);
        textBox.getChildren().add(info7);
        textBox.getChildren().add(textField7);
        textBox.getChildren().add(info4);
        textBox.getChildren().add(textField4);
        textBox.getChildren().add(info5);
        textBox.getChildren().add(textField5);
        textBox.getChildren().add(info6);
        textBox.getChildren().add(textField6);
        Button button= new Button("Enter");
        textBox.getChildren().add(button);
        button.setOnAction(e ->
                {
                    stage.setScene(scene1);
                    String redW = textField1.getText();
                    String redH = textField2.getText();
                    String redRatio = textField3.getText();
                    String redAnimalEnergy = textField7.getText();
                    String redLostEnergy = textField4.getText();
                    String redPlantEnergy = textField5.getText();
                    String redStartAnimals = textField6.getText();
                    if(isInteger(redW) && isInteger(redH) && isInteger(redRatio) && isInteger(redLostEnergy) && isInteger(redPlantEnergy) && isInteger(redStartAnimals) && isInteger(redAnimalEnergy)){
                        int rW=Integer.parseInt(redW);
                        int rH=Integer.parseInt(redH);
                        int rR=Integer.parseInt(redRatio);
                        int rAE=Integer.parseInt(redAnimalEnergy);
                        int rLE=Integer.parseInt(redLostEnergy);
                        int rPE=Integer.parseInt(redPlantEnergy);
                        int rSA=Integer.parseInt(redStartAnimals);
                        if(rW <= 0 || rH <= 0 || rW*rH -2 < rSA || rPE <= 0 || rW/rR == 0 || rH/rR == 0){
                            System.out.println("wrong data");
                            System.exit(-1);
                        }
                        else{
                            engine1 = new SimulationEngine(rW,rH,rR,rAE,rLE,rPE,rSA);
                            engine2 = new SimulationEngine(rW,rH,rR,rAE,rLE,rPE,rSA);
                            int xMaxSize = 600/engine1.map.getWidth();
                            int yMaxSize = 400/engine1.map.getHeight();
                            if(xMaxSize > yMaxSize){
                                xMaxSize = yMaxSize;
                            }
                            b = xMaxSize;

                        }

                    }
                    else {
                        System.out.println("wrong data");
                        System.exit(-1);
                    }
                }
        );
        return textBox;
    }

    public Scene startingScene(Stage stage){
        Pane pane1 = new Pane();
        pane1.setMaxSize(300,300);
        VBox data = startingData(stage);
        pane1.getChildren().add(data);
        data.relocate(450,450);
        Scene startingScene = new Scene(pane1,300,300);
        return startingScene;

    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


}

