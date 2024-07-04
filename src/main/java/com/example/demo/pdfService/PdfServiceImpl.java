package com.example.demo.pdfService;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfServiceImpl {

    public void addTextToPdf(String inputFilePath, String outputFilePath, String text) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputFilePath);
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        PdfStamper stamper = new PdfStamper(reader, fos);
        PdfContentByte cb = stamper.getOverContent(2);

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 12);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text, 50, 50, 0);
        cb.endText();

        stamper.close();
        reader.close();
    }

    public void addImageToPdf(String inputFilePath, String outputFilePath, String imagePath) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputFilePath);
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        PdfStamper stamper = new PdfStamper(reader, fos);
        PdfContentByte cb = stamper.getOverContent(2);
        Image image = Image.getInstance(imagePath);
        image.setAbsolutePosition(200, 200);
        cb.addImage(image);

        stamper.close();
        reader.close();
    }

    public void mergePdfs(String filePath1, String filePath2, String outputFilePath) throws IOException, DocumentException {
        PdfReader reader1 = null;
        PdfReader reader2 = null;
        PdfStamper stamper = null;

        try {
            reader1 = new PdfReader(filePath1);
            reader2 = new PdfReader(filePath2);
            stamper = new PdfStamper(reader1, new FileOutputStream(outputFilePath));

            int totalPages1 = reader1.getNumberOfPages();
            int totalPages2 = reader2.getNumberOfPages();

            for (int i = 1; i <= totalPages1; i++) {
                PdfImportedPage page = stamper.getImportedPage(reader1, i);
                stamper.getUnderContent(i).addTemplate(page, 0, 0);
            }

            for (int i = 1; i <= totalPages2; i++) {
                PdfImportedPage page = stamper.getImportedPage(reader2, i);
                stamper.insertPage(totalPages1 + i, reader2.getPageSize(i));
                stamper.getUnderContent(totalPages1 + i).addTemplate(page, 0, 0);
            }

        } finally {
            if (stamper != null) {
                stamper.close();
            }
            if (reader1 != null) {
                reader1.close();
            }
            if (reader2 != null) {
                reader2.close();
            }
        }
    }
}
