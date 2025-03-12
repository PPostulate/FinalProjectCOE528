/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

/**
 *
 * @author szyang
 */
public sealed abstract class Datashard permits BookData, CustomerData{
    public String name;
    
    public Datashard(String name){
        this.name = name; 
    }
}
