/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jeffrey Gan
 */
public class GraphPane extends AnchorPane{
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<Number, Number> chart = new LineChart(xAxis, yAxis);
    
    private XYChart.Series positionData0 = new XYChart.Series<>();
    private XYChart.Series positionData1 = new XYChart.Series<>();
    private XYChart.Series stopData = new XYChart.Series<>();
    
    private FieldPane fieldLines = new FieldPane();
    
    GraphPane() {
        
        this.setPrefSize(790.0, 390.0);
        this.setMaxSize(790.0, 390.0);
        this.setStyle("-fx-background-color: gainsboro; -fx-font-size: 15;");
        this.setPadding(new Insets(10));
        
        AnchorPane.setTopAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 0.0);
        AnchorPane.setBottomAnchor(chart, 10.0);
        AnchorPane.setLeftAnchor(chart, 10.0);
        
        chart.getData().addAll(positionData0, positionData1, stopData);
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);
        positionData0.getNode().setStyle("-fx-stroke: blue;");
        positionData1.getNode().setStyle("-fx-stroke: blue;");
        stopData.getNode().setStyle("-fx-stroke: red; -fx-stroke-width: 10;");
        
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
                
        Label xLabel = new Label("x");
        AnchorPane.setRightAnchor(xLabel, 10.0);
        AnchorPane.setBottomAnchor(xLabel, 0.0);
        
        Label yLabel = new Label("y");
        AnchorPane.setLeftAnchor(yLabel, 0.0);
        AnchorPane.setTopAnchor(yLabel, 0.0);
        
        AnchorPane.setTopAnchor(fieldLines, 15.0);
        AnchorPane.setRightAnchor(fieldLines, 14.0);
        

        
        this.getChildren().addAll(chart, xLabel, yLabel, fieldLines);
    }
    
    public void clearData(){
        this.positionData0.getData().clear();
        this.positionData1.getData().clear();
        this.stopData.getData().clear();
    }
    public void addPositionData0(Double x, Double y){
        this.positionData0.getData().add(new XYChart.Data(x, y));
    }
    public void addPositionData1(Double x, Double y){
        this.positionData1.getData().add(new XYChart.Data(x, y));
    }
    
    public void addBoundaryLine(boolean isVertical, Double pos, Double min, Double max){
        if(isVertical){
            this.stopData.getData().add(new XYChart.Data(pos, min-10));
            this.stopData.getData().add(new XYChart.Data(pos, max+10));
            
        }
        else{
            this.stopData.getData().add(new XYChart.Data(min-10, pos));
            this.stopData.getData().add(new XYChart.Data(max+10, pos));
        }
    }
    public void setFieldDirection(String dir){
        this.fieldLines.setFieldDirection(dir);
    }
    
    private class FieldPane extends AnchorPane{
        private final int width = 698;
        private final int height = 303;
        private Pane hPane = new Pane();
        private Pane vPane = new Pane();
        private ArrayList<Arrow> arrows = new ArrayList(6);
        
        private FieldPane() {
            super();
            this.setPrefSize(width, height);
            this.setMaxSize(width, height);
            
            double h = height/4.0;
            for(int i=1;i<4;i+=2){
                hPane.getChildren().add(new Line(0, i*h, width, i*h));
            }
            
            double w = width/6.0;
            for(int i=1;i<6;i+=2){
                vPane.getChildren().add(new Line(i*w, 0, i*w, height));
            }
            
            for(int y=1;y<4;y+=2){
                for(int x=1;x<6;x+=2){
                    arrows.add(new Arrow(x*w, y*h));
                }
            }
            
            hPane.setVisible(false);
            vPane.setVisible(false);
            setArrowsVisibility(false);
            
            this.getChildren().addAll(hPane, vPane);
            for(Arrow a: arrows){
                this.getChildren().add(a);
            }
            
        }
        private void setFieldDirection(String dir){
            switch(dir){
                case "up":
                    this.vPane.setVisible(true);
                    this.hPane.setVisible(false);
                    setArrowsVisibility(true);
                    setArrowsDirection(0);
                    break;
                    
                case "right":
                    this.vPane.setVisible(false);
                    this.hPane.setVisible(true);
                    setArrowsVisibility(true);
                    setArrowsDirection(90);
                    break;
                    
                case "down":
                    this.vPane.setVisible(true);
                    this.hPane.setVisible(false);
                    setArrowsVisibility(true);
                    setArrowsDirection(180);
                    break;
                    
                case "left":
                    this.vPane.setVisible(false);
                    this.hPane.setVisible(true);
                    setArrowsVisibility(true);
                    setArrowsDirection(270);
                    break;
                
                default:
                    this.vPane.setVisible(false);
                    this.hPane.setVisible(false);
                    setArrowsVisibility(false);
                    setArrowsDirection(0);
                    break;
            }
        }
        
        private void setArrowsVisibility(boolean value){
            for(Arrow a: arrows){
                a.setVisible(value);
            }
        }
        private void setArrowsDirection(double deg){
            for (Arrow arrow : arrows) {
                arrow.setRotation(deg);
            }
        }
        
        
        private class Arrow extends Polygon{
            private Double x, y;
            
            private Arrow(Double x, Double y){
                super(
                    x,y,
                    x-6,y+12,
                    x+6,y+12
                );
                this.x = x;
                this.y = y;
                
            }
            private void setRotation(double deg){
                this.getTransforms().setAll(new Rotate(deg, x, y));
            }
        }
        
    }
}
