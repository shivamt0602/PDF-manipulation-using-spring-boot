package com.example.demo.pdfService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import com.itextpdf.text.pdf.*;


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

    public  void signPdf(String src, String dest, String keystorePath, String keystorePassword, String alias) throws IOException, DocumentException, GeneralSecurityException {
        // Load the keystore
    	System.out.println(src);
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

        // Get the private key and certificate chain
        PrivateKey pk = (PrivateKey) ks.getKey(alias, keystorePassword.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);

        // Read the existing PDF
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');

        // Create appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason("Example Reason");
        appearance.setLocation("Example Location");

        // Create a new blank signature field on page 1
        Rectangle rect = new Rectangle(50, 100, 220, 140);
        PdfFormField sigField = PdfFormField.createSignature(stamper.getWriter());
        sigField.setWidget(rect, PdfAnnotation.HIGHLIGHT_INVERT);
        sigField.setFlags(PdfAnnotation.FLAGS_PRINT);
        sigField.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g"));
        sigField.setFieldName("signature");
        sigField.setPage();
        stamper.addAnnotation(sigField, 1);

        // Set the visible signature on the new field
        appearance.setVisibleSignature(rect, 1, "signature");

        // Create digest
        ExternalDigest digest = new BouncyCastleDigest();

        // Create signature
        ExternalSignature signature = new PrivateKeySignature(pk, "SHA-256", "BC");

        // Sign the document
        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

        // Close resources
        stamper.close();
        reader.close();
    }
}
