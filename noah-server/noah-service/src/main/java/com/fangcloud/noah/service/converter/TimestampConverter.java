package com.fangcloud.noah.service.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by chenke on 16-8-20.
 */
public class TimestampConverter implements Converter<Date, Timestamp> {

    public Timestamp convert(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

}
