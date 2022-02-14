package com.gis.medfind.ServiceImplementationTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.gis.medfind.serviceImplem.FileStorageServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class FileStorageServiceImplTest {

    final String sourcePath = "/src/test/java/com/gis/medfind/ServiceImplementationTests/test_uploads";

    @Autowired
    private FileStorageServiceImpl fileService;
    
    @Test
    public void testSaveAndLoadFile() {
        Path path = Paths.get(System.getProperty("user.dir"), sourcePath + "/testFile.txt");
        String name = "testFile.txt";
        String originalFileName = "testFile.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {

            System.out.println("File not Found");
        }

        MultipartFile testFile = new MockMultipartFile(name, originalFileName, contentType, content);
        fileService.upload(testFile);

        Resource res = fileService.getFile("testFile.txt");

        try{
            assertThat(res.getInputStream().readAllBytes()).isEqualTo(testFile.getInputStream().readAllBytes());            
        }catch(IOException ioe){}
    }
    
    @Test
    public void testDeleteByName() {
        fileService.deleteByName("testFile.txt");
        try{
            fileService.getFile("testFile.txt");
        }catch(RuntimeException rte){

        }
        
    }
    
    @Test
    public void testLoadAllFiles() {
        List<Path> all_files = fileService.loadAllFiles();
        for(Path path: all_files){
            System.out.println(path.toUri().toString());
        }
        
    }
}