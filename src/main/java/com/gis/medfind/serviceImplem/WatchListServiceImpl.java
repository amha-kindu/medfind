
package com.gis.medfind.serviceImplem;

import com.gis.medfind.entity.WatchList;
import com.gis.medfind.repository.WatchListRepository;
import com.gis.medfind.service.WatchListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WatchListServiceImpl implements WatchListService{

    @Autowired
    private WatchListRepository repo;

    public WatchList findWatchListByUserId(Long Id){ 
        return repo.findWatchListByUserId(Id);
    }

    
}