package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.TypeConverter;

import com.epson.pulsenseapi.model.MeasureLogModel;

public class RawDataConverter {

//    @TypeConverter
//    public static SimpleDate revertSimpleDate() {
//
//    }
//
//    @TypeConverter
//    public static long convertSimpleDate(SimpleDate value) {
//        return value.
//    }

    @TypeConverter
    public static MeasureLogModel revertMeasureLogModel(byte[] value) {
        MeasureLogModel measureLogModel = new MeasureLogModel();
        measureLogModel.setBytes(value);
        return measureLogModel;
    }

    @TypeConverter
    public static byte[] convertMeasureLogModel(MeasureLogModel value) {
        return value.getBytes();
    }
}
