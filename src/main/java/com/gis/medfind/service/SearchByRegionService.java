package com.gis.medfind.service;

import java.util.List;
import com.gis.medfind.entity.Pharmacy;

public interface SearchByRegionService {
    List<Pharmacy> findPharmaciesWithInRegion(String region_name, String medicineName);
}
