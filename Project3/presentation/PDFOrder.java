package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.OrderItem;
import model.Orderc;

/**
 * Clasa PDFClient genereaza un PDF cu tabela Client in mometul actual
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class PDFOrder {
	ArrayList<Orderc> c = new ArrayList<Orderc>();
	ArrayList<OrderItem> o = new ArrayList<OrderItem>();
	private static int i = 1;
	private int id;

	public PDFOrder(ArrayList<Orderc> c, ArrayList<OrderItem> o) {
		this.c = c;
		this.o = o;
		id = i++;
	}

	/**
	 * Metoda este folosita pentru a formata header-ul tabelei OrderItem
	 * 
	 * @param table Este singurul parametru al metodei. Reprezinta tabela a carui
	 *              header va fi formatat
	 */
	private void addTable1Header(PdfPTable table) {
		Stream.of("ID Order Item", "Product Name", "Product Quantity", "Client Name").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(3);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	/**
	 * Metoda este folosita pentru a formata header-ul tabelei Order
	 * 
	 * @param table Este singurul parametru al metodei. Reprezinta tabela a carui
	 *              header va fi formatat
	 */
	private void addTable2Header(PdfPTable table) {
		Stream.of("ID Order", "Client Name", "Total").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.GREEN);
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
	private void add1Rows(PdfPTable table) {
		for (OrderItem ord : o) {
			table.addCell(Integer.toString(ord.getId()));
			table.addCell(ord.getProductName());
			table.addCell(Integer.toString(ord.getProductQuantity()));
			table.addCell(ord.getClient());
		}
	}

	/**
	 * Metoda este folosita pentru a popula a doua tabela cu detaliile
	 * corespunzatoare
	 * 
	 * @param table Este singurul parametru al metodei. Reprezinta tabela care va fi
	 *              populata
	 */
	private void add2Rows(PdfPTable table) {
		for (Orderc or : c) {
			table.addCell(Integer.toString(or.getId()));
			table.addCell(or.getClientname());
			table.addCell(Float.toString(or.getTotal()));
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
		String denumire = "Oder" + Integer.toString(id) + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(denumire));
		document.open();
		PdfPTable table1 = new PdfPTable(4);
		table1.setSpacingBefore(15f);
		table1.setSpacingAfter(15f);
		addTable1Header(table1);
		add1Rows(table1);
		document.add(table1);
		document.add(new Paragraph(""));
		PdfPTable table2 = new PdfPTable(3);
		table2.setSpacingBefore(15f);
		table2.setSpacingAfter(15f);
		addTable2Header(table2);
		add2Rows(table2);
		document.add(table2);
		document.close();

	}

	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		ArrayList<OrderItem> q = new ArrayList<OrderItem>();
		OrderItem a = new OrderItem(1, "mere", 50, "Ana");
		q.add(a);
		ArrayList<Orderc> s = new ArrayList<Orderc>();
		Orderc w = new Orderc(3, "Mihai", 20);
		s.add(w);
		PDFOrder g = new PDFOrder(s, q);
		g.generate();

	}
}
