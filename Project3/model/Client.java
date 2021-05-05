package model;

/**
 * <h1>Client</h1> Clasa Client defineste notiunile abstracte pentru un obiect
 * client si contine metodele de set si get a fiecarui atribuit a obiectului
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class Client {
	private int id;
	private String name;
	private String address;

	public Client(int id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
