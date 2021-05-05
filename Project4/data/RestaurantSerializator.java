package data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import business.restaurant.Restaurant;

public class RestaurantSerializator {

	public RestaurantSerializator() {
	}

	public void serialize(Restaurant r) {
		try {
			FileOutputStream fileOut = new FileOutputStream("restaurant.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(r);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in restaurant.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
