package com.gis.medfind.controller;

import java.io.IOException;
// import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import com.gis.medfind.Forms.RequestForm;
import com.gis.medfind.entity.Request;
import com.gis.medfind.serviceImplem.FileStorageServiceImpl;
import com.gis.medfind.serviceImplem.RequestHandlingServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestController {

    @Autowired
    public RequestHandlingServiceImpl requestService;

    @Autowired
    public FileStorageServiceImpl fileService;

    @Autowired
    public RequestForm requestForm;

    @ModelAttribute(name="RequestForm")
    public RequestForm requestForm(){
        return requestForm;
    }
 
    @GetMapping("/sendRequest")
    public String sendRequest(){
        return "sendRequest";
    }



    @PostMapping("/sendRequest")
    public String processRequest(@Valid @ModelAttribute(name="RequestForm") RequestForm request, Errors errors, Model models) throws IOException {
                
        if (errors.hasErrors()){
            models.addAttribute("requestFailed", true);
            return "sendRequest";
        }   
        request.toRequest(requestService,fileService);
        models.addAttribute("requestSuccess", true);
        return "sendRequest";
    }



    @GetMapping("/handle_request")
    public String handleRequest(Model model){
        List<Request> requests = requestService.getAllRequests();
        model.addAttribute("requests", requests);
        
        return "validator";
    }

    @PostMapping("/validator/get_request")
    public String getRequest(@RequestParam("pharmacyName") String pharmacyName, Model model){
        Request found = requestService.getRequest(pharmacyName);
        model.addAttribute("selected_request", found);
        List<Request> requests = requestService.getAllRequests();
        model.addAttribute("requests", requests);
        return "validator";
    }


    @PostMapping("/validator/approve")
    public String approveRequest(@RequestParam String requestId){
        requestService.acceptRequest(Long.parseLong(requestId));
        return "validator";
    }
    @PostMapping("/validator/reject")
    public String rejectRequest(String requestId){
        requestService.rejectRequest(Long.parseLong(requestId));
        return "validator";
    }

    @GetMapping(value = "/uploads/license/{filename}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getLicense(Model model, @PathVariable("filename") String filename){
        Resource res = fileService.getFile(filename);
        byte[] data = new byte[]{};
        try{
            data = res.getInputStream().readAllBytes();
        }catch(Exception ex){}

        return data;
    }
    

  

    







    
}

