package com.example.demo.pdfService;

//import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

@Service
public class PdfSigningService {

    public void signPdf(String src, String dest, String keystorePath, String keystorePassword, String alias) throws Exception {
        // Load the keystore
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

        // Get the private key and certificate
        PrivateKey pk = (PrivateKey) ks.getKey(alias, keystorePassword.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);

        // Reader and Stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');

        // Appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason("Example Reason");
        appearance.setLocation("Example Location");
        appearance.setVisibleSignature("sig");

        // Signature
        ExternalDigest digest = new BouncyCastleDigest();
        ExternalSignature signature = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, "BC");
        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
    }
}
