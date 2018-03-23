/*
 * Closest pari, D & C
 */
package closestpair;

import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Wang Zheng-Yu <zhengyuw@kth.se>
 */
public class ClosestPair extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        int n = 10;
        Point[] p = new Point[n];
        Random random = new Random();
        for(int i = 0; i < p.length; i ++) {
            double x = random.nextDouble() * 2 - 1;
            double y = random.nextDouble() * 2 - 1;
            Point point = new Point(x,y);
            p[i] = point;
            System.out.print(p[i].toString());
        }
        
        Model m = new Model();
        Pair closestPair = m.divideAndConquer(p);
        System.out.println("\nShortest: " + closestPair.toString());
        
         //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Horitzontal distance");
        yAxis.setLabel("Vertical height");
        
        // create scatter chart representing houses
        final ScatterXChart<Number,Number> sc = new
            ScatterXChart<>(xAxis,yAxis);
        xAxis.setLabel("x - coordinate");                
        yAxis.setLabel("y - coordinate");
        sc.setTitle("Points");
 
        for(int i= 0; i < p.length; i ++) {
            XYChart.Series scatteredPoints = new XYChart.Series();
            scatteredPoints.setName("Point " + i);
            scatteredPoints.getData().add(new XYChart.Data<>(p[i].getX(), p[i].getY()));
            sc.getData().add(scatteredPoints);
        }
        
        //creating the line chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<>(xAxis,yAxis);
                
        lineChart.setTitle("Closest Pair");
       
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Closest Pair");
        
        //populating the series with data
        series.getData().add(new XYChart.Data<>(closestPair.getPoint1().getX(), 
                closestPair.getPoint1().getY()));
        series.getData().add(new XYChart.Data<>(closestPair.getPoint2().getX(), 
                closestPair.getPoint2().getY()));
        
        lineChart.getData().add(series);
        
        Pane root = new StackPane();
        Button btn = new Button();
        btn.setText("closest pair!");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("closest pair!");
            if(root.getChildren().contains(sc)) {
                
                root.getChildren().remove(sc);
                root.getChildren().add(lineChart);
            }
        });
        
        root.getChildren().add(sc);
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 1200, 800);
        
        primaryStage.setTitle("Closest Pair problem by divid & conquer, NB42");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
