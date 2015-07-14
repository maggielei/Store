/**
 * This class contains the main method and uses the Java API HashTable that maps the RFID numbers to an Item object.
 * The RFID number serves as the key and the item object serves as a value. The program checks to see whether or not
 * a previous save file is available. If there is no file found, an empty HashTable is created and initialized.
 * 
 * @author Maggie
 * ID: 1087990364
 * Recitation: 04
 * Homework #6 for CSE 214, Fall 2013
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Scanner;
public class Store {
	/**
	 * Creates a static Hashtable that accepts a String for a key and an Item object for a value.
	 */
	static Hashtable<String, Item> store;
	/**
	 * Initializes a double variable that stores the total amount purchased for the option "Go shopping"
	 */
	static double totalCost = 0;
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		try{
			FileInputStream file = new FileInputStream("mySaveFile.obj");
			ObjectInputStream fin = new ObjectInputStream(file);
			store = (Hashtable) fin.readObject();
			file.close();
			System.out.println("Successfully loaded the items:");
			System.out.printf("%-9s %-25s %-10s %-10s \n", "RFID", "Name", "Quantity", "Price");
			System.out.println("----------------------------------------------------");
			Object[] items = store.values().toArray();
			for(int i = 0; i < items.length; i++){
				System.out.printf("%-9s %-25s %-10d $%-5.2f \n \n", ((Item) items[i]).getRfidNumber(), ((Item) items[i]).getName(), ((Item) items[i]).getQuantity(), ((Item) items[i]).getPrice());
			}
		}
		catch(IOException a){
			System.out.println("items.obj not found, beginning with an empty has table... \n");
			store = new Hashtable();
		}
		catch(ClassNotFoundException c){
		}
		printMenu();
		String ans = s.nextLine();
		while(!ans.equalsIgnoreCase("q")){
			/**
			 * The option to add an item into the store. Prompts user for information and creates an Item object
			 * with the information acquired. Limits are placed on each input so that rules are not violated.
			 * (25 character limit, negative prices, and negative quantities)
			 * If an item with an identical RFID number to another item is entered, the program forbids the user from
			 * adding the new item.
			 */
			if(ans.equalsIgnoreCase("i")){
				System.out.println("Inserting an item into the store...");
				System.out.println("Enter the name of the item: ");
				String name = s.nextLine();
				while(name.length() > 25){
					System.out.println("Name cannot be longer than 25 characters. Try again.");
					name = s.nextLine();
				}
				System.out.println("RFID Number: ");
				String rfid = s.nextLine();
				rfid = rfid.toUpperCase();
				while(rfid.length() != 9 || checkHex(rfid) == false){
					System.out.println("RFID Number must be a 9 digit hexadecimal number. Try again.");
					rfid = s.nextLine();
				}
				System.out.println("Price: ");
				double price = s.nextDouble();
				while(price < 0.0){ 
					System.out.println("Price cannot be negative. Try again.");
					price = s.nextDouble();
				}
				System.out.println("Quantity: ");
				int quantity = s.nextInt();
					while(quantity < 0){
						System.out.println("Quantity cannot be negative. Try again.");
						quantity = s.nextInt();
					}	
				if(!store.containsKey(rfid)){
					Item newItem = new Item(name, rfid.toUpperCase(), quantity, price);
					System.out.println(quantity + " " + name + " have been added to the inventory"
							+ " with RFID number " + rfid + " and at a price of $" + String.format("%-5.2f",price) + ". \n");
					store.put(rfid, newItem);
					printMenu();
					ans = s.nextLine();
				}
				else{
					System.out.println("An item with that RFID number already exists! Try again. \n");
					printMenu();
					ans = s.nextLine();
				}
			}
			/**
			 * Changes the quantity and price of an already existing Item object. Both variables are limited to
			 * only positive values. If the RFID number entered does not match any existing RFID number, the user is
			 * asked to try again.
			 */
			if(ans.equalsIgnoreCase("a")){
				System.out.println("Adding to the inventory...");
				System.out.println("RFID Number: ");
				String rfid = s.nextLine();
				rfid = rfid.toUpperCase();
				while(rfid.length() != 9 || checkHex(rfid) == false){
					System.out.println("RFID Number must be a 9 digit hexadecimal number. Try again.");
					rfid = s.nextLine();
				}
				System.out.println("New Price: ");
				double price = s.nextDouble();
				System.out.println("Additional Quantity: ");
				int quantity = s.nextInt();
				if(store.containsKey(rfid)){
					store.get(rfid).setQuantity(store.get(rfid).getQuantity() + quantity);
					store.get(rfid).setPrice(price);
					System.out.println("There are now " + store.get(rfid).getQuantity() + " " + store.get(rfid).getName() 
							+ " in the store with a price of $" + String.format("%-5.2f",store.get(rfid).getPrice()) + ".");
				}
				else{
					System.out.println("There is no such item with that RFID number! Try again. \n");
					printMenu();
					ans = s.next();
				}
			}
			/**
			 * Removes an item from the store. Removes all quantities of the Item object with the entered RFID number.
			 * If there are no items in the store with the entered RFID number, a statement is printed for the user.
			 */
			if(ans.equalsIgnoreCase("r")){
				System.out.println("Removing an item from inventory...");
				System.out.println("Select the RFID Number of the item to be removed: ");
				String rfid = s.nextLine();
				rfid = rfid.toUpperCase();
				while(rfid.length() != 9 || checkHex(rfid) == false){
					System.out.println("RFID Number must be a 9 digit hexadecimal number. Try again.");
					rfid = s.nextLine();
				}
				if(store.containsKey(rfid)){
					if(store.get(rfid).getQuantity() == 0){
						System.out.println("Current quantity of the item is 0. Cannot remove. \n");
						printMenu();
					}
					else{
						System.out.println("Successfully removed all " + store.get(rfid).getName() + " from the store. \n");
						store.remove(rfid);
						printMenu();
					}
				}
				else{
					System.out.println("There are currently no items in the store with the given RFID number. Try again. \n");
					printMenu();
				}
			}
			/**
			 * Returns information about an Item object according to its RFID number entered. If there are no items
			 * in the store with the entered RFID number, a statement is printed for the user.
			 */
			if(ans.equalsIgnoreCase("s")){
				System.out.println("Searching for information about an item...");
				System.out.println("Enter its RFID: ");
				String rfid = s.nextLine();
				rfid = rfid.toUpperCase();
				while(rfid.length() != 9 || checkHex(rfid) == false){
					System.out.println("RFID Number must be a 9 digit hexadecimal number. Try again.");
					rfid = s.nextLine();
					rfid = rfid.toUpperCase();
				}
				if(store.containsKey(rfid)){
					System.out.println(store.get(rfid).getQuantity() + " " + store.get(rfid).getName() + " in the store, currently"
							+ " at a price of $" + String.format("%-5.2f",store.get(rfid).getPrice()) + ". \n");
					printMenu();
				}
				else{
					System.out.println("There are currently no items in the store with the given RFID number. Try again. \n");
					printMenu();
				}
			}
			/**
			 * "Purchases" items from the store. This option shows the user what's available for purchase. If the user enters '0',
			 * the option ends. If the user enters an RFID number, they will add items to their shopping cart. A total price 
			 * is updated every item added into the cart. When you enter '0', the program shows the user how much they
			 * need to pay for all their items. 
			 */
			if(ans.equalsIgnoreCase("g")){
				if(store.isEmpty()){
					System.out.println("There are no items in the store!\n");
					printMenu();
				}
				else{
					System.out.println("\nList of items in the store:");
					System.out.printf("%-9s %-25s %-10s %-10s \n", "RFID", "Name", "Quantity", "Price");
					System.out.println("----------------------------------------------------");
					Object[] items = store.values().toArray();
					for(int i = 0; i < items.length; i++){
						System.out.printf("%-9s %-25s %-10d $%-5.2f \n \n", ((Item) items[i]).getRfidNumber(), ((Item) items[i]).getName(), ((Item) items[i]).getQuantity(), ((Item) items[i]).getPrice());
					}
					System.out.println("Enter a RFID number to purchase (0 to finish):");
					ans = s.nextLine();
					
					while(!ans.equalsIgnoreCase("0")){
						String rfid = ans;
						rfid = rfid.toUpperCase();
						while(rfid.length() != 9 || checkHex(rfid) == false){
							System.out.println("RFID Number must be a 9 digit hexadecimal number. Try again.");
							rfid = s.nextLine();
							rfid = ans.toUpperCase();
						}
						if(store.containsKey(rfid)){
							System.out.println("Enter quantity:");
							int purchase = s.nextInt();
							while(store.get(rfid).getQuantity() - purchase < 0){
								System.out.println("Error: There is not enough of that item in the store. Try again.");
								purchase = s.nextInt();
							}
							double cost = store.get(rfid).getPrice() * purchase;
							totalCost += cost;
							store.get(rfid).setQuantity(store.get(rfid).getQuantity() - purchase);
							System.out.println("You have selected " + purchase + " " + store.get(rfid).getName() + 
									" which totals to $" + String.format("%-5.2f",cost ) + ".");
							System.out.println("Your total so far is: $" + String.format("%-5.2f",totalCost));
							System.out.println("Enter a RFID number to purchase (0 to finish):");
							ans = s.next();
							}
						else{
							System.out.println("There is no item with this RFID number. Try again. \n");
							rfid = s.nextLine();
						}
					}
					System.out.println("Finished shopping! Please pay the cashier $" + String.format("%-5.2f",totalCost) + "\n");
					totalCost = 0;
					printMenu();
				}
			}
			ans = s.nextLine();
		}
		System.out.println("The file, items.obj, has successfully been saved.");
		try{
			FileOutputStream file = new FileOutputStream("mySaveFile.obj");
			ObjectOutputStream fout = new ObjectOutputStream(file);
			fout.writeObject(store);
			fout.close();
		}
		catch(IOException a){
		}
	}
	/**
	 * A method that prints the menu options neatly.
	 */
	public static void printMenu(){
		System.out.println("Please select a menu option");
		System.out.println("I) Insert an item into the store");
		System.out.println("A) Add to the inventory for a given item");
		System.out.println("R) Remove an item from the store");
		System.out.println("S) Search for information about an item");
		System.out.println("G) Go shopping");
		System.out.println("Q) Quit and save");
		System.out.println("Select an option: ");
	}
	/**
	 * A method that check whether or not its String parameter is a hexadecimal.
	 * @param hex
	 * The String being validated.
	 * @return
	 * Returns true if the String is a hexadecimal number. Returns false if the String is
	 * not a hexadecimal number.
	 */
	private static boolean checkHex(String hex){
		try{
		long value = Long.parseLong(hex, 16);
			return true;
		}
		catch(Exception a){
			return false;
		}
	}
}
