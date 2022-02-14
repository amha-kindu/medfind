package com.gis.medfind.service;

import com.gis.medfind.entity.WatchList;

public interface WatchListService {
    public WatchList findWatchListByUserId(Long Id);
}
