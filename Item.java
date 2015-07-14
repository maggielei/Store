/**
 * This class contains information about an item in the store. The class has a constructor, and mutators and accessor methods 
 * for each variable. This class implements Serializable to save all data.
 * 
 * @author Maggie
 * ID: 1087990364
 * Recitation: 04
 * Homework #6 for CSE 214, Fall 2013
 */
import java.io.Serializable;
public class Item implements Serializable {
	/**
	 * The name of the item. It is at most 25 characters long.
	 */
	private String name;
	/**
	 * The RFID number of the item. It is a 9 digit hexadecimal digit. This is also the key to the hashtable.
	 */
	private String rfidNumber;
	/**
	 * The quantity of the item in the store. It cannot be negative.
	 */
	private int quantity;
	/**
	 * The price of the item in the store. It cannot be negative.
	 */
	private double price;
	
	/**
	 * The constructor for this class. Accepts 2 String variables, an int, and a double parameter to
	 * fill in an item object's information.
	 * @param newName
	 * The name of the item object being created.
	 * @param newRfidNumber
	 * The RFID number of the item object being created.
	 * @param newQuantity
	 * The quantity of the item object being created.
	 * @param newPrice
	 * The price of the item object being created.
	 */
	public Item(String newName, String newRfidNumber, int newQuantity, double newPrice){
		this.name = newName;
		this.rfidNumber = newRfidNumber;
		this.quantity = newQuantity;
		this.price = newPrice;
	}
	
	/**
	 * This method changes the name of the item.
	 * @param newName
	 * The new name of the item.
	 */
	public void setName(String newName){
		this.name = newName;
	}
	/**
	 * This method changes the RFID number of the item.
	 * @param newRfidNumber
	 * The new RFID number of the item.
	 */
	public void setRfidNumber(String newRfidNumber){
		this.rfidNumber = newRfidNumber;
	}
	/**
	 * This method changes the quantity of the item.
	 * @param newQuantity
	 * The new quantity of the item.
	 */
	public void setQuantity(int newQuantity){
		this.quantity = newQuantity;
	}
	/**
	 * This method changes the price of the item.
	 * @param newPrice
	 * The new price of the item.
	 */
	public void setPrice(double newPrice){
		this.price = newPrice;
	}
	/**
	 * This method returns the name of an item.
	 * @return
	 * Returns the name of the item.
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * This method returns the RFID number of an item.
	 * @return
	 * Returns the RFID number of the item.
	 */
	public String getRfidNumber(){
		return this.rfidNumber;
	}
	/**
	 * This method returns the quantity of an item.
	 * @return
	 * Returns the quantity of the item.
	 */
	public int getQuantity(){
		return this.quantity;
	}
	/**
	 * This method returns the price of an item.
	 * @return
	 * Returns the price of the item.
	 */
	public double getPrice(){
		return this.price;
	}	
}