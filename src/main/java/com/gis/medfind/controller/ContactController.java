package com.gis.medfind.controller;

import com.gis.medfind.entity.Message;
import com.gis.medfind.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @Autowired
MessageRepository repo;
    @GetMapping("/contact")
    public String contact(  ){
        return "Contact";
    }
    @PostMapping("/contact")
    public String messageProcess(@RequestParam("comment") String comment ,Model model){
        Message ms= new Message();
        ms.setComment(comment);
        repo.save(ms);
        model.addAttribute("messageRecorded", true);
        return "Contact";


    }
}
