package com.pnc.api.model;
import com.pnc.api.model.enums.TIME_TYPE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomDate {
    public  static Date getDate(String  timeStamp, TIME_TYPE type) throws ParseException {
        //new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String input = convert(timeStamp);
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputFormatter = null;
        Date date = null;
        int index = 0;

        switch(type) {
            case TIME:
                index = 1;
                outputFormatter = new SimpleDateFormat("HH:mm:ss");
                break;
            case DATE:
                outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case DATE_TIME:
                outputFormatter = inputFormatter;
                break;
        }

        try {
            String[] inputs = null;
            if(type != TIME_TYPE.DATE_TIME){
                inputs = input.split(" ");
                date = outputFormatter.parse(inputs[index]);
            } else
            {
                date = inputFormatter.parse(input);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getYearMonth(String timeStamp,boolean isLastMonth) {
        try {
            LocalDate localDate = convertToLocalDateViaInstant(getDate(timeStamp, TIME_TYPE.DATE_TIME));
            if(isLastMonth)
                localDate = localDate.minusMonths(1);
            String yM = YearMonth.of(localDate.getYear(),localDate.getMonth().getValue()).toString();
            return Integer.parseInt(yM.replaceAll("-",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  0;
    }

    private static  String convert(String str){
        return Instant.parse(str)
                .atOffset( ZoneOffset.UTC )
                .format(
                        DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" )
                );
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}