/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motionelectricfieldsimulation;

import java.util.HashMap;
import javafx.event.ActionEvent;
import view.InputPane;
import view.SimWindow;

/**
 *
 * @author Jeffrey Gan
 * 
 * Collects the values from the control elements of the GUI and does
 * the necessary calculations to compute missing desired information.
 */
public class InputHandler {
    //constant values
    private Double 
            mass = 0.0,
            charge = 0.0,
            fieldMag = 0.0,
            stop = 0.0,
            accelX = 0.0,
            accelY = 0.0;
    //initial values
    private Double
            initPosX = 0.0,
            initPosY = 0.0,
            initVelMag = 0.0,
            initVelX = 0.0,
            initVelY = 0.0,
            initVelDir = 0.0;
    //final values
    private Double
            finalPosX = 0.0,
            finalPosY = 0.0,
            finalVelX = 0.0,
            finalVelY = 0.0,
            finalVelDir = 0.0;
    //difference
    private Double
            diffX = 0.0,
            diffY = 0.0,
            diffVelX = 0.0,
            diffVelY = 0.0,
            diffVelDir = 0.0,
            diffTime = 0.0;
    
    private Boolean 
            isVfield = true,
            isVbound = true,
            hasException = false;
    
    private String exMessage = "";
    
    private SimWindow view;
    private HashMap<String, Double> valueMap = new HashMap<>();
    
    public InputHandler(SimWindow view){
        setToggleButtonAction(view.input);
        this.view = view;
    }
    
    //The group of functions that are called when the "CALCULATE" button is pressed.
    public void onAction(){
        reset();
        getInputs();
        calculate();
        setMapValues();
    }
    
    private void getInputs(){
        reset();

        //get the values from the textfields and detect invalid values
        try {
            mass       = Double.parseDouble(view.input.mass.getText().toUpperCase());
            initPosX   = Double.parseDouble(view.input.posX.getText().toUpperCase());
            initPosY   = Double.parseDouble(view.input.posY.getText().toUpperCase());
            initVelMag = Double.parseDouble(view.input.velMag.getText().toUpperCase());
            initVelDir = Double.parseDouble(view.input.velDir.getText().toUpperCase());
            charge     = Double.parseDouble(view.input.charge.getText().toUpperCase());
            fieldMag   = Double.parseDouble(view.input.fieldMag.getText().toUpperCase());
            stop       = Double.parseDouble(view.input.stop.getText().toUpperCase());

            if(mass<0.0){
                throw new NegativeMassException();
            }
            else if(mass.equals(0.0)){
                throw new ZeroMassException();
            }

        } 
        catch (NumberFormatException nfe) {
            String nfeMessage = 
                    "Number Format Exception! Please enter valid numbers only.\n"
                    + "\tExamples of valid numbers: 123, 3.8E+12, 94.2e-26";
            addToErrorString(nfeMessage);
        } 
        catch (NegativeMassException nme) {
            String nmeMessage = "Negative Mass! Please enter a positive mass\n";
            addToErrorString(nmeMessage);
        } 
        catch (ZeroMassException zme) {
            String zmeMessage = "Zero Mass! Please enter a positive mass\n";
            addToErrorString(zmeMessage);
        }

        if(hasException){
            view.input.exMessage.setText(exMessage);
        }else{
            exMessage = "Calculated!";
            view.input.exMessage.setText(exMessage);
        }
    };
    //This function first calculates the value of time, and then calculates the missing information.
    private void calculate(){
        diffTime = 0.0;
        initVelX = initVelMag*Math.cos(Math.toRadians(initVelDir));
        initVelY = initVelMag*Math.sin(Math.toRadians(initVelDir));
        finalVelX = initVelX;
        finalVelY = initVelY;
        
        double accel = charge*fieldMag/mass;
        if(accel == 0.0){//no acceleration
            if(isVbound){
                finalPosX = stop;
                diffX = finalPosX-initPosX;
                diffTime = diffX/initVelX;
            }else{
                finalPosY = stop;
                diffY = finalPosY-initPosY;
                diffTime = diffY/initVelY;
            }
            
            if(diffTime<0){
                diffTime = Double.POSITIVE_INFINITY;
            }
            
        }else if(isVbound&&isVfield){ //xfinal = stop, vertical acceleration
            accelY = accel;
            finalPosX = stop;
            diffX = finalPosX - initPosX;
            
            diffTime = diffX/initVelX;
            
        }else if(isVbound&&!isVfield){//xfinal = stop, horizontal acceleration
            accelX = accel;
            finalPosX = stop;
            diffX = finalPosX-initPosX;
            
            finalVelX = Math.sqrt(initVelX*initVelX + 2.0*accelX*diffX);
            diffVelX = finalVelX - initVelX;
            
            diffTime = diffVelX/accelX;
            if(diffTime<0){
                double time0 = -initVelX/accelX;
                double time1 = -finalVelX/accelX;
                diffTime = time0+time1;
            }
            
        }else if(!isVbound&&isVfield){//yfinal = stop, vertical acceleration
            accelY = accel;
            finalPosY = stop;
            diffY = finalPosY - initPosY;
            
            finalVelY = Math.sqrt(initVelY*initVelY + 2.0*accelY*diffY);
            diffVelY = finalVelY-initVelY;
            
            diffTime = diffVelY/accelY;
            if(diffTime<0){
                double time0 = -initVelY/accelY;
                double time1 = -finalVelY/accelY;
                diffTime = time0+time1;
            }
            
        }else if(!isVbound&&!isVfield){//yfinal = stop, horizontal acceleration
            accelX = accel;
            finalPosY = stop;
            diffY = finalPosY - initPosY;
            
            diffTime = diffY/initVelY;
            
        }
        if(diffTime<0){
            diffTime = Double.POSITIVE_INFINITY;
        }
        
        finalPosX = initPosX + initVelX*diffTime;
        diffX = finalPosX - initPosX;
        finalVelX = initVelX + accelX*diffTime;
        diffVelX = finalVelX - initVelX;
        
        finalPosY = initPosY + initVelY*diffTime;
        diffY = finalPosY - initPosY;
        finalVelY = initVelY + accelY*diffTime;
        diffVelY = finalVelY - initVelY;
        
        finalVelDir = Math.toDegrees(Math.atan2(finalVelY, finalVelX));
        diffVelDir = finalVelDir - initVelDir;
        
    }
    
    private void reset(){
        mass = 0.0;
        charge = 0.0;
        fieldMag = 0.0;
        stop = 0.0;
        accelX = 0.0;
        accelY = 0.0;
        
        initPosX = 0.0;
        initPosY = 0.0;
        initVelMag = 0.0;
        initVelX = 0.0;
        initVelY = 0.0;
        initVelDir = 0.0;
        
        finalPosX = 0.0;
        finalPosY = 0.0;
        finalVelX = 0.0;
        finalVelY = 0.0;
        finalVelDir = 0.0;
        
        diffX = 0.0;
        diffY = 0.0;
        diffVelX = 0.0;
        diffVelY = 0.0;
        diffTime = 0.0;
        
        hasException = false;
        exMessage = "";        
    }
    private void setMapValues(){
        valueMap.put("mass",mass);
        valueMap.put("charge",charge);
        valueMap.put("fieldMag",fieldMag);
        valueMap.put("stop",stop);
        valueMap.put("accelX",accelX);
        valueMap.put("accelY",accelY);
        
        valueMap.put("initPosX", initPosX);
        valueMap.put("initPosY", initPosY);
        valueMap.put("initVelX", initVelX);
        valueMap.put("initVelY", initVelY);
        valueMap.put("initVelDir", initVelDir);

        valueMap.put("finalPosX", finalPosX);
        valueMap.put("finalPosY", finalPosY);
        valueMap.put("finalVelX", finalVelX);
        valueMap.put("finalVelY", finalVelY);
        valueMap.put("finalVelDir", finalVelDir);

        valueMap.put("diffX", diffX);
        valueMap.put("diffY",diffY);
        valueMap.put("diffVelX",diffVelX);
        valueMap.put("diffVelY",diffVelY);
        valueMap.put("diffVelDir",diffVelDir);
        valueMap.put("diffTime",diffTime);
        
        valueMap.put("isVbound",0.0);
        valueMap.put("isVfield",0.0);
        
        if(isVbound){
            valueMap.put("isVbound",1.0);
        }
        if(isVfield){
            valueMap.put("isVfield",1.0);
        }
        
        
    }

    public HashMap<String, Double> getValueMap() {
        return valueMap;
    }
    
    
    //This sets the action of the buttons that selects
    //the axis of the field and the axis of the stop condition
    private void setToggleButtonAction(InputPane inputPane){
        inputPane.isVbound.setOnAction((ActionEvent event) -> {
            if(isVbound){
                isVbound=false;
                inputPane.isVbound.setText("y =");
            }
            else{
                isVbound=true;
                inputPane.isVbound.setText("x =");
            }
        });
        
        inputPane.isVfield.setOnAction((ActionEvent event) -> {
            if(isVfield){
                isVfield=false;
                inputPane.isVfield.setText("Horizontal Field");
            }
            else{
                isVfield=true;
                inputPane.isVfield.setText("Vertical Field");
            }
        });
        
    }
    
    private void addToErrorString(String excString){
        if (!exMessage.contains(excString)) {
            exMessage += excString;
        }
        hasException = true;
    }
    
    private class ZeroMassException extends Exception{
        private ZeroMassException() {
        }
    }
    private class NegativeMassException extends Exception{
        private NegativeMassException() {
        }
    }
}
