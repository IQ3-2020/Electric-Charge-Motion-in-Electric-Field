/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jeffrey Gan
 * 
 * A Pane that has all the Labels necessary for displaying useful variable values.
 */

public class OutputPane extends VBox{
    
    private GridPane infoPane = new GridPane();
    
    //initial values
    public UnitLabel
            initPosX = new UnitLabel("m"),
            initPosY = new UnitLabel("m"),
            initVelX = new UnitLabel("m/s"),
            initVelY = new UnitLabel("m/s"),
            initVelDir = new UnitLabel("deg");
    //final values
    public UnitLabel
            finalPosX = new UnitLabel("m"),
            finalPosY = new UnitLabel("m"),
            finalVelX = new UnitLabel("m/s"),
            finalVelY = new UnitLabel("m/s"),
            finalVelDir = new UnitLabel("deg");
    //difference (final - initial)
    public UnitLabel
            diffPosX = new UnitLabel("m"),
            diffPosY = new UnitLabel("m"),
            diffVelX = new UnitLabel("m/s"),
            diffVelY = new UnitLabel("m/s"),
            diffVelDir = new UnitLabel("deg"),
            diffTime = new UnitLabel("s");
            
    
    //constant values
    public UnitLabel
            mass = new UnitLabel("kg"),
            charge = new UnitLabel("C"),
            fieldMag = new UnitLabel("N/C");
    
    
    OutputPane(){
        super(0);
        this.setPrefSize(790.0, 390.0);
        this.setMaxSize(790.0, 390.0);
        this.setAlignment(Pos.TOP_LEFT);
        this.setStyle("-fx-background-color: gainsboro; -fx-font-size: 15;");
        this.setPadding(new Insets(20));
        
        ColumnConstraints headerColumn = new ColumnConstraints();
        headerColumn.setPercentWidth(16.0);
        ColumnConstraints otherColumns = new ColumnConstraints();
        otherColumns.setPercentWidth((100.0-16.0)/3.0);
        
        infoPane.setVgap(5);
        infoPane.setMinWidth(750.0); infoPane.setMaxWidth(750.0);
        infoPane.getColumnConstraints().addAll(
                headerColumn,
                otherColumns, otherColumns, otherColumns
        );
        
        infoPane.addRow(0, new Label(),
                new Label("Initial Values"),
                new Label("Final Values"),
                new Label("Difference")
        );
        int colIndex = -1;
        colIndex++; infoPane.addColumn(colIndex,
                new Label("Pos X:"),
                new Label("Pos Y:"),
                new Label("Vel X:"),
                new Label("Vel Y:"),
                new Label("Vel Dir:"),
                new Label("Time:")
        );
        colIndex++; infoPane.addColumn(colIndex,
                initPosX,
                initPosY,
                initVelX,
                initVelY,
                initVelDir
        );
        colIndex++; infoPane.addColumn(colIndex,
                finalPosX,
                finalPosY,
                finalVelX,
                finalVelY,
                finalVelDir
        );
        colIndex++; infoPane.addColumn(colIndex,
                diffPosX,
                diffPosY,
                diffVelX,
                diffVelY,
                diffVelDir,
                diffTime
        );
        
        int rowIndex = 6;
        rowIndex++; infoPane.add(new Separator(), 0, rowIndex, 4, 1);
        rowIndex++; infoPane.add(new Label("Constant Values"), 1, rowIndex);
        rowIndex++; infoPane.addRow(rowIndex, new Label("Mass:"), mass);
        rowIndex++; infoPane.addRow(rowIndex, new Label("Charge:"), charge);
        rowIndex++; infoPane.addRow(rowIndex, new Label("Field:"), fieldMag);
        
        this.getChildren().addAll(infoPane);
    }
    /**
     * A HBox that contains two Labels
     * The first Label displays the numerical value
     * The second Label displays the unit of the value
     */
    public final class UnitLabel extends HBox{
        private String unit;
        private Double value;
        private final Label 
                valueLabel = new Label(),
                unitLabel = new Label();
        
        private UnitLabel(String unit){
            super();
            this.unit = "[ "+unit+" ]";
            this.value = 0.0;
            
            this.valueLabel.setPrefWidth(120.0);
            setValue(this.value);
            
            this.unitLabel.setText(String.format("%-8s", this.unit));
            this.unitLabel.setPrefWidth(60.0);
            
            this.getChildren().addAll(valueLabel, unitLabel);
            
        }
        public void setValue(Double value){
            this.value = value;
            this.valueLabel.setText(String.format("%10.4g", this.value));
        }
        
    }
    
    
}
