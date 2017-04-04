package com.ashleymariecramer.services;

import com.ashleymariecramer.Accommodation;
import com.ashleymariecramer.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository aRepo;

    public Map<String, Object> makeAccommodationListDTO(Accommodation accommodation) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("name", accommodation.getName());
        dto.put("city", accommodation.getCity());
        dto.put("id", accommodation.getId());
        return dto;
    }


} //End of AccommodationService