package business.menuitem;

import java.io.Serializable;

public interface MenuItem extends Serializable {

	public int computePrice();

	public void editName(String newName);

	public String getName();

	public String getItems();

	@Override
	public String toString();
}
