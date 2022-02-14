package com.gis.medfind.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.locationtech.jts.geom.Polygon;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.n52.jackson.datatype.jts.GeometrySerializer;
import org.n52.jackson.datatype.jts.GeometryDeserializer;


@Data
@Entity
public class Region{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "region_id")
    private Long id;

    @Column(name = "region_name")
    private String name;

    @Column(name = "region_boundary",columnDefinition="geometry")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Polygon boundary;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_region")
    @JsonManagedReference
    private List<Pharmacy> pharmacies = new ArrayList<>();


    public boolean addPharmacy(Pharmacy pharm){
        return this.pharmacies.add(pharm);
    }

}