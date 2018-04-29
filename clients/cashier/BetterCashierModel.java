package clients.cashier;

import java.io.Serializable;
import catalogue.Basket;
import catalogue.BetterBasket;
import middle.MiddleFactory;

public class BetterCashierModel extends CashierModel implements Serializable {
	

	public BetterCashierModel(MiddleFactory mf) {
		super(mf);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected Basket makeBasket() {
		
		return new BetterBasket();
       

	}
}
