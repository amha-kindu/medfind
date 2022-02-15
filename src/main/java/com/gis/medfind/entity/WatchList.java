package com.gis.medfind.entity;

import lombok.Data;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import java.util.List;


@Data
@Entity
@Table(name = "watch_list")
public class WatchList{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "watchList_id")
    private Long id;
    
    @Column(name = "creation_date")
    private String creationDate;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_owner")
    private User owner;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "watchlist_medicine", 
        joinColumns = @JoinColumn(
            name = "watchlist_id", referencedColumnName = "watchlist_id"), 
        inverseJoinColumns = @JoinColumn(
            name = "medicine_id", referencedColumnName = "medicine_id"))
    private List<Medicine> medicines = new ArrayList<>();

    public boolean addMedicine(Medicine med){
        return this.medicines.add(med);
    }

    public boolean removeMedicine(Medicine med){
        return this.medicines.remove(med);
    }
}
