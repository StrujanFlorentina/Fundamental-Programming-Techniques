package business.menuitem;

public class BaseProduct implements MenuItem {
	private static final long serialVersionUID = 1L;
	private String name;
	private int price;

	public BaseProduct(String name, int price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int computePrice() {
		return price;
	}

	@Override
	public void editName(String newName) {
		this.name = newName;
	}

	@Override
	public String getItems() {
		return name;
	}

	@Override
	public String toString() {
		return name + ", price: " + price;
	}

}
