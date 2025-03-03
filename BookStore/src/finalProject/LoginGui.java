/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package finalProject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Text;

/**
 *
 * @author myasui
 */
public class LoginGui extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(20));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        Text welcomeMsg = new Text("Welcome to the BookStore App");
        Text userPrompt = new Text("Username:");
        Text pwPrompt = new Text("Pasword:");
        
        
        TextField username = new TextField();
        TextField password = new TextField();
        
        username.setPromptText("Username");
        password.setPromptText("Password");
        
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String userStr = username.getText();
                String pwStr = password.getText();
                System.out.printf("User: %s\n Password: %s\n", userStr, pwStr);
            }
        });
        
        
        gridpane.add(welcomeMsg,0,0);
        gridpane.add(userPrompt,0,1);
        gridpane.add(pwPrompt,0,2);
        gridpane.add(username, 1,1);
        gridpane.add(password,1,2);
        gridpane.add(loginBtn, 1, 3);
        
        Scene scene = new Scene(gridpane, 400, 200);
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
