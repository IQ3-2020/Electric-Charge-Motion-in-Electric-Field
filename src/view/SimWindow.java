/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jeffrey Gan
 */
public class SimWindow extends AnchorPane{
    public final GraphPane graph = new GraphPane();
    public final InputPane input = new InputPane();
    public final OutputPane output = new OutputPane();
    
    public SimWindow(){
        super();
        this.setPrefSize(1200, 800);
        this.setMaxSize(1200, 800);
        this.setPadding(new Insets(5));
        
        AnchorPane.setTopAnchor(input, 0.0);
        AnchorPane.setRightAnchor(input, 0.0);
        AnchorPane.setBottomAnchor(input, 0.0);
        
        AnchorPane.setBottomAnchor(output, 0.0);
        AnchorPane.setLeftAnchor(output, 0.0);
        AnchorPane.setTopAnchor(graph, 0.0);
        AnchorPane.setLeftAnchor(graph, 0.0);
        
        this.getChildren().addAll(input, output, graph);
    }
    
    
    
}
