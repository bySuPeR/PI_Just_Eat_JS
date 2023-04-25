package es.unex.pi.util;

public class OrderSingleton {
	private boolean defineOrder;
	private static OrderSingleton instance;

	private OrderSingleton () {
		defineOrder = true;
	}

	public static OrderSingleton getInstance () {
		if (instance == null) {
			instance = new OrderSingleton ();
		}
		return instance;
	}

	public boolean getOrder () {
		return this.defineOrder;
	}

	//Used to reverse the order every time you click the button
	public void switchOrder () {
		defineOrder = !defineOrder;
	}
}
