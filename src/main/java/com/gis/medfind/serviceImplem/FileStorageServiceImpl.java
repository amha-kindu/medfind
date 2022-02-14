package com.gis.medfind.serviceImplem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.gis.medfind.entity.FileInfo;
import com.gis.medfind.repository.FileInfoRepository;
import com.gis.medfind.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Autowired
    private FileInfoRepository fileRepo;
    
    private Path root = Paths.get("uploads/license");

    public FileStorageServiceImpl(){
        this.initialize();
    }

    @Override
    public void initialize(){
        try{
            Files.createDirectory(root);
        }
        catch(IOException e){
            System.out.print(e.getMessage());
            System.out.print("Initialization Failed");
        }
    }

    @Override
    public void initialize(String subdir){
        this.root = Paths.get(root.getRoot().toString()+"/"+subdir); 
        try{
            Files.createDirectory(root);
        }
        catch(IOException e){
            System.out.print(e.getMessage());
            System.out.print("Initialization Failed");
        }
    }

    @Override
    public FileInfo upload(MultipartFile file){
        FileInfo metaData = null;
        try{
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

            FileInfo license_meta = new FileInfo();
                license_meta.setName(file.getOriginalFilename());
                license_meta.setUrl(root.toUri().toString());
            metaData = fileRepo.save(license_meta);

        }catch(IOException e){
            System.out.print(e.getMessage());
            System.out.print("File uploading failed!");
        }
        return metaData;
    }

    @Override
    public Resource getFile(String filename) throws RuntimeException{
        try{
            Path file = root.resolve(filename);
            Resource res = new UrlResource(file.toUri());
            if(res.exists() || res.isReadable()){
                return res;
            }else{
                throw new RuntimeException("Unable to read file");
            }
        }
        catch(MalformedURLException e){
            throw new RuntimeException("Couldn't find file!");
        }
    }

    @Override
    public boolean deleteAll(){
        return FileSystemUtils.deleteRecursively(this.root.toFile());
    }

    @Override
    public boolean deleteByName(String fileName){
        Path deleteFile = this.root.resolve(fileName);
        try{
            Files.delete(deleteFile);
        }catch(IOException e){
            System.out.println("Couldn't delete file!");
        }
        return true;
    }

    @Override
    public List<Path> loadAllFiles(){
        List<Path> allFiles = null;
        try{
            allFiles = Files.walk(this.root,1).filter(path -> !path.equals(this.root)).map(this.root::relativize).toList();
        }
        catch(IOException e){
            System.out.println("Could not load all the files");
        }
        return allFiles;
    }

    @Override
    public String getURL(String fileName){
        return this.root.normalize().toString()+"\\"+fileName;
    }
}
