package com.ashleymariecramer.services;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class StringToLocalDateService {

    public LocalDate convertStringToLocalDate(String dateString) {
        LocalDate localDate = null;
//        Date date = null;
//        Date parsed = null;
        try{
            localDate = LocalDate.parse(dateString);
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            parsed = format.parse(dateString);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
//        return parsed;
        return localDate;
    }

    public LocalDate calculateDayBefore(String dateString){
        LocalDate today = LocalDate.parse(dateString);
        LocalDate dayBefore = today.plus(1, ChronoUnit.DAYS).minusDays(2);

        return dayBefore;

    }

    public LocalDate calculateDayAfter(String dateString){
        LocalDate today = LocalDate.parse(dateString);
        LocalDate dayAfter = today.plus(1, ChronoUnit.DAYS);

        return dayAfter;

    }

}








