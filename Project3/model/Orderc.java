package model;

/**
 * <h1>Order</h1> Clasa Order defineste notiunile abstracte pentru un obiect de
 * tip chitanta si contine metodele de set si get a fiecarui atribuit a
 * obiectului
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class Orderc {
	private int id;
	private String clientname;
	private float total;

	public Orderc(int id, String clientname, float total) {
		this.id = id;
		this.clientname = clientname;
		this.total = total;
	}

	public Orderc() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", clientname=" + clientname + ", total=" + total + "]";
	}

}