package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.OrderItem;
import model.Orderc;

/**
 * <h1>Bill</h1> Clasa Bill genereaza o chitanta pentru un client, afisant un
 * tabel cu produsele achizitionate si cantitatea acestora,data achizitiei,
 * precum si un mesaj ce contine numele clientului si suma totala cheltuita
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class Bill {
	Orderc o = new Orderc();
	ArrayList<OrderItem> ord = new ArrayList<OrderItem>();

	public Bill(Orderc o, ArrayList<OrderItem> ord) {
		this.o = o;
		this.ord = ord;
	}

	/**
	 * Metoda este folosita pentru a formata header-ul tabelei ce contine produsele
	 * si cantitatea acestora
	 * 
	 * @param table Este singurul parametru al metodei. Reprezinta tabela a carui
	 *              header va fi formatat
	 */
	private void addTableHeader(PdfPTable table) {
		Stream.of("Product Name", "Product Quantity").forEach(columnTitle -> {
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
		for (OrderItem ord : ord) {
			table.addCell(ord.getProductName());
			table.addCell(Integer.toString(ord.getProductQuantity()));
		}
	}

	/**
	 * Metoda genereaza chitanta
	 * 
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void generate() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
		String denumire = "Bill for client " + o.getClientname() + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(denumire));
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(20f);
		table.setSpacingAfter(20f);
		addTableHeader(table);
		addRows(table);
		document.open();
		document.add(table);
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("The client " + o.getClientname() + " bought products worth " + o.getTotal() + " €.",
				font);
		Chunk c = new Chunk("Acquisition Date: " + ft.format(dNow), font);
		document.add(new Paragraph("\n\n"));
		document.add(chunk);
		document.add(new Paragraph("\n\n"));
		document.add(c);
		document.close();
	}
}
