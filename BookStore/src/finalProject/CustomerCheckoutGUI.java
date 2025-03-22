/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author myasui
 */
public class CustomerCheckoutGUI extends GUIMode{
    public boolean payWithPoints;
    public ArrayList<BookData> bookCart = new ArrayList();
    public CustomerData cData;
    
    public CustomerCheckoutGUI(ArrayList b, CustomerData c, boolean p){
        payWithPoints = p;
        bookCart = b;
        cData = c;
    }
    
    @Override
    public void start(Stage stage, Driver d){
        double transactionCost = 0;
        
        GridPane checkoutPane = new GridPane();
        checkoutPane.setPadding(new Insets(20));
        checkoutPane.setAlignment(Pos.CENTER);
        checkoutPane.setHgap(10);  // Horizontal gap
        checkoutPane.setVgap(10);  // Vertical gap
        Label transactionCostLabel = new Label();
        Label customerStats = new Label();
        
        for (BookData b : bookCart){
            transactionCost += b.price;
            Database.Remove(b);
        }
        
        if (payWithPoints){
            if (transactionCost - cData.points/100 >= 0){
                transactionCost -= cData.points/100;
                cData.points = transactionCost*10;
            } else{
                cData.points -= transactionCost*100;
                transactionCost = 0;
            }
        }
        
        transactionCostLabel.setText("Total Cost: " + transactionCost);
        customerStats.setText("Points: " + cData.points + " Status: " + cData.getStatus());
        
        
        
        // Add logout button
        Button logout = new Button("Logout");
        logout.setOnAction(event -> {
            d.setGUIMode(new LoginGUI());
            d.start(stage);
        });

        // Add items to GridPane
        checkoutPane.add(transactionCostLabel, 0, 0);
        checkoutPane.add(customerStats, 0, 1);
        checkoutPane.add(logout, 0, 2);
        
        // Create Scene and Stage
        Scene scene = new Scene(checkoutPane, 400, 200);
        stage.setTitle("Bookstore App [Customer Checkout]");
        stage.setScene(scene);
        stage.show();
        
       
    }
    
}
