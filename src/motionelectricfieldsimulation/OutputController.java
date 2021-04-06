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
public class OutputController {
    private SimWindow view;
    private HashMap<String,Double> valueMap;
    
    public OutputController(SimWindow view, HashMap<String,Double> valueMap){
        this.view = view;
        this.valueMap = valueMap;
    }
    
    public void setLabelValues(){
        view.output.initPosX.setValue(valueMap.get("initPosX"));
        view.output.initPosY.setValue(valueMap.get("initPosY"));
        view.output.initVelX.setValue(valueMap.get("initVelX"));
        view.output.initVelY.setValue(valueMap.get("initVelY"));
        view.output.initVelDir.setValue(valueMap.get("initVelDir"));

        view.output.finalPosX.setValue(valueMap.get("finalPosX"));
        view.output.finalPosY.setValue(valueMap.get("finalPosY"));
        view.output.finalVelX.setValue(valueMap.get("finalVelX"));
        view.output.finalVelY.setValue(valueMap.get("finalVelY"));
        view.output.finalVelDir.setValue(valueMap.get("finalVelDir"));

        view.output.diffPosX.setValue(valueMap.get("diffX"));
        view.output.diffPosY.setValue(valueMap.get("diffY"));
        view.output.diffVelX.setValue(valueMap.get("diffVelX"));
        view.output.diffVelY.setValue(valueMap.get("diffVelY"));
        view.output.diffVelDir.setValue(valueMap.get("diffVelDir"));
        view.output.diffTime.setValue(valueMap.get("diffTime"));

        view.output.mass.setValue(valueMap.get("mass"));
        view.output.charge.setValue(valueMap.get("charge"));
        view.output.fieldMag.setValue(valueMap.get("fieldMag"));
    }
}
