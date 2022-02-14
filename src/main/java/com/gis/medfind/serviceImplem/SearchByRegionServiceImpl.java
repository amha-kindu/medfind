package com.gis.medfind.serviceImplem;

import com.gis.medfind.entity.Pharmacy;
import com.gis.medfind.entity.Region;
import com.gis.medfind.repository.PharmacyRepository;
import com.gis.medfind.repository.RegionRepository;

import java.util.ArrayList;
import java.util.List;

import com.gis.medfind.service.SearchByRegionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    
@Service 
public class SearchByRegionServiceImpl implements SearchByRegionService{

    @Autowired
    private PharmacyRepository pharmRepo;
    @Autowired
    private RegionRepository regionRepo;
    
    public List<Pharmacy> findPharmaciesWithInRegion(String region_name, String medicineName){
        Region reg = regionRepo.findByName(region_name);
        List<Pharmacy> pharmsInRegion = pharmRepo.findAllPharmacyWithInRegion(reg.getId());
        List<Pharmacy> pharmsInRegionWithMedicine = new ArrayList<>();
        for(Pharmacy pharm: pharmsInRegion){
            if(pharm.checkMedicine(medicineName)){
                pharmsInRegionWithMedicine.add(pharm);
            }
        }
        return pharmsInRegionWithMedicine;
    }
}
