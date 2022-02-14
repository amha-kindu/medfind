package com.gis.medfind.serviceImplem;

import java.util.ArrayList;
import java.util.List;

import com.gis.medfind.entity.Pharmacy;
import com.gis.medfind.entity.Request;
import com.gis.medfind.entity.Role;
import com.gis.medfind.entity.User;
import com.gis.medfind.repository.PharmacyRepository;
import com.gis.medfind.repository.RegionRepository;
import com.gis.medfind.repository.RequestRepository;
import com.gis.medfind.repository.RoleRepository;
import com.gis.medfind.repository.UserRepository;
import com.gis.medfind.service.RequestHandlingService;
import com.gis.medfind.utils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RequestHandlingServiceImpl implements RequestHandlingService {

    @Autowired
    RequestRepository requestRepo;

    @Autowired
    PharmacyRepository pharmRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RegionRepository regionRepo;

    @Override
    public Request newRequest(Request rq){
        return requestRepo.save(rq);
    }
    
    @Override
    public void acceptRequest(Long requestId){
        Request rq = requestRepo.findById(requestId).orElseThrow();
        Pharmacy newPharm = new Pharmacy();
            newPharm.setLocation(rq.getLocation());
            newPharm.setName(rq.getPharmacyName());

                User owner = new User();
                    owner.setEmail(rq.getEmail());
                    owner.setFirstName(rq.getSenderFullName()); 
                        List<Role> roles = new ArrayList<>();
                            roles.add(roleRepo.findByName("USER"));
                    owner.setRoles(roles);
                    owner = userRepo.save(owner);
            newPharm.setOwner(owner);
            newPharm.setLicenseFile(rq.getLicenseFile());
            newPharm.setRegion(regionRepo.findRegionContaining(rq.getLocation()));
            newPharm.setAddress(utils.reverseGeocode(rq.getLocation().getX(), rq.getLocation().getY()));
        newPharm = pharmRepo.save(newPharm);
        requestRepo.delete(rq);
    }
    
    @Override
    public void rejectRequest(Long rq){
        requestRepo.delete(requestRepo.getById(rq));
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepo.findAll();
    }

    @Override
    public Request getRequest(String pharmacyName){
        return requestRepo.findByName(pharmacyName);
    }

}