package com.gis.medfind.controller;

import java.util.List;

import com.gis.medfind.entity.Pharmacy;
import com.gis.medfind.entity.User;
import com.gis.medfind.repository.PharmacyRepository;
import com.gis.medfind.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class AdminController {
    
    @Autowired
    UserRepository userRepo;
    
    @Autowired
    PharmacyRepository pharmaRepo;


    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/pharmacies")
    public String showPharmacies(Model model) {
        List<Pharmacy> pharmacies = pharmaRepo.findAll();
        model.addAttribute("pharmacies", pharmacies);
        return "admin";
    }

}
