package com.example.demo.pdfService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfServiceImpl {

    public void addTextToPdf(String filePath, String text) throws IOException, DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Paragraph paragraph = new Paragraph(text);
            document.add(paragraph);
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    public void addImageToPdf(String filePath, String imagePath) throws IOException, DocumentException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Image image = Image.getInstance(imagePath);
            document.add(image);
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
