package goldstar_backend.util;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;

public class PdfGenerator {

    public static String generateLookbook(String title,
                                          String customerName,
                                          String outfitName) throws IOException {

        File folder = new File("lookbooks");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = "lookbooks/" +
                System.currentTimeMillis() +
                "_lookbook.pdf";

        PdfWriter writer = new PdfWriter(fileName);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        document.add(new Paragraph("GOLDSTAR"));
        document.add(new Paragraph("----------------------------"));

        document.add(new Paragraph("Lookbook Title : " + title));

        document.add(new Paragraph("Customer : " + customerName));

        document.add(new Paragraph("Outfit : " + outfitName));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("AI Render Images will appear here."));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("Powered by Goldstar"));

        document.close();

        return fileName;
    }

}