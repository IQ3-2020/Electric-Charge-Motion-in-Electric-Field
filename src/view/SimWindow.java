/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jeffrey Gan
 * 
 * The main window of the simulation.
 * Contains the input pane, the output pane and the graph pane.
 * Determines the layout of the simulation.
 * 
 */
public class SimWindow extends AnchorPane{
    public final GraphPane graph = new GraphPane();
    public final InputPane input = new InputPane();
    public final OutputPane output = new OutputPane();
    
    public SimWindow(){
        super();
        //Size is restricted to simplify coding and to prevent layout problems
        this.setPrefSize(1200, 800);
        this.setMinSize(1200, 800);
        this.setMaxSize(1200, 800);
        this.setPadding(new Insets(5));
        
        //The inputs are on the right
        AnchorPane.setTopAnchor(input, 0.0);
        AnchorPane.setRightAnchor(input, 0.0);
        AnchorPane.setBottomAnchor(input, 0.0);
        
        //The outputs are on the bottom-left
        AnchorPane.setBottomAnchor(output, 0.0);
        AnchorPane.setLeftAnchor(output, 0.0);
        
        //The graph is on the top-left
        AnchorPane.setTopAnchor(graph, 0.0);
        AnchorPane.setLeftAnchor(graph, 0.0);
        
        this.getChildren().addAll(input, output, graph);
    }
    
    
    
}
