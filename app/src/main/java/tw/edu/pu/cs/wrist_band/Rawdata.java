package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Rawdata {

    @PrimaryKey
    @NonNull
    private String id;
    private String band_id;
    private String sleep_time;
    private String step_num;
    private String calories;
    private String heart_rate;

    public Rawdata(String id,String band_id, String sleep_time, String step_num, String calories, String heart_rate) {
        this.id = id;
        this.band_id=band_id;
        this.sleep_time=sleep_time;
        this.step_num=step_num;
        this.calories=calories;
        this.heart_rate=heart_rate;
    }

    public String getId() {
        return id;
    }

    public String getBand_id() {
        return band_id;
    }

    public String getSleep_time() {
        return sleep_time;
    }

    public String getStep_num() {
        return step_num;
    }

    public String getCalories() {
        return calories;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String rate){ heart_rate=rate;}



}
