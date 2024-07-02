package com.example.demo.pdfController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.pdfService.PdfServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class MyPdfController {

    @Autowired
    private PdfServiceImpl pdfServiceImpl;

    @PostMapping("/addText")
    public String addText(@RequestParam String filepath, @RequestParam String text) {
        try {
            // Provide the complete output file path where you want to save the modified PDF
            String outputFilePath = "/home/shivamptirmare/Desktop/MyPdfPOutput.pdf";
            pdfServiceImpl.addTextToPdf(filepath, outputFilePath, text);
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
            String outputFilePath = "/home/shivamptirmare/Desktop/MyPdfPOutput.pdf";
            pdfServiceImpl.addImageToPdf(filePath, outputFilePath, imagePath);
            return "Image added successfully";
        } catch (IOException e) {
            return "Error adding image: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
