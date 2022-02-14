package com.gis.medfind.repository;

import java.util.List;

import com.gis.medfind.entity.Region;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query(value = "SELECT rg.region_id, * FROM region rg WHERE rg.region_name= :region_name", nativeQuery = true)
    Region findByName(@Param("region_name") String regionName);

    @Query(value = "SELECT rg.region_id, rg.region_name, rg.region_boundary FROM region rg WHERE ST_CONTAINS(rg.region_boundary, ST_SetSRID(:location, 4326))", nativeQuery = true)
    Region findRegionContaining(@Param("location") Point loc);
    
    @Query(value = "SELECT rg.region_name FROM region rg", nativeQuery = true)
    List<String> getAllRegionNames();
}
