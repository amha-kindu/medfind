package com.gis.medfind.service;

import java.util.List;

import com.gis.medfind.entity.Request;

public interface RequestHandlingService {

    public Request newRequest(Request rq);

    public boolean acceptRequest(Long requestId);

    public void rejectRequest(Long rq);

    public List<Request> getAllRequests();

    public Request getRequest(String pharmacyName);

}
