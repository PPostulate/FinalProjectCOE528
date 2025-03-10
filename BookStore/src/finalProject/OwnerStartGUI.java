/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import finalProject.OwnerSubGUIs.OwnerBookGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author myasui
 */
public class OwnerStartGUI extends GUIMode{
    
   
    
    @Override
    public void start (Stage stage, Driver d){
        GridPane ownerGridPane = new GridPane();
        ownerGridPane.setPadding(new Insets(20));
        ownerGridPane.setAlignment(Pos.CENTER);
        ownerGridPane.setHgap(10);
        ownerGridPane.setVgap(10);
        
        VBox buttonBox = new VBox(10); 
        buttonBox.setAlignment(Pos.CENTER);
        
        Button bookButton = new Button("Books"); 
        Button customerButton = new Button("Customers"); 
        Button logoutButton = new Button("Logout"); 
        
        double buttonWidth = 150; 
        bookButton.setPrefWidth(buttonWidth);
        customerButton.setPrefWidth(buttonWidth);
        logoutButton.setPrefWidth(buttonWidth);
        
        bookButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               d.setGUIMode(new OwnerBookGUI());
               d.startGUI(stage);

            }
            
        });
        
        
        buttonBox.getChildren().addAll(bookButton,customerButton,logoutButton); 
        ownerGridPane.add(buttonBox, 0, 0);
        
        Scene scene = new Scene(ownerGridPane, 400, 200);
        stage.setTitle("Bookstore App [Admin]");
        stage.setScene(scene);
        stage.show();
        
    }
}
