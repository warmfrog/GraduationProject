package cn.booksp.sharebook.repository.dao.converter;

import android.arch.persistence.room.TypeConverter;

import java.math.BigDecimal;

/**
 * Created by warmfrog on 2019/2/25.
 */

public class BigDcimalConverter {
    @TypeConverter
    public static Double toDouble(BigDecimal num){
        if(num == null){
            return 0.0;
        }else {
            return num.doubleValue();
        }
    }
    @TypeConverter
    public static BigDecimal toBigDecimal(Double num){
        if(num == null){
            return BigDecimal.valueOf(0.0);
        }else {
            return BigDecimal.valueOf(num);
        }
    }
}
