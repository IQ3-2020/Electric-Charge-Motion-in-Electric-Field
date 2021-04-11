/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jeffrey Gan
 * 
 * A Pane that contains all the GUI control elements necessary for collecting user input.
 */
public class InputPane extends VBox{
    //Control Elements
    public TextField mass = new TextField();
    public TextField posX = new TextField();
    public TextField posY = new TextField();
    public TextField velMag = new TextField();
    public TextField velDir = new TextField();
    public TextField charge = new TextField();
    public TextField fieldMag = new TextField();
    public Button isVfield = new Button("Vertical Field");
    public Button isVbound = new Button("x =");
    public TextField stop = new TextField();
    public Label exMessage = new Label("");
    public Button startButton = new Button("CALCULATE");
    
    //Input Pane Elements
    private final Label paneTitle = new Label("INPUT");
    private final GridPane chargePane = new GridPane();
    private final GridPane fieldPane = new GridPane();
    private final GridPane stopPane = new GridPane();
    
    
    InputPane(){
        //Style of the InputPane
        super(30);
        this.setPrefWidth(390.0);
        this.setMaxWidth(390.0);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-background-color: gainsboro; -fx-font-size: 15;");
        this.setPadding(new Insets(20));
        
        double width = 350.0;
        
        //Set the Prompt Text for the TextFields
        mass.setPromptText("mass (kg)");
        posX.setPromptText("x position (m)");
        posY.setPromptText("y position (m)");
        velMag.setPromptText("magnitude (m/s)");
        velDir.setPromptText("direction (deg)");
        charge.setPromptText("charge magnitude (C)");
        fieldMag.setPromptText("field magnitude (N/C)");
        stop.setPromptText("boundary condition (m)");
        
        //Style of the GridPane columns
        ColumnConstraints headerColumn = new ColumnConstraints();
        headerColumn.setPercentWidth(24.0);
        ColumnConstraints otherColumns = new ColumnConstraints();
        otherColumns.setPercentWidth((100.0-24.0)/2.0);
        
        paneTitle.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
        
        //Set up the "CHARGE PROPERTIES" section
        chargePane.setVgap(3);
        chargePane.setMinWidth(width);chargePane.setMaxWidth(width);
        chargePane.getColumnConstraints().addAll(headerColumn, otherColumns, otherColumns);
        chargePane.add(new Label("CHARGE PROPERTIES"), 0, 0, 3, 1);
        chargePane.add(new Label("mass"), 0, 1, 1, 1);
        chargePane.add(mass, 1, 1, 2, 1);
        chargePane.addRow(2, new Label("position"), posX, posY);
        chargePane.addRow(3, new Label("velocity"), velMag, velDir);
        chargePane.addRow(4, new Label("charge"));
        chargePane.add(charge, 1, 4, 2, 1);
        
        //Set up the "FIELD PROPERTIES" section
        fieldPane.setVgap(3);
        fieldPane.setMinWidth(width);fieldPane.setMaxWidth(width);
        fieldPane.getColumnConstraints().addAll(headerColumn, otherColumns, otherColumns);
        fieldPane.add(new Label("FIELD PROPERTIES"), 0, 0, 3, 1);
        fieldPane.add(new Label("magnitude"), 0, 1, 1, 1);
        fieldPane.add(fieldMag, 1, 1, 2, 1);
        isVfield.setPrefWidth(350.0);
        fieldPane.add(isVfield, 0, 2, 3, 1);
        
        //Set up the "STOP CONDITION" section
        stopPane.setVgap(3);
        stopPane.setMinWidth(width);stopPane.setMaxWidth(width);
        stopPane.getColumnConstraints().addAll(headerColumn, otherColumns, otherColumns);
        isVbound.setPrefWidth(76.0);
        stopPane.add(new Label("STOP CONDITION"), 0, 0, 3, 1);
        stopPane.add(isVbound, 0, 1, 1, 1);
        stopPane.add(stop, 1, 1, 2, 1);
        
        //This space is used to ensure that the CALCULATE button is at the bottom of the Vbox
        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);
        
        //This text displays messages regarding the inputs
        exMessage.setStyle("-fx-font-size: 12; -fx-text-fill: red;");
        exMessage.setWrapText(true);
        
        //The "CALCULATE" button
        startButton.setPrefSize(300.0, 100.0);
        startButton.setStyle("-fx-font-size: 30; -fx-font-weight: bold;");
        
        
        this.getChildren().addAll(paneTitle,
                chargePane,
                fieldPane,
                stopPane,
                new HBox(exMessage),
                space,
                startButton
        );
        
    }
}
