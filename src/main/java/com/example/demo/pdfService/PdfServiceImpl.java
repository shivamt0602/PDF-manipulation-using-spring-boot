package com.example.demo.pdfService;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfServiceImpl {

    public void addTextToPdf(String inputFilePath, String outputFilePath, String text) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputFilePath);
        FileOutputStream fos = new FileOutputStream(outputFilePath); // Creates a new output file
        PdfStamper stamper = new PdfStamper(reader, fos);
        PdfContentByte cb = stamper.getOverContent(1);

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 12);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 36, 72, 0);
        cb.endText();

        stamper.close();
        reader.close();
    }

    public void addImageToPdf(String inputFilePath, String outputFilePath, String imagePath) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputFilePath);
        FileOutputStream fos = new FileOutputStream(outputFilePath); // Creates a new output file
        PdfStamper stamper = new PdfStamper(reader, fos);
        PdfContentByte cb = stamper.getOverContent(1);

        Image image = Image.getInstance(imagePath);
        image.setAbsolutePosition(100, 500); // Adjust as needed
        cb.addImage(image);

        stamper.close();
        reader.close();
    }
}
