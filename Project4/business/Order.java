package business;

import java.util.Date;

public class Order {
	private int OrderID;
	private Date date;

	public Order(int orderID) {
		this.OrderID = orderID;
		this.date = new Date();
	}

	public int getOrderID() {
		return OrderID;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return OrderID + ", " + date;
	}

	@Override
	public int hashCode() {
		return OrderID;
	}

}
