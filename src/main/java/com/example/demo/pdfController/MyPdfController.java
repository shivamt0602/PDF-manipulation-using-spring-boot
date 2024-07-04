package com.example.demo.pdfController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.pdfService.PdfServiceImpl;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class MyPdfController {

    @Autowired
    private PdfServiceImpl pdfServiceImpl;

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
}
