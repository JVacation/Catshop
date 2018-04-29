package clients.collection;

import catalogue.Basket;
import clients.cashier.CashierModel;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Observable;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import catalogue.Product;

import java.io.File;

/**
 * Implements the Model of the collection client
 * 
 * @author Mike Smith University of Brighton
 * @version 1.0
 */

public class CollectModel extends Observable {
	private String theAction = "";
	private String theOutput = "";
	private OrderProcessing theOrder = null;
	public boolean run = false;
	public Date today = new Date();
	public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	public File file = new File("record/" + sdf.format(today) + "-CollectionTable.html");

	/*
	 * Construct the model of the Collection client
	 * 
	 * @param mf The factory to create the connection objects
	 */
	public CollectModel(MiddleFactory mf) {
		try //
		{
			theOrder = mf.makeOrderProcessing(); // Process order
		} catch (Exception e) {
			DEBUG.error("%s\n%s", "CollectModel.constructor\n%s", e.getMessage());
		}
	}

	/**
	 * Check if the product is in Stock
	 * 
	 * @param orderNumber
	 *            The order to be collected
	 * @throws FileNotFoundException
	 */

	public void doCollect(String orderNumber) {
		int orderNum = 0;
		String on = orderNumber.trim(); // Product no.
		try {
			orderNum = Integer.parseInt(on); // Convert
		} catch (Exception err) {
			// Convert invalid order number to 0
		}
		try {
			boolean ok = theOrder.informOrderCollected(orderNum);
			if (ok) {
				theAction = "";
				theOutput = "Collected order #" + orderNum;
				if (run == false) {
					PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
					printWriter.println(
							"<TABLE BORDER><TR><TH>Collection order # (In order of time)<TH>Basket (Click Link)<TH>Collection Date / Time</TR>");
					printWriter.close(); // Without this, the output file may be empty
					run = true;
				}
				Date now = new Date();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("E dd.MM.yyyy 'at' hh:mm:ss a zzz");
				PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
				printWriter.println("<TR><TH>" + orderNum + "<TH><a href='" + sdf.format(today) + "/Order-" + orderNum + ".html'>Basket</a> <TH>" 
						+ dateFormatter.format(now) + "</TR>");
				printWriter.close(); // Without this, the output file may be empty
			} else {
				theAction = "No such order to be collected : " + orderNumber;
				theOutput = "No such order to be collected : " + orderNumber;
			}
		} catch (Exception e) {
			theOutput = String.format("%s\n%s", "Error connection to order processing system", e.getMessage());
			theAction = "!!!Error";
		}
		setChanged();
		notifyObservers(theAction);
	}



	/**
	 * The output to be displayed
	 * 
	 * @return The string to be displayed
	 */
	public String getResponce() {
		return theOutput;
	}

}
