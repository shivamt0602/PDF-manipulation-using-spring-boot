package com.example.demo.pdfController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.pdfService.PdfServiceImpl;
import com.example.demo.pdfService.PdfSigningService;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class MyPdfController {

    @Autowired
    private PdfServiceImpl pdfServiceImpl;

    @Autowired
    private PdfSigningService pdfSigningService;

    @PostMapping("/addText")
    public String addText(@RequestParam String filePath, @RequestParam String text) {
        try {
            // Provide the complete output file path where you want to save the modified PDF
            String outputFilePath = "/home/shivamptirmare/Desktop/textop1.pdf";
            pdfServiceImpl.addTextToPdf(filePath, outputFilePath, text);
            return "Text added successfully";
        } catch (FileNotFoundException e) {
            return "File not found: " + e.getMessage();
        } catch (IOException e) {
            return "Error adding text: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/addImage")
    public String addImage(@RequestParam String filePath, @RequestParam String imagePath) {
        try {
            // Provide the complete output file path where you want to save the modified PDF
            String outputFilePath = "/home/shivamptirmare/Desktop/imageop1.pdf";
            pdfServiceImpl.addImageToPdf(filePath, outputFilePath, imagePath);
            return "Image added successfully";
        } catch (IOException e) {
            return "Error adding image: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/merge")
    public String mergePdfs(@RequestParam String filePath1, @RequestParam String filePath2) {
        try {
            // Provide the complete output file path where you want to save the merged PDF
            String outputFilePath = "/home/shivamptirmare/Desktop/mergedop1.pdf";
            pdfServiceImpl.mergePdfs(filePath1, filePath2, outputFilePath);
            return "PDFs merged successfully";
        } catch (IOException | DocumentException e) {
            return "Error merging PDFs: " + e.getMessage();
        }
    }

    @PostMapping("/sign")
    public String signPdf(@RequestParam String src, @RequestParam String dest, @RequestParam String keystorePath, @RequestParam String keystorePassword, @RequestParam String alias) {
        try {
            pdfSigningService.signPdf(src, dest, keystorePath, keystorePassword, alias);
            return "PDF signed successfully";
        } catch (Exception e) {
            return "Error signing PDF: " + e.getMessage();
        }
    }
}
