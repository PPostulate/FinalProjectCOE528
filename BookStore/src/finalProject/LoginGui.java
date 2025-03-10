/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author myasui
 */
public class LoginGui extends GUIMode {

    @Override
    public void start(Stage stage, Driver d) {
        
        GridPane loginGridPane = new GridPane();
        loginGridPane.setPadding(new Insets(20));
        loginGridPane.setHgap(10);
        loginGridPane.setVgap(10);

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
                
                d.setGUIMode(new OwnerStartGUI());
                d.startGUI(stage);
                
                
                String userStr = username.getText();
                String pwStr = password.getText();
                if (userStr.equals("admin") && pwStr.equals("admin")){
                    d.setGUIMode(new OwnerStartGUI());
                    d.startGUI(stage);
                }

            }
        });

        loginGridPane.add(welcomeMsg, 0, 0);
        loginGridPane.add(userPrompt, 0, 1);
        loginGridPane.add(pwPrompt, 0, 2);
        loginGridPane.add(username, 1, 1);
        loginGridPane.add(password, 1, 2);
        loginGridPane.add(loginBtn, 1, 3);
        
        Scene scene = new Scene(loginGridPane, 400, 200);
        stage.setTitle("Bookstore App");
        stage.setScene(scene);
        stage.show();

    }

}
