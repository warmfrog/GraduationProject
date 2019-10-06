package cn.booksp.sharebook.repository.dao.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by warmfrog on 2019/2/25.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
