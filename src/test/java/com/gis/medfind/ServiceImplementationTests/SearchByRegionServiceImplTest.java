package com.gis.medfind.ServiceImplementationTests;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import com.gis.medfind.serviceImplem.SearchByRegionServiceImpl;

import com.gis.medfind.entity.Pharmacy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class SearchByRegionServiceImplTest {
    
    @Autowired
    private SearchByRegionServiceImpl searchService;

    @Test
    public void testFindPharmaciesWithInRegion(){
        String medicine = "Abilify";
        List<Pharmacy> found_in_bole = searchService.findPharmaciesWithInRegion("Bole", medicine);
        
        List<Pharmacy> found_in_yeka = searchService.findPharmaciesWithInRegion("Yeka", medicine);
        
        List<Pharmacy> commonElements = found_in_bole.stream()
            .filter(found_in_yeka::contains)
            .collect(Collectors.toList());

        assertThat(commonElements.size()).isEqualTo(0);


    }
    
}
