package com.gis.medfind.Forms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.atlis.location.model.impl.Address;
import com.atlis.location.model.impl.MapPoint;
import com.atlis.location.nominatim.NominatimAPI;
import com.gis.medfind.entity.Request;
import com.gis.medfind.serviceImplem.FileStorageServiceImpl;
import com.gis.medfind.serviceImplem.RequestHandlingServiceImpl;

import org.apache.log4j.BasicConfigurator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Component
@Data
public class RequestForm {
    
    @NotEmpty
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String senderFullName;
    @NotEmpty(message="Email Can't Be Empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String pharmacyName;

    @NotNull
    private String pharmacyAddress;

    @NotNull
    private MultipartFile license;
    
    public void toRequest(RequestHandlingServiceImpl requestService, FileStorageServiceImpl fileService){

        Request request = new Request();
        request.setSenderFullName(this.senderFullName);
        request.setEmail(this.email);
        request.setPharmacyName(this.pharmacyName);
        Address pharmAddress = new Address();
        pharmAddress.setDisplayName(this.pharmacyAddress);

        BasicConfigurator.configure();
        String endpointUrl = "https://nominatim.openstreetmap.org/";
        MapPoint addressLatLon = NominatimAPI.with(endpointUrl).getMapPointFromAddress(pharmAddress,2);
        Double pharmacyLat=0.0;
        Double pharmacyLon=0.0;
        if(addressLatLon != null){
             pharmacyLat = addressLatLon.getLatitude();
             pharmacyLon = addressLatLon.getLongitude();
        }
            GeometryFactory geom = new GeometryFactory(new PrecisionModel(),4326);
            Coordinate location = new Coordinate(pharmacyLat, pharmacyLon);
        request.setLocation(geom.createPoint(location));

        request.setLicenseFile(fileService.upload(this.license));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String currentDateTime  = dtf.format(now); 
        request.setCreatedDate(currentDateTime);
        
        requestService.newRequest(request);
        
    }
    
}
