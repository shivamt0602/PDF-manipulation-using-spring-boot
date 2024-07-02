package com.example.demo.pdfController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.pdfService.PdfServiceImpl;

@RestController
@RequestMapping("/pdf")
public class MyPdfController {
	
	 @Autowired
	 private PdfServiceImpl pdfserviceimpl;

	    @PostMapping("/addText")
	    public String addText(@RequestParam String filepath, @RequestParam String text) {
	        try {
	            pdfserviceimpl.addTextToPdf(filepath, text);
	            return "Text added successfully";
	        } catch (FileNotFoundException e) {
	            return "File not found: " + e.getMessage();
	        }
	    }

	    @PostMapping("/addImage")
	    public String addImage(@RequestParam String filePath, @RequestParam String imagePath) {
	        try {
	        	pdfserviceimpl.addImageToPdf(filePath, imagePath);
	            return "Image added successfully";
	        } catch (IOException e) {
	            return "Error adding image: " + e.getMessage();
	        }
	    }
}
