/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package finalProject;

import javafx.application.Application;
import javafx.stage.Stage;




/**
 *
 * @author myasui
 */
public class Driver extends Application {
    private GUIMode currentGUI = new LoginGui();
    
    @Override
    public void start(Stage primaryStage) {
        currentGUI.start(primaryStage, this);
        primaryStage.setOnCloseRequest(event -> {
            Database.Flush();
        });
    }
    
    public void startGUI(Stage s){
        currentGUI.start(s,this);
    }
    
    public void setGUIMode(GUIMode g){
        currentGUI = g;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database.Init();
        launch(args);
    }
    
}
