/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

enum FilePath{
    book, 
    customer
}

final class BookData extends Datashard{
    public double price; 
    
    public BookData(String name, double price){
        super(name); 
        this.price = price; 
    }
}

final class CustomerData extends Datashard{
    public double points;
    public String password; 
    
    public CustomerData(String name, double points, String password){
        super(name);
        this.points = points; 
        this.password = password; 
    }
}

sealed abstract class Datashard permits BookData, CustomerData{
    public String name;
    
    public Datashard(String name){
        this.name = name; 
    }
}
 

/**
 * Database manages read/writes from the data directory
 * @author szyang
 */
public class Database {
    
    private static FileWriter fileWriter;
    private static FileReader fileReader; 
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static boolean verified = false; 
    
    private static final String BOOKDATAPATH = "src/finalProject/Data/books.txt";
    private static final String CUSTOMERDATAPATH = "src/finalProject/Data/customers.txt";
  
    private static final ArrayList<Datashard> bookDataCache = new ArrayList<>(); 
    private static final ArrayList<Datashard> customerDataCache = new ArrayList<>(); 
   
    // Private Methods 
    
    private static void Write(ArrayList<Datashard> abstractDataList, FilePath path){
        try{
            
            fileWriter = new FileWriter((path == FilePath.book) ? BOOKDATAPATH : CUSTOMERDATAPATH);
            writer = new BufferedWriter(fileWriter);
            
            if(path == FilePath.book){
                
                for(Datashard abstractData : abstractDataList){
                    BookData currentBook = (BookData) abstractData; 
                    writer.write(currentBook.name + "," + currentBook.price + "\n");
                }
                writer.close();
            }else{
                for(Datashard abstractData : abstractDataList){
                    CustomerData currentBook = (CustomerData) abstractData; 
                    writer.write(currentBook.name + "," + currentBook.points + "\n");
                }
                writer.close();
            }
            
        }catch(IOException e){
            System.out.println("Failed to write data array"); 
        }
        
    }
    
    // Public Methods
    
    /**
     * Initializes the database caches and verifies all data files are present
     */
    public static void Init(){
        File bookDataFile = new File(BOOKDATAPATH);
        File customerDataFile = new File(CUSTOMERDATAPATH);
        
        if(!bookDataFile.exists()){
            try{
                // Creates a new books.txt file 
                bookDataFile.createNewFile();
            }catch(IOException e){
                System.out.println("Unable to automatically create book.txt data file!"); 
                return;
            }
        }
        
        if(!customerDataFile.exists()){
            try{
                customerDataFile.createNewFile();
            }catch(IOException e){
                System.out.println("Unable to automatically create customer.txt data file!"); 
                return;
            }
        }
        
        verified = true;
        
        
        Database.Read(FilePath.book);
        Database.Read(FilePath.customer);
        
        System.out.println("Database successfully initialized"); 
        
    }
    
    /**
     * Reads the current given file and returns it as a datashard object. 
     * When looping through data, remember to typecast to the specific datatype you require
     * @param path
     * @return ArrayList<Datashard>
     */
    public static ArrayList<Datashard> Read(FilePath path){
        
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform read operation!");
        }
        
        // Checks if the book data has already been cached 
        if(path == FilePath.book && bookDataCache.size() > 0)
            return bookDataCache;
        else if(customerDataCache.size() > 0)
            return customerDataCache;
            
        ArrayList<Datashard> cache = new ArrayList<Datashard>();
        String currentLine;
        try{
            fileReader = new FileReader((path == FilePath.book) ? BOOKDATAPATH : CUSTOMERDATAPATH); 
            reader = new BufferedReader(fileReader); 
            currentLine = reader.readLine();
            
            if(currentLine == null){
                return new ArrayList<Datashard>(); 
            }
            
            while(currentLine != null){
                
                // Splits the string 
                String[] formattedData = currentLine.split(","); 
                String name = formattedData[0]; 
                Double value = Double.parseDouble(formattedData[1]); 
                String password = formattedData[2];
                
                // Converts the data into a specified object 
                Datashard newDataShardObject = (path == FilePath.book) ? new BookData(name,value) : new CustomerData(name, value,password); 
                cache.add(newDataShardObject);
                
                // Caches the current object to the specified array cache 
                if(path == FilePath.book)
                    bookDataCache.add(newDataShardObject);
                else
                    customerDataCache.add(newDataShardObject);
                currentLine = reader.readLine(); 
            }
            
            reader.close();
            
            return cache; 
        }catch(IOException e){
            System.out.println("File reader error: " + e);
            return null; 
        }
    }
    
    /**
     * Writes the data to the current specified path 
     * This methods appends to the file. DOES NOT OVERWRITE DATA
     * @param path
     * @param data
     * @return 
     */
    public static boolean Write(CustomerData data){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
        
        try{
            fileWriter = new FileWriter(CUSTOMERDATAPATH,true); 
            writer = new BufferedWriter(fileWriter); 
            
            // Converts the book data into a string 
            writer.write(data.name + "," + data.points + "," + data.password + "\n");
            writer.close();
            customerDataCache.add(data);
            return true; 
        }catch(IOException e){
            System.out.println("File writer error: " + e); 
            return false; 
        }
    }
    
    /**
     * Writes the data to the current specified path 
     * This method overwrites all existing data in a file with the current provided data. 
     * @param path
     * @param data
     * @return 
     */
    public static boolean Write(CustomerData data, boolean overwrite){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
        
        try{
            fileWriter = new FileWriter(CUSTOMERDATAPATH); 
            writer = new BufferedWriter(fileWriter); 
            
            // Converts the book data into a string 
            writer.write(data.name + "," + data.points + "," + data.password + "\n");
            writer.close();
            
            customerDataCache.clear();
            customerDataCache.add(data);
            return true; 
        }catch(IOException e){
            System.out.println("File writer error: " + e); 
            return false; 
        }
    }
    
    /**
     * Writes the data to the current specified path 
     * This method appends to the file. DOES NOT OVERWRITE DATA
     * @param path
     * @param data
     * @return 
     */
    public static boolean Write(BookData data){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
        try{
            fileWriter = new FileWriter(BOOKDATAPATH,true); 
            writer = new BufferedWriter(fileWriter); 
            
            // Converts the book data into a string 
            writer.write(data.name + "," + data.price+ "\n");
            writer.close();
            bookDataCache.add(data);
            return true; 
        }catch(IOException e){
            System.out.println("File writer error: " + e); 
            return false; 
        }
    }
    
    /**
     * Writes the data to the current specified path 
     * This method overwrites all existing data in a file with the current provided data. 
     * @param path
     * @param data
     * @return 
     */
    public static boolean Write(BookData data, boolean overwrite){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
        try{
            fileWriter = new FileWriter(BOOKDATAPATH); 
            writer = new BufferedWriter(fileWriter); 
            
            // Converts the book data into a string 
            writer.write(data.name + "," + data.price+ "\n");
            writer.close();
            
            bookDataCache.clear();
            bookDataCache.add(data);

            return true; 
        }catch(IOException e){
            System.out.println("File writer error: " + e); 
            return false; 
        }
    }
    
    /**
     * Removes a specific book from the data. Only removes the first book found matching the passed in data
     * @param data
     * @return True if the book was deleted, otherwise false if no book of that name was found 
     */
    public static boolean Remove(BookData data){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform remove operation!");
        }
        
        // Searches for the specific book data
        for(int i = 0; i < bookDataCache.size(); i++){
            BookData currentBook = (BookData) bookDataCache.get(i);
            if(currentBook.name.equals(data.name)){
                bookDataCache.remove(i);
                Database.Write(bookDataCache,FilePath.book);
                return true;
            }
        }
        
        return false; 
    }
    
    
    
    /**
     * Removes a specific customer from the data. Only removes the first book found matching the passed in data
     * @param data
     * @return True if the book was deleted, otherwise false if no book of that name was found 
     */
    public static boolean Remove(CustomerData data){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform remove operation!");
        }
        
        // Searches for the specific book data
        for(int i = 0; i < customerDataCache.size(); i++){
            CustomerData currentCustomer = (CustomerData) customerDataCache.get(i);
            if(currentCustomer.name.equals(data.name) && currentCustomer.password.equals(currentCustomer.password)){
                customerDataCache.remove(i);
                Database.Write(customerDataCache,FilePath.customer);
                return true;
            }
        }
        
        return false; 
    }
    
    
 
    /**
     * Clears the specific file of all data
     * @param path
     * @return 
     */
    public static boolean Clear(FilePath path){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform clear operation!");
        }
        if(path == FilePath.book){
            try{
                fileWriter = new FileWriter(BOOKDATAPATH); 
                writer = new BufferedWriter(fileWriter); 
                writer.write("");
                bookDataCache.clear();
            }catch(IOException e){
                System.out.println("Error clearing file: " + e);
                return false; 
            }
        }else{
            try{
                fileWriter = new FileWriter(CUSTOMERDATAPATH); 
                writer = new BufferedWriter(fileWriter); 
                writer.write("");
                customerDataCache.clear();
            }catch(IOException e){
                System.out.println("Error clearing file: " + e);
                return false; 
            }
        }
        return true;
    }
    

    
    public static void main(String[] args){
        Database.Init();
        
        BookData B1 = new BookData("The washed",100);
        BookData B2 = new BookData("Max Dynasty the 7th", 3000);
        CustomerData C1 = new CustomerData("JQ",10000,"12345");
        CustomerData C2 = new CustomerData("Jeff Bezos", 3000000,"Amazon Is Prime");
        
        System.out.println("---------------[TESTING DATA WRITE AND READ]--------------");
        Database.Write(B1);
        System.out.println("Finish writing book 1");
        Database.Write(B2);
        System.out.println("Finished writing book 2"); 
        Database.Write(C1);
        System.out.println("FInished writing customer 1"); 
        Database.Write(C2);
        System.out.println("Finished writing customer 2"); 
        System.out.println(""); 
        
        ArrayList<Datashard> bookData = Database.Read(FilePath.book);
        ArrayList<Datashard> customerData = Database.Read(FilePath.customer);
        for(Datashard abstractData : bookData){
            BookData data = (BookData) abstractData; 
            System.out.println("Book: " + data.name + " " + data.price); 
        }
        
        for(Datashard abstractData : customerData){
            CustomerData data = (CustomerData) abstractData; 
            System.out.println("Customer: " + data.name + " " + data.points + " " + data.password);
        }
        
        if(bookData.size() != 2 || customerData.size() != 2)
            System.out.println("\033[0;31mDATA READ WRITE FAILED \033[0m");
        else
            System.out.println("\033[0;32mDATA READ WRITE PASSED \033[0m");
        
        System.out.println("");
        System.out.println("-----------------[TESTING DATA REMOVING]------------------");
        boolean pass = true; 
        Database.Remove(B1); 
        System.out.println("Removing book 1 from data list"); 
        Database.Remove(C1); 
        System.out.println("Removing customer 1 from data list"); 
        
        bookData = Database.Read(FilePath.book);
        customerData = Database.Read(FilePath.customer);
        
        for(Datashard abstractData : bookData){
            BookData data = (BookData) abstractData; 
            if(data.name.equals(B1.name)){
                System.out.println("\033[0;31m BOOK DATA REMOVING FAILED \033[0m");
                pass = false; 
                break;
            }
        }
       
        for(Datashard abstractData : customerData){
            CustomerData data = (CustomerData) abstractData; 
            if(data.name.equals(C1.name)){
                System.out.println("\033[0;31m CUSTOMER DATA REMOVING FAILED \033[0m");
                pass = false;
                break;
            }
        }
        
        for(Datashard abstractData : bookData){
            BookData data = (BookData) abstractData; 
            System.out.println("Book: " + data.name + " " + data.price); 
        }
        
        for(Datashard abstractData : customerData){
            CustomerData data = (CustomerData) abstractData; 
             System.out.println("Customer: " + data.name + " " + data.points + " " + data.password);
        }
        
        if(pass){
            System.out.println("\033[0;32mDATA REMOVING PASSED \033[0m");
        }
        
        System.out.println("");
        System.out.println("-----------------[TESTING DATA OVERWRITES]----------------");
   
        // Testing overwrites
        Database.Write(B1,true);
        System.out.println("Overwriting book data with book 1");
        Database.Write(C1,true);
        System.out.println("Overwriting customer data with customer 1"); 
        System.out.println(""); 
        
        bookData = Database.Read(FilePath.book);
        customerData = Database.Read(FilePath.customer);
        for(Datashard abstractData : bookData){
            BookData data = (BookData) abstractData; 
            System.out.println("Book: " + data.name + " " + data.price); 
        }
        
        for(Datashard abstractData : customerData){
            CustomerData data = (CustomerData) abstractData; 
             System.out.println("Customer: " + data.name + " " + data.points + " " + data.password);
        }
        
        if(bookData.size() != 1 || customerData.size() != 1)
            System.out.println("\033[0;31mDATA OVERWRITE FAILED \033[0m");
        else
            System.out.println("\033[0;32mDATA OVERWRITE PASSED \033[0m");
        
        System.out.println("");
        System.out.println("-----------------[TESTING DATA CLEARING]----------------");
        
        Database.Clear(FilePath.book);
        System.out.println("Removing all book data"); 
        Database.Clear(FilePath.customer);
        System.out.println("Removing all customer data"); 
        System.out.println("");
        
        bookData = Database.Read(FilePath.book);
        customerData = Database.Read(FilePath.customer);
        for(Datashard abstractData : bookData){
            BookData data = (BookData) abstractData; 
            System.out.println("Book: " + data.name + " " + data.price); 
        }
        
        for(Datashard abstractData : customerData){
            CustomerData data = (CustomerData) abstractData; 
             System.out.println("Customer: " + data.name + " " + data.points + " " + data.password);
        }
       
        if(bookData.size() != 0 || customerData.size() != 0)
            System.out.println("\033[0;31mDATA CLEARING FAILED \033[0m");
        else
            System.out.println("\033[0;32mDATA CLEARING PASSED \033[0m");
        
    }
    
    
    
    
}
