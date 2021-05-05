package presentation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>Reading from file</h1> Clasa ReadingFromFile citeste dintr-un fisier
 * informatiile, linie cu linie, dupa care le amplaseaza intr-un vector de
 * Strin-uri
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class ReadingFromFile {
	ArrayList<String> line = new ArrayList<String>();
	String st;

	public void citeste(String fisier) {
		try {
			File file = new File(fisier);
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null)
				line.add(st);
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fisierul nu a fost gasit");
		} catch (IOException e) {
			System.out.println("Eroare la citire");
		}
	}

	public ArrayList<String> getLines() {
		return line;
	}

}