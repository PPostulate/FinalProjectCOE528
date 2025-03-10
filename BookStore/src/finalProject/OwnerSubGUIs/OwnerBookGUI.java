package finalProject.OwnerSubGUIs;
import finalProject.GUIMode;
import finalProject.Driver; 
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OwnerBookGUI extends GUIMode {
    
    @Override
    public void start(Stage stage, Driver d) {
        // Create GridPane for layout
        GridPane ownerBookPane = new GridPane(); 
        ownerBookPane.setPadding(new Insets(20));
        ownerBookPane.setAlignment(Pos.CENTER);
        ownerBookPane.setHgap(0);  // Horizontal gap
        ownerBookPane.setVgap(0);  // Vertical gap
        
        // Create TableView and columns
        TableView table = new TableView(); 
        table.setEditable(true);

        // Create columns
        TableColumn BookName = new TableColumn("Book Name");
        TableColumn BookPrice = new TableColumn("Book Price");

        // Set column width to 50% of TableView width
        BookName.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        BookPrice.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        BookName.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
        BookPrice.setStyle("-fx-padding: 0; -fx-alignment: center; -fx-border-width: 0;");
        // Add columns to table
        table.getColumns().addAll(BookName, BookPrice);

        // Set the table size properties
        table.prefWidthProperty().bind(ownerBookPane.widthProperty().subtract(40)); // Adjust width to fit in GridPane
        table.prefHeightProperty().bind(ownerBookPane.heightProperty().subtract(40)); // Adjust height
        
//        ObservableList<BookData> data = FXCollections.observableArrayList();
        
        // Add VBox to GridPane
        ownerBookPane.add(table, 0, 0);

        // Remove padding from table itself
        table.setStyle("-fx-padding: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: transparent;");
        table.getStyleClass().add("no-table-border"); 
        // Create Scene and Stage
        Scene scene = new Scene(ownerBookPane, 400, 200);
        stage.setTitle("Bookstore App [Book]"); 
        stage.setScene(scene); 
        stage.show(); 
    }
}
