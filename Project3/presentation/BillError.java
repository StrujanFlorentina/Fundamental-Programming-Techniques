package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.OrderItem;

/**
 * <h1>Bill Error</h1> Clasa Bill genereaza o instiintare de esec a tranzactiei
 * unui client, precum si data acestuia
 * 
 * @author FlorentinaStrujan
 * @since 2020-04-12
 */
public class BillError {
	OrderItem o = new OrderItem();

	public BillError(OrderItem o) {
		this.o = o;
	}

	/**
	 * Metoda este folosita pentru a generarea instiintarii scrise
	 */
	public void generate() throws FileNotFoundException, DocumentException {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
		Document document = new Document();
		String denumire = "Bill for client " + o.getClient() + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(denumire));

		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 22, BaseColor.BLACK);
		Chunk chunk = new Chunk("    The transaction desired by " + o.getClient() + " could not be processed.", font);
		Chunk c = new Chunk("Transaction Date: " + ft.format(dNow), font);
		document.add(new Paragraph("\n\n"));
		document.add(chunk);
		document.add(new Paragraph("\n\n"));
		document.add(c);
		document.close();
	}

}
