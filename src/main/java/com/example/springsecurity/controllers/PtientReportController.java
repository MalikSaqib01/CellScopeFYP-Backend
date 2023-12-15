package com.example.springsecurity.controllers;


import com.amazonaws.services.s3.AmazonS3;
import com.example.springsecurity.models.PatientReport;
import com.example.springsecurity.payload.request.ReportRequest;
import com.example.springsecurity.repository.PatientReportRepository;
import com.example.springsecurity.service.MailService;
import com.example.springsecurity.service.PatientReportService;
import com.example.springsecurity.service.StorageService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/reports")
@CrossOrigin("*")
public class PtientReportController {

    @Autowired
    private StorageService storageService;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;


    @Autowired
    private MailService mailService;
 
    @Autowired
    private PatientReportRepository patientReportRepository;
    @Autowired
    private PatientReportService patientReportService;

    @GetMapping("/getAddress")
    public List<PatientReport> getReport() {
        List<PatientReport> report =  patientReportRepository.findAll();
        return  report;
    }

    @GetMapping("/get-report-data/{Id}")
    public PatientReport getReport(@PathVariable("Id") Long Id) {
        List<PatientReport> reports =  patientReportRepository.findByAppointmentId(Id);
        PatientReport report = reports.get(reports.size()-1);
        return  report;
    }


//    @PostMapping("/jasperpdf/result")
//    public String createPDF(@RequestBody ReportRequest reportRequest, HttpServletResponse response  ) throws IOException, JRException {
//
////        System.out.println(reportRequest.getEmail());
//
//        return patientReportService.exportJasperReport(reportRequest , response );
//
//
//          //File file = service.exportJasperReport(response);
////          System.out.println(file.getName());
////        MultipartFile multipartFile = convertFileToMultipartFile(file);
////       return storageService.uploadFile(multipartFile, "saqii8872@gmail.com", "Report Medical", "");
//
//
//
//    }


     // Replace with the actual path to your PDF directory

    @GetMapping("/pdf/{filename}")
    public ResponseEntity<Resource> getPdfFile(@PathVariable String filename) {
        try {

            filename = filename + ".pdf";

             String pdfDirectoryPath = "C:\\Users\\hp\\Downloads\\";
            // Construct the file path
            Path filePath = Path.of(pdfDirectoryPath, filename);

            // Load the file as a resource
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists
            if (resource.exists() && resource.isReadable()) {
                // Set the content type as PDF
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", filename);

                // Return the file as a ResponseEntity
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                // File not found
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., file not found, permission issues)
            return ResponseEntity.status(500).body(null);
        }
    }


//    @PostMapping("/create-report-data")
@PostMapping("/pdf/create-data")
public ResponseEntity<PatientReport> createReportData(@RequestBody ReportRequest reportRequest) {
        try {
            PatientReport createdReport = patientReportService.exportReportRecord(reportRequest);
            return new ResponseEntity<PatientReport>(createdReport, HttpStatus.CREATED);
        } catch (Exception e) {
            // You can handle exceptions and return appropriate status codes
            return new ResponseEntity<PatientReport>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    }