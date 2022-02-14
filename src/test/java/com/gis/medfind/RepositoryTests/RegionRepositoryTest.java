package com.gis.medfind.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;


import com.gis.medfind.entity.Region;
import com.gis.medfind.repository.RegionRepository;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class RegionRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GeometryFactory geometryFactory;
     
    @Autowired
    private RegionRepository repo;
    
     
    @Test
    public void testCreateRegion() {
        Region rg = new Region();
        Coordinate first = new Coordinate(0,0); 
        Coordinate second = new Coordinate(1,1); 
        Coordinate third = new Coordinate(1,0); 
        Coordinate forth= new Coordinate(0,0); 
        Coordinate[] coordinates = {first, second, third, forth};        
        rg.setBoundary(geometryFactory.createPolygon(coordinates));
        rg.setName("bole");
        Region saved_rg = repo.save(rg);

        Region exist_rg = entityManager.find(Region.class, rg.getId());
        assertThat(exist_rg.getName()).isEqualTo(saved_rg.getName());        
        assertThat(exist_rg.getBoundary().equals(saved_rg.getBoundary())).isEqualTo(true);        
    }
}