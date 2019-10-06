package test.taylor.com.taylorcode.data_persistence;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataConverter {

    /**
     * time case: convert utc to timestamp
     * @param time
     * @return
     * @throws ParseException
     */
    @TypeConverter
    public long utcToTimestamp(String time) throws ParseException {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df2.parse(time);
        return date.getTime();
    }

    @TypeConverter
    public String timestampToUtc(long timestamp) throws ParseException {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date date = df2.parse(time);
        return "dkfjdk";
    }
}
