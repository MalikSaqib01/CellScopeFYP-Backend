package com.example.springsecurity.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.springsecurity.models.Appointment;
import com.example.springsecurity.models.PatientReport;
import com.example.springsecurity.payload.request.ReportRequest;
import com.example.springsecurity.repository.AppointmentRepository;
import com.example.springsecurity.repository.PatientReportRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PatientReportService {


    @Autowired
    private StorageService storageService;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    MailService mailService;

    @Autowired
    private PatientReportRepository patientReportRepository;


    @Autowired
    AppointmentRepository appointmentRepository;
//
    @Autowired
    AppointmentService appointmentService;


    public String exportJasperReport(ReportRequest reportRequest , HttpServletResponse response) throws JRException, IOException {

        PatientReport patientReport =  new PatientReport();
      //  patientReport.setAppointmentId(reportRequest.getAppointmentId());
        BeanUtils.copyProperties(reportRequest, patientReport);

        patientReportRepository.save(patientReport);

        //List<PatientReport> patientReports = patientReportRepository.findByFirstnameAndLastname(reportRequest.getFirstname(), reportRequest.getLastname());
        List<PatientReport> patientReports = patientReportRepository.findByAppointmentId(reportRequest.getAppointmentId());

        if (patientReports.isEmpty()) {
            return "no file found";
        }

        // Get file and compile it
        File file = ResourceUtils.getFile("classpath:Address_01.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patientReports);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "CELL SCOPE");

        // Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Set the response content type and headers
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+reportRequest.getAppointmentId()  + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Export the filled Jasper report to PDF and write it to the response output stream
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.getOutputStream().flush();

        // Specify the directory where you want to save the PDF file
        String exportPath = "C:\\Users\\hp\\Downloads";

        // Generate a unique filename
        String filename = reportRequest.getAppointmentId() + ".pdf";

        // Combine the directory path and filename to create the full file path
        Path filePath = Paths.get(exportPath, filename);

        try {

            if (appointmentRepository.existsById(reportRequest.getAppointmentId()))
            {


                // Export the filled Jasper report to PDF and write it to the specified file
                JasperExportManager.exportReportToPdfFile(jasperPrint, filePath.toString());
                File file1 =  filePath.toFile();
                MultipartFile multipartFile = convertFileToMultipartFile(file);

              //  System.out.println(appointment);

                Optional<Appointment> appointment1 = appointmentRepository.findById(reportRequest.getAppointmentId());
                System.out.println(appointment1);
                    // Now, you can safely call getStatus on the 'appointment' object.
                Appointment appointment = appointment1.get(); // Use get() to extract the Appointment
                appointment.setStatus("completed");

                if (appointment1.isPresent()) {
                    appointmentService.updateAppointment(appointment1.get().getId(), appointment);
                }



//                BeanUtils.copyProperties(appointment, appointment1);
//                appointment.setStatus("completed");
//                System.out.println(appointment);

                Long id = reportRequest.getAppointmentId();
                 //appointmentService.updateAppointment( reportRequest.getAppointmentId() , appointment1);
                storageService.uploadFile(multipartFile, reportRequest.getEmail(), "Report Medical", "");

            }else {
                return "file not found";
            }


             // appointmentService.updateAppointment(reportRequest.getAppointmentId() , appointment);

        } catch (JRException e) {
            // Handle the exception (log it, throw it, etc.)
            e.printStackTrace();

            return "null";
           // return null;
        }

        return "null";
    }


    private MultipartFile convertFileToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file",
                file.getName(), "application/octet-stream", input);
    }



    public PatientReport exportReportRecord(ReportRequest reportRequest)  {

        PatientReport patientReport =  new PatientReport();
        BeanUtils.copyProperties(reportRequest, patientReport);

        patientReportRepository.save(patientReport);
        List<PatientReport> patientReports = patientReportRepository.findByAppointmentId(reportRequest.getAppointmentId());

//        patientReport = patientReports.get(patientReports.size()-1);


        Optional<Appointment> appointment1 = appointmentRepository.findById(reportRequest.getAppointmentId());
                System.out.println(appointment1);
                Appointment appointment = appointment1.get(); // Use get() to extract the Appointment
                appointment.setStatus("completed");
                if (appointment1.isPresent()) {
                    appointmentService.updateAppointment(appointment1.get().getId(), appointment);
                }
                Long id = reportRequest.getAppointmentId();
//                storageService.uploadFile(multipartFile, reportRequest.getEmail(), "Report Medical", "");


        System.out.println(reportRequest.getEmail());

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("malicksaqib01@gmail.com");
                simpleMailMessage.setTo(reportRequest.getEmail());
                simpleMailMessage.setSubject("REPORT");
                simpleMailMessage.setText( "click here ro download your report:  " + "http://localhost:8081/file/download/"+".pdf");
                mailService.sendMessage(simpleMailMessage);
                return patientReport;



    }




}