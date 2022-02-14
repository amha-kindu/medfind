package com.gis.medfind.repository;

import com.gis.medfind.entity.FileInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    @Query(value = "SELECT fi.id, * FROM file_info fi WHERE fi.name = :file_name", nativeQuery = true)
    FileInfo getByName(@Param("file_name") String fileName);
}