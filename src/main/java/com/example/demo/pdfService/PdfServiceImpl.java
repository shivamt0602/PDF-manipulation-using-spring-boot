package com.example.demo.pdfService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import java.io.IOException;


@Service
public class PdfServiceImpl {
	
	 public void addTextToPdf(String dest, String text) throws FileNotFoundException {
	        PdfWriter writer = new PdfWriter(dest);
	        PdfDocument pdf = new PdfDocument(writer);
	        Document document = new Document(pdf);
	        document.add(new Paragraph(text));
	        document.close();
	    }
	 
	 public void addImageToPdf(String dest, String imagePath) throws IOException {
	        PdfWriter writer = new PdfWriter(dest);
	        PdfDocument pdf = new PdfDocument(writer);
	        Document document = new Document(pdf);
	        ImageData data = ImageDataFactory.create(imagePath);
	        Image img = new Image(data);
	        document.add(img);
	        document.close();
	    }
}
