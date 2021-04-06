/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motionelectricfieldsimulation;

import java.util.HashMap;
import view.SimWindow;

/**
 *
 * @author Jeffrey Gan
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
    public void plotAll(){
        view.graph.clearData();
        plotPoints();
        plotBoundary();
        setFieldDirection();
    }
    
    private void plotPoints(){
        Double posX, posY, time;
        Double prevX = valueMap.get("initPosX");
        Double prevY = valueMap.get("initPosY");
        Double timeMax = valueMap.get("diffTime");
        boolean xflag = false;
        
        minX = 0.0;
        minY = 0.0;
        maxX = 0.0;
        maxY = 0.0;
        
        if(timeMax.isInfinite() || timeMax.isNaN()){
            timeMax = 100.0;
        }
        
        int limit = 10;
        for(int i=0; i<=limit; i++){
            time = i*timeMax/(double)limit;
            
            posX = valueMap.get("initPosX")
                    + valueMap.get("initVelX")*time
                    + 0.5*valueMap.get("accelX")*time*time;
            
            posY = valueMap.get("initPosY")
                    + valueMap.get("initVelY")*time
                    + 0.5*valueMap.get("accelY")*time*time;
            
            if(maxX<posX){
                maxX = posX;
            }
            if(maxY<posY){
                maxY = posY;
            }
            if(minX>posX){
                minX = posX;
            }
            if(minY>posY){
                minY = posY;
            }
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
        }
        
    }
    private void plotBoundary(){
        boolean isVbound = valueMap.get("isVbound").equals(1.0);
        Double max = 0.0, min = 0.0;
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
