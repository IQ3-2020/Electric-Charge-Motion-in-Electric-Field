/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motionelectricfieldsimulation;

import view.SimWindow;

/**
 *
 * @author Jeffrey Gan
 * 
 * The controller that includes every other controller
 * Sets the functionality of the "CALCULATE" button
 * 
 */
public class MotionSimController {
    private SimWindow view = new SimWindow();
    private InputHandler inputHandler = new InputHandler(view);
    private OutputController outputController = new OutputController(view, inputHandler.getValueMap());
    private GraphController graphController = new GraphController(view, inputHandler.getValueMap());
    
    public MotionSimController() {
        view.input.startButton.setOnAction(((event) -> {
            inputHandler.onAction();
            outputController.setLabelValues();
            graphController.plotAll();
        }));
    }
    
    /**
     * Returns the SimWindow of this controller, AKA the layout of this Simulation
     * @return The SimWindow of this controller
     */
    public SimWindow getSimWindow() {
        return view;
    }
    
        
        
}
