package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.epson.pulsenseapi.model.MeasureLogModel;

@Entity
public class Rawdata {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long serial_id;

    @NonNull
    private String personal_id;

    @NonNull
    private String band_id;

//    @NonNull
//    private SimpleDate start_date;
//
//    @NonNull
//    private SimpleTime start_time;
//
//    @NonNull
//    private SimpleDate stop_date;
//
//    @NonNull
//    private SimpleTime stop_time;

    @NonNull
    private MeasureLogModel measureLogModel;

    public Rawdata(String personal_id,
                   String band_id,
//                   SimpleDate start_date,
//                   SimpleTime start_time,
//                   SimpleDate stop_date,
//                   SimpleTime stop_time,
                   MeasureLogModel measureLogModel) {
        this.personal_id = personal_id;
        this.band_id = band_id;
//        this.start_date = start_date;
//        this.start_time = start_time;
//        this.stop_date = stop_date;
//        this.stop_time = stop_time;
        this.measureLogModel = measureLogModel;
    }

    public long getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(long serial_id) {
        this.serial_id = serial_id;
    }

    public String getPersonal_id() {
        return personal_id;
    }

    public String getBand_id() {
        return band_id;
    }

//    public SimpleDate getStart_date() {
//        return start_date;
//    }
//
//    public SimpleTime getStart_time() {
//        return start_time;
//    }
//
//    public SimpleDate getStop_date() {
//        return stop_date;
//    }
//
//    public SimpleTime getStop_time() {
//        return stop_time;
//    }

    public MeasureLogModel getMeasureLogModel() {
        return measureLogModel;
    }

}
