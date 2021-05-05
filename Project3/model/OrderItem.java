package model;

/**
 * <h1>Order Item</h1> Clasa OrderItem defineste notiunile abstracte pentru un
 * obiect de tip coada de ordine si contine metodele de set si get a fiecarui
 * atribuit a obiectului
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class OrderItem {
	private int id;
	private String productName;
	private int productQuantity;
	private String client;

	public OrderItem(int id, String productName, int productQuantity, String client) {
		this.id = id;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.client = client;
	}

	public OrderItem() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", productName=" + productName + ", productQuantity=" + productQuantity
				+ ", client=" + client + "]";
	}

}
