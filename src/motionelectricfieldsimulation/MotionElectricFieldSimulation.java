/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motionelectricfieldsimulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.SimWindow;

/**
 *
 * @author Jeffrey Gan
 */
public class MotionElectricFieldSimulation extends Application {
    
    @Override
    public void start(Stage stage){
        
        MotionSimController controller = new MotionSimController();
        
        Scene scene = new Scene(controller.getSimWindow());
        stage.setScene(scene);
        
        stage.setResizable(false);
        stage.setTitle("Charge Motion in an Electric Field Simulator");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
