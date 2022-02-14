package com.gis.medfind.repository;

import com.gis.medfind.entity.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query(value = "SELECT rq.request_id,* FROM request rq WHERE rq.pharmacy_name = :name", nativeQuery = true)
    public Request findByName(@Param("name") String pharmacyName);
}
