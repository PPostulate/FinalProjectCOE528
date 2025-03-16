/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 *
 * @author myasui
 */
public class CustomerStartGUI extends GUIMode{
    private CustomerData c;
    
    public CustomerStartGUI (CustomerData c){
        this.c = c;
    }
    
    @Override
    public void start(Stage stage, Driver d){
        
        GridPane customerPane = new GridPane();
        customerPane.setPadding(new Insets(20));
        customerPane.setAlignment(Pos.CENTER);
        customerPane.setHgap(10);  // Horizontal gap
        customerPane.setVgap(10);  // Vertical gap
        
        Label welcomeLabel = new Label();
        welcomeLabel.setText("Welcome " + c.name + ". You have " + c.points + " points. Your status is "+ c.getStatus());
        
        // Create TableView and columns
        TableView <BookData> table = new TableView<>(); 
        table.setEditable(true);
        
        // Create columns
        TableColumn<BookData, String> bookName = new TableColumn<>("Book Name");
        bookName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().name));
    
        TableColumn<BookData, Double> bookPrice = new TableColumn<>("Book Price");
        bookPrice.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().price).asObject());
        
        TableColumn<BookData, Boolean> selected = new TableColumn<>("Select");
        selected.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleBooleanProperty(cellData.getValue().selected));
        selected.setCellFactory(CheckBoxTableCell.forTableColumn(selected));
        
        // Set column width to 50% of TableView width
        bookName.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        bookPrice.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        selected.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
    
        bookName.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
        bookPrice.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
        selected.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
        
        //Make observable list
        ObservableList<BookData> books = FXCollections.observableArrayList();
        for (Datashard data : Database.Read(FilePath.book)){
            books.add((BookData)data);
        }
        
        //Add columns to table
        table.getColumns().addAll(bookName, bookPrice, selected);
        table.setItems(books);
        
        // Set the table size properties
        table.prefWidthProperty().bind(customerPane.widthProperty().subtract(40)); // Adjust width to fit in GridPane
        table.prefHeightProperty().bind(customerPane.heightProperty().subtract(40)); // Adjust height
        
        
        // Add items to GridPane
        customerPane.add(welcomeLabel, 0, 0);
        customerPane.add(table, 0, 1);
        
        // Remove padding from table itself
        table.setStyle("-fx-padding: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: transparent;");
        table.getStyleClass().add("no-table-border");
        
        // Create Scene and Stage
        Scene scene = new Scene(customerPane, 800, 400);
        stage.setTitle("Bookstore App [Customer]");
        stage.setScene(scene);
        stage.show();
        
    }

    
    
    
}
