package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import business.restaurant.Restaurant;

public class RestaurantDeserializator {

	public RestaurantDeserializator() {
	}

	public void deserialize(Restaurant r) {
		try {
			FileInputStream fileIn = new FileInputStream("restaurant.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			r = (Restaurant) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("TotalItems class not found");
			c.printStackTrace();
			return;
		}
	}

}
