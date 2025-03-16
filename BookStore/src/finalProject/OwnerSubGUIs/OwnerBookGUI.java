package finalProject.OwnerSubGUIs;

import finalProject.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import finalProject.BookData;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.control.cell.PropertyValueFactory;

public class OwnerBookGUI extends GUIMode {
    
    @Override
    public void start(Stage stage, Driver d) {
        // Create GridPane for layout
        GridPane ownerBookPane = new GridPane(); 
        ownerBookPane.setPadding(new Insets(20));
        ownerBookPane.setAlignment(Pos.CENTER);
        ownerBookPane.setHgap(10);  // Horizontal gap
        ownerBookPane.setVgap(10);  // Vertical gap
        
        // Create TableView and columns
        TableView <BookData> table = new TableView<>(); 
        table.setEditable(true);

        // Create columns
        TableColumn<BookData,String> BookName = new TableColumn<>("Book Name");
        BookName.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().name));
        
        
        TableColumn<BookData, Double> BookPrice = new TableColumn<>("Book Price");
        BookPrice.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().price).asObject());
        
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
        
        //Make observable list
        ObservableList<BookData> books = FXCollections.observableArrayList();
        for (Datashard data : Database.Read(FilePath.book)){
            books.add((BookData)data);
        }
        
        table.setItems(books);
        
        
        //Make book name and price entry fields
        TextField nameField = new TextField();
        nameField.setPrefWidth(300);
        TextField priceField = new TextField();
        priceField.setPrefWidth(300);
        nameField.setPromptText("Book Name");
        priceField.setPromptText("Book Price");
        Label errLabel = new Label();
        
        Button addButton = new Button("Add"); 
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
                String bookName = nameField.getText();
                try {
                    double bookPrice = Double.parseDouble(priceField.getText());
                    if (Database.compareBook(bookName)){
                        BookData b = new BookData(bookName, bookPrice);
                        books.add(b);
                        Database.Write(b);
                        errLabel.setText("");
                        
                    }else {
                        errLabel.setText("Invalid, Book already in list");
                    }
                } catch(NumberFormatException e){
                    errLabel.setText("Invalid, please enter an integer");
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
            BookData highlightedRow = table.getSelectionModel().getSelectedItem();
            
            if (highlightedRow != null){
                books.remove(highlightedRow);
                Database.Remove(highlightedRow);
            } else{
                errLabel.setText("No row highlighted.");
            }
        });
        

        
        // Add VBox to GridPane
        ownerBookPane.add(table, 0, 0);
        ownerBookPane.add(nameField, 0, 1);
        ownerBookPane.add(priceField, 0, 2);
        ownerBookPane.add(addButton, 0, 3);
        ownerBookPane.add(errLabel, 0, 4);
        ownerBookPane.add(deleteButton, 0, 5);
        ownerBookPane.add(backButton,0,6);
        

        
        
        // Remove padding from table itself
        table.setStyle("-fx-padding: 0; -fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-border-color: transparent;");
        table.getStyleClass().add("no-table-border"); 
        // Create Scene and Stage
        Scene scene = new Scene(ownerBookPane, 800, 400);
        stage.setTitle("Bookstore App [Book]"); 
        stage.setScene(scene); 
        stage.show(); 
    }
}
