package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.TypeConverter;

import java.math.BigInteger;
import java.util.Date;

public class MeasureLogConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static BigInteger revertBigInteger(String value) {
        return new BigInteger(value);
    }

    @TypeConverter
    public static String convertBigInteger(BigInteger bigInteger) {
        return bigInteger.toString();
    }
}
