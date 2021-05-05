package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Product;

/**
 * Clasa PDFClient genereaza un PDF cu tabela Client in mometul actual
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class PDFProduct {
	ArrayList<Product> c = new ArrayList<Product>();
	private static int i = 1;
	private int id;

	public PDFProduct(ArrayList<Product> c) {
		this.c = c;
		id = i++;
	}

	/**
	 * Metoda este folosita pentru a formata header-ul tabelei Client
	 * 
	 * @param table Este singurul parametru al metodei. Reprezinta tabela a carui
	 *              header va fi formatat
	 */
	private void addTableHeader(PdfPTable table) {
		Stream.of("ID", "Name", "Quantity", "Price").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(3);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	/**
	 * Metoda este folosita pentru a popula tabela cu detaliile corespunzatoare
	 * 
	 * @param table Este singurul parametru al metodei. Reprezinta tabela care va fi
	 *              populata
	 */
	private void addRows(PdfPTable table) {
		for (Product Product : c) {
			table.addCell(Integer.toString(Product.getId()));
			table.addCell(Product.getName());
			table.addCell(Integer.toString(Product.getQuantity()));
			table.addCell(Float.toString(Product.getPrice()));
		}
	}

	/**
	 * Metoda genereaza tabela
	 * 
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void generate() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		String denumire = "Product" + Integer.toString(id) + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(denumire));
		document.open();
		PdfPTable table = new PdfPTable(4);
		addTableHeader(table);
		addRows(table);
		document.add(table);
		document.close();

	}
}
