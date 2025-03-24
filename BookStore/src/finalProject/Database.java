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
    
    private static String BOOKDATAPATH = "src/finalProject/Data/books.txt";
    private static String CUSTOMERDATAPATH = "src/finalProject/Data/customers.txt";
  
    private static final ArrayList<Datashard> bookDataCache = new ArrayList<>(); 
    private static final ArrayList<Datashard> customerDataCache = new ArrayList<>(); 
   
    
    // Private Methods 
    
    private static boolean Write(FilePath path){
        try{
            
            fileWriter = new FileWriter((path == FilePath.book) ? BOOKDATAPATH : CUSTOMERDATAPATH);
            writer = new BufferedWriter(fileWriter);
            
            if(path == FilePath.book){
                
                for(Datashard abstractData : bookDataCache){
                    BookData currentBook = (BookData) abstractData; 
                    writer.write(currentBook.name + "," + currentBook.price + "\n");
                }
                writer.close();
            }else{
                for(Datashard abstractData : customerDataCache){
                    CustomerData customerData = (CustomerData) abstractData; 
                    writer.write(customerData.name + "," + customerData.points + "," + customerData.password +"\n");
                }
                writer.close();
            }
            return true; 
        }catch(IOException e){
            System.out.println("Failed to write data array"); 
            return false; 
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
        
        try{
            Database.Read(FilePath.book);
            Database.Read(FilePath.customer);
        }catch(Exception e){
            verified = false; 
            System.out.println("Database failed to read from data files"); 
            System.out.println("Error: " + e); 
            return; 
        }
            
        System.out.println("Database successfully initialized"); 
        
    }
    
    /**
     * Initializes the database caches and verifies all data files are present
     * Database is initialized under testMode to avoid the primary database
     * @param testMode 
     */
    public static void Init(boolean testMode){
        if(!testMode){
            throw new IllegalArgumentException("Cannot pass argument false in test"); 
        }
        
        BOOKDATAPATH = "src/finalProject/Data/booksTEST.txt";
        CUSTOMERDATAPATH = "src/finalProject/Data/customerTEST.txt";
        
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
        
        bookDataFile.deleteOnExit(); 
        customerDataFile.deleteOnExit(); 
        
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
        if(path == FilePath.book && bookDataCache.size() > 0){
            return bookDataCache;
        }
        else if(path == FilePath.customer && customerDataCache.size() > 0){
            return customerDataCache;
        }
           
            
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
                String password = "";
                Double value = Double.parseDouble(formattedData[1]); 
                if (formattedData.length > 2){
                    password = formattedData[2];
                }
                
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
     * This method appends data to the current customer data cache. DOES NOT WRITE TO FILES
     * @param path
     * @param data
     * @return 
     */
    public static boolean Write(CustomerData data){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
        customerDataCache.add(data);
        return true; 
    }
    
    /**
     * Writes the data to the current specified path 
     * This method overwrites all data in the customer data cache with the current data provided. DOES NOT WRITE TO FILE. 
     * @param overwrite
     * @param data
     * @return 
     */
    public static boolean Write(CustomerData data, boolean overwrite){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }

        customerDataCache.clear();
        customerDataCache.add(data);
        return true; 
    }
    
    /**
     * Writes the data to the current specified path 
     * This method appends data into the book data cache. DOES NOT WRITE TO FILE
     * @param data
     * @return 
     */
    public static boolean Write(BookData data){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
 
        bookDataCache.add(data);
        return true; 
    }
    
    /**
     * Writes the data to the current specified path 
     * This method overwrites all data in the book data cache with the current data provided. DOES NOT WRITE TO FILE. 
     * @param overwrite
     * @param data
     * @return 
     */
    public static boolean Write(BookData data, boolean overwrite){
        if(!verified){
            throw new RuntimeException("Database has not been initialized. Cannot perform write operation!");
        }
        bookDataCache.clear();
        bookDataCache.add(data);
        return true; 
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
            bookDataCache.clear();
        }else{
            customerDataCache.clear();
        }
        return true;
    }
    
    
    /**
     * Flushes all changes made to the data caches into the file and saves it 
     * @param path
     * @return 
     */
    public static void Flush(){
        Database.Write(FilePath.customer);
        Database.Write(FilePath.book); 

    }
    
    public static boolean compareBook (String b){
        for (Datashard a : bookDataCache){
            BookData c = (BookData) a;
            if((c.name.equals(b))){
                return false;
            }
        }
        return true;
    }
    
    public static boolean compareCustomer (String b){
        for (Datashard a : customerDataCache){
            CustomerData c = (CustomerData) a;
            if(c.name.equals(b)){
                return false;
            }
        }
        return true;
    }

}
