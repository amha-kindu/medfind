package com.gis.medfind.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;

import com.gis.medfind.entity.FileInfo;
import com.gis.medfind.repository.FileInfoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class FileInfoRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private FileInfoRepository fileRepo;
     
    @Test
    public void testCreateFileInfo() {
        FileInfo file = new FileInfo();
            file.setName("kenema_license");
            file.setUrl("uploads/license/kenema/");
        
        FileInfo saved_file = fileRepo.save(file);

        FileInfo exist_file = entityManager.find(FileInfo.class, file.getId());
        assertThat(exist_file.getUrl()).isEqualTo(saved_file.getUrl());        
    }
}