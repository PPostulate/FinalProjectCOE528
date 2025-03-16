/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

/**
 *
 * @author szyang
 */
public final class BookData extends Datashard{
    public double price; 
    public boolean selected = false;
    
    public BookData(String name, double price){
        super(name); 
        this.price = price; 
    }
}
