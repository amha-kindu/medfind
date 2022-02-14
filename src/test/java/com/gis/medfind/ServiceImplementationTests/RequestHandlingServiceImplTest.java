package com.gis.medfind.ServiceImplementationTests;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.gis.medfind.entity.FileInfo;
import com.gis.medfind.entity.Request;
import com.gis.medfind.repository.FileInfoRepository;
import com.gis.medfind.repository.PharmacyRepository;
import com.gis.medfind.serviceImplem.RequestHandlingServiceImpl;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class RequestHandlingServiceImplTest {     
     
    @Autowired
    private PharmacyRepository pharmRepo;
     
    @Autowired
    private RequestHandlingServiceImpl requestHandlingService;

    @Autowired
    private FileInfoRepository fileService;
    
    @Autowired
    private GeometryFactory geom;

    @Test
    public void testNewRequest() {
        Request newRequest = new Request();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String currentDateTime  = dtf.format(now);  
            newRequest.setCreatedDate(currentDateTime);
            newRequest.setEmail("habtetsegaye19@gmail.com");
                FileInfo license = new FileInfo();
                    license.setName("myFile");
                    license.setUrl("files/vendor");
                    license = fileService.save(license);
            newRequest.setLicenseFile(license);
            newRequest.setLocation(geom.createPoint(new Coordinate(78.89, 22.2)));
            newRequest.setPharmacyName("ST. MARKOS1");
            newRequest.setSenderFullName("Habte Tsegaye1");

            newRequest = requestHandlingService.newRequest(newRequest);
            
    }

    @Test
    public void acceptNewRequest() {
        Request newRequest = new Request();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String currentDateTime  = dtf.format(now);  
            newRequest.setCreatedDate(currentDateTime);
            newRequest.setEmail("habtetsegaye29@gmail.com");
                FileInfo license = new FileInfo();
                    license.setName("myFile2");
                    license.setUrl("files/vendor");
                    license = fileService.save(license);
            newRequest.setLicenseFile(license);
            newRequest.setLocation(geom.createPoint(new Coordinate(18.89, 22.2)));
            newRequest.setPharmacyName("ST. MARKOS2");
            newRequest.setSenderFullName("Habte Tsegaye2");
            newRequest = requestHandlingService.newRequest(newRequest);
    
            int initialPharmCount = pharmRepo.findAll().size();
            requestHandlingService.acceptRequest(newRequest.getId());

            assertThat(pharmRepo.findAll().size()).isEqualTo(initialPharmCount+1);
    }

    @Test
    public void rejectRequest() {
        Request newRequest = new Request();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String currentDateTime  = dtf.format(now);  
            newRequest.setCreatedDate(currentDateTime);
            newRequest.setEmail("habtetsegaye39@gmail.com");
                FileInfo license = new FileInfo();
                    license.setName("myFile3");
                    license.setUrl("files/vendor");
                    license = fileService.save(license);
            newRequest.setLicenseFile(license);
            newRequest.setLocation(geom.createPoint(new Coordinate(58.89, 22.2)));
            newRequest.setPharmacyName("ST. MARKOS3");
            newRequest.setSenderFullName("Habte Tsegaye3");            
            newRequest = requestHandlingService.newRequest(newRequest);

            int initialReqCount = requestHandlingService.getAllRequests().size();
    
            requestHandlingService.rejectRequest(newRequest.getId());
    
            List<Request> allRequests = requestHandlingService.getAllRequests();
            assertThat(allRequests.size()).isEqualTo(initialReqCount-1);
    }

    @Test
    public void getAllRequest() {
        Request newRequest = new Request();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String currentDateTime  = dtf.format(now);  
            newRequest.setCreatedDate(currentDateTime);
            newRequest.setEmail("habtetsegaye89@gmail.com");
                FileInfo license = new FileInfo();
                    license.setName("myFile4");
                    license.setUrl("files/vendor");
                    license = fileService.save(license);
            newRequest.setLicenseFile(license);
            newRequest.setLocation(geom.createPoint(new Coordinate(39.89, 22.2)));
            newRequest.setPharmacyName("ST. MARKOS4");
            newRequest.setSenderFullName("Habte Tsegaye4");


        int initialReqCount = requestHandlingService.getAllRequests().size();
        newRequest = requestHandlingService.newRequest(newRequest);

        List<Request> allRequests = requestHandlingService.getAllRequests();
        assertThat(allRequests.size()).isEqualTo(initialReqCount+1);

    }
}