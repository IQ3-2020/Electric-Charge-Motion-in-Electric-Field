/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motionelectricfieldsimulation;

import java.util.Arrays;
import java.util.HashMap;
import view.SimWindow;

/**
 *
 * @author Jeffrey Gan
 * 
 * Creates the Data for the GraphPane
 */
public class GraphController {
    private SimWindow view;
    private HashMap<String,Double> valueMap;
    
    private Double minX = 0.0;
    private Double minY = 0.0;
    private Double maxX = 0.0;
    private Double maxY = 0.0;
    
    public GraphController(SimWindow view, HashMap<String,Double> valueMap){
        this.view = view;
        this.valueMap = valueMap;
    }
    //The group of functions that are called when the "CALCULATE" button is pressed.
    public void plotAll(){
        view.graph.clearData();
        plotPoints();
        plotBoundary();
        setFieldDirection();
    }
    //Generates the points representing the position of the charged particle during its trajectory
    private void plotPoints(){
        Double posX, posY, time;
        Double prevX = valueMap.get("initPosX");
        Double prevY = valueMap.get("initPosY");
        Double timeMax = valueMap.get("diffTime");
        boolean xflag = false;
        
        double[] xValues = new double[12];
        double[] yValues = new double[12];
        
        //If the value of time is undefined, get the trajectory for 100 seconds
        if(timeMax.isInfinite() || timeMax.isNaN()){
            timeMax = 100.0;
        }
        
        //The number of times to loop
        int limit = 10;
        
        //Generates the position data based on 2D kinematics
        for(int i=0; i<=limit; i++){ //Loops limit+1 times
            time = i*timeMax/(double)limit;
            
            posX = valueMap.get("initPosX")
                    + valueMap.get("initVelX")*time
                    + 0.5*valueMap.get("accelX")*time*time;
            
            posY = valueMap.get("initPosY")
                    + valueMap.get("initVelY")*time
                    + 0.5*valueMap.get("accelY")*time*time;
            
            
            if(posX.compareTo(prevX) < 0){
                view.graph.addPositionData1(posX, posY);
                if(!xflag){
                    view.graph.addPositionData1(prevX, prevY);
                    xflag = true;
                }
            }else{
                view.graph.addPositionData0(posX, posY);
            }
            
            prevX = posX;
            prevY = posY;
            
            xValues[i] = posX;
            yValues[i] = posY;
        }
        
        Arrays.sort(xValues);
        Arrays.sort(yValues);
        minX = xValues[0];
        maxX = xValues[limit];
        
        minY = yValues[0];
        maxY = yValues[limit];
        
    }
    //Sends values for the GraphPane to graph the line representing the final positon boundary.
    private void plotBoundary(){
        boolean isVbound = valueMap.get("isVbound").equals(1.0);
        Double max, min;
        if(isVbound){
            min = minY;
            max = maxY;
        }else{
            min = minX;
            max = maxX;
        }
        
        view.graph.addBoundaryLine(isVbound, valueMap.get("stop"),
                min, max);
        
    }
    //Sets the direction of the arrows in the FieldPane in the GraphPane
    //based on the value of the acceleration.
    private void setFieldDirection(){
        double accelX = valueMap.get("accelX");
        double accelY = valueMap.get("accelY");
        String dir = "";
        
        if(accelX<0){ //left
            dir = "left";
        }
        if(accelX>0){ //right
            dir = "right";
        }
        if(accelY<0){ //down
            dir = "down";
        }
        if(accelY>0){ //up
            dir = "up";
        }
        
        view.graph.setFieldDirection(dir);
    }
}
