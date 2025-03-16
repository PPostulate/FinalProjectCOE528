/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

/**
 *
 * @author szyang
 */
public final class CustomerData extends Datashard{
    public double points;
    public String password; 
    
    public CustomerData(String name, double points, String password){
        super(name);
        this.points = points; 
        this.password = password; 
    }
    
    public char getStatus(){
        return (points < 1000)? ('S'):('G');
    }
}

