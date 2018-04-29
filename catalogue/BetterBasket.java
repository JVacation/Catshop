package catalogue;

import java.io.Serializable;
import java.util.Collections;

/**
 * Write a description of class BetterBasket here.
 * 
 * @author James Holliday
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(Product pr) {
        /** For loop runs through the size of the basket */
		for (int i = 0; i < this.size(); i++) {
			/** Checks if the product numbers of the items in the basket match the one we are adding one by one */
			if (this.get(i).getProductNum().equals(pr.getProductNum())) {
				/** If they match than the quantity is set and returns */
				this.get(i).setQuantity(this.get(i).getQuantity() + pr.getQuantity());
				return true;
			}
		}
		/** If they match than the quantity is set and returns */
		productNumberSort(pr);
		return true;
		
	}
	
	public void productNumberSort (Product product) {
		super.add(product);
		Collections.sort(this, (p1, p2) -> p1.getProductNum().compareTo(p2.getProductNum())); 
	}
}






