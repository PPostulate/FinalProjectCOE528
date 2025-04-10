/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject.OwnerSubGUIs;

import finalProject.Database;
import finalProject.CustomerData;
import finalProject.Datashard;
import finalProject.Driver;
import finalProject.FilePath;
import finalProject.GUIMode;
import finalProject.OwnerStartGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author myasui
 */
public class OwnerCustomerGUI extends GUIMode {
    
    @Override
    public void start(Stage stage, Driver d) {
    GridPane ownerCustomerPane = new GridPane();
    ownerCustomerPane.setPadding(new Insets(20));
    ownerCustomerPane.setAlignment(Pos.CENTER);
    ownerCustomerPane.setHgap(10);  // Horizontal gap
    ownerCustomerPane.setVgap(10);  // Vertical gap
    
    // Create TableView and columns
    TableView <CustomerData> table = new TableView<>(); 
    table.setEditable(true);
    
    // Create columns
    TableColumn<CustomerData, String> username = new TableColumn<>("Username");
    username.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().name));  
        
    TableColumn<CustomerData, String> password = new TableColumn<>("Password");
    password.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(cellData.getValue().password));
    
    TableColumn<CustomerData, Double> points = new TableColumn<>("Points");
    points.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().points).asObject());
    
    // Set column width to 50% of TableView width
    username.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
    password.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
    points.prefWidthProperty().bind(table.widthProperty().multiply(0.34));
    
    username.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
    password.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
    points.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
    
    // Add columns to table
    table.getColumns().addAll(username, password, points);
    
    // Set the table size properties
    table.prefWidthProperty().bind(ownerCustomerPane.widthProperty().subtract(40)); // Adjust width to fit in GridPane
    table.prefHeightProperty().bind(ownerCustomerPane.heightProperty().subtract(40)); // Adjust height
    
    //Make observable list
    ObservableList<CustomerData> customers = FXCollections.observableArrayList();
    for (Datashard data : Database.Read(FilePath.customer)){
            customers.add((CustomerData)data);
    }
    
    table.setItems(customers);
    
    
    //Make book name and price entry fields
    TextField userField = new TextField();
    userField.setPrefWidth(300);
    TextField passField = new TextField();
    passField.setPrefWidth(300);
    userField.setPromptText("Username");
    passField.setPromptText("Password");
    Label errLabel = new Label();
    
    //Button for adding a customer
    Button addButton = new Button("Add"); 
    addButton.setOnAction(event -> {
        String user = userField.getText();
        String pass = passField.getText();
        if (Database.compareCustomer(user) && user.trim().length() > 0 && pass.trim().length() > 0){
            CustomerData c = new CustomerData(user, 0, pass);
            customers.add(c);
            Database.Write(c);  
            errLabel.setText("");
        }else {
            if (user.trim().length() == 0){
                errLabel.setText("Invalid, customer username cannot be blank");
            }else if (pass.trim().length() == 0){
                errLabel.setText("Invalid, customer password cannot be blank");
            }else{
                errLabel.setText("Invalid, Customer username already exists");
            }
        }
        
    });
    
    //Exit Button 
    Button backButton = new Button("Back"); 
    backButton.setOnAction(event -> {
        // Goes back to the previous screen
        d.setGUIMode(new OwnerStartGUI());
        d.startGUI(stage);
    });
    
    //Delete Button
    Button deleteButton = new Button("Delete");
    deleteButton.setOnAction(event -> {
        CustomerData highlightedRow = table.getSelectionModel().getSelectedItem();
            
        if (highlightedRow != null){
            customers.remove(highlightedRow);
            Database.Remove(highlightedRow);
        } else{
            errLabel.setText("No row highlighted.");
        }
    });
    
    
    // Adds a new HBox 
    HBox buttonContainer = new HBox(); 
    buttonContainer.setAlignment(Pos.CENTER_LEFT);
    buttonContainer.setSpacing(5); 
    buttonContainer.getChildren().addAll(backButton,deleteButton,addButton,errLabel); 
    
    
    
    
    //Add elements to GridPane
    
    ownerCustomerPane.add(table, 0, 0);
    ownerCustomerPane.add(userField, 0, 1);
    ownerCustomerPane.add(passField, 0, 2);
    ownerCustomerPane.add(buttonContainer, 0, 3);
    //ownerCustomerPane.add(errLabel, 0, 4);

    // Remove padding from table itself
    table.setStyle("-fx-padding: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: transparent;");
    table.getStyleClass().add("no-table-border");
    
    // Create Scene and Stage
    Scene scene = new Scene(ownerCustomerPane, 800, 400);
    stage.setTitle("Bookstore App [Customer]");
    stage.setScene(scene);
    stage.show();
    
    }
    
}
