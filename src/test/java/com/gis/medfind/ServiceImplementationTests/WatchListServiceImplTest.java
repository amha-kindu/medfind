package com.gis.medfind.ServiceImplementationTests;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.gis.medfind.entity.Medicine;
import com.gis.medfind.entity.User;
import com.gis.medfind.entity.WatchList;
import com.gis.medfind.repository.MedicineRepository;
import com.gis.medfind.repository.UserRepository;
import com.gis.medfind.repository.WatchListRepository;
import com.gis.medfind.serviceImplem.WatchListServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class WatchListServiceImplTest {     
    @Autowired
    private WatchListRepository watchRepo;
     
    @Autowired
    private MedicineRepository medRepo;
     
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private WatchListServiceImpl watchListService;

    @Test
    public void testGetWatchList() {
        WatchList watchlist = new WatchList();
        watchlist.setCreationDate(new Date().toString());
            List<Medicine> medicines = new ArrayList<>();
            Medicine med1 = medRepo.save(new Medicine());
            Medicine med2 = medRepo.save(new Medicine());
            Medicine med3 = medRepo.save(new Medicine());
            Medicine med4 = medRepo.save(new Medicine());

            medicines.add(med1);
            medicines.add(med2);
            medicines.add(med3);
            medicines.add(med4);
        
        watchlist.setMedicines(medicines);
        User newUser = new User();
        newUser =  userRepo.save(newUser);
        watchlist.setOwner(newUser);
        WatchList savedWatchList = watchRepo.save(watchlist);
        WatchList swl = watchListService.findWatchListByUserId(newUser.getId());  
        assertThat(swl).isEqualTo(savedWatchList);
    }
}