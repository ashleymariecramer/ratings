package com.ashleymariecramer.services;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StringToDateService {

    public Date convertStringToDate(String dateString) {
        Date date = null;
        Date parsed = null;
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            parsed = format.parse(dateString);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return parsed;
    }

}


