package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(indices = {@Index("PersonalID")},
        foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "PersonalID",
                                  childColumns = "PersonalID"))

public class SleepSummary {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long SerialID;

    private String PersonalID;

    @NonNull
    private String BandID;

    private Date SleepStartTime;

    private Date SleepStopTime;

    private Date DeepSleepStartTime;

    private Date LightSleepStartTime;

    private Date OtherSleepStartTime;

    public SleepSummary(String PersonalID,
                        String BandID,
                        Date SleepStartTime,
                        Date SleepStopTime,
                        Date DeepSleepStartTime,
                        Date LightSleepStartTime,
                        Date OtherSleepStartTime) {
        this.PersonalID = PersonalID;
        this.BandID = BandID;
        this.SleepStartTime = SleepStartTime;
        this.SleepStopTime = SleepStopTime;
        this.DeepSleepStartTime = DeepSleepStartTime;
        this.LightSleepStartTime = LightSleepStartTime;
        this.OtherSleepStartTime = OtherSleepStartTime;
    }

    public Long getSerialID() {
        return SerialID;
    }

    public void setSerialID(Long SerialID) {
        this.SerialID = SerialID;
    }

    public String getPersonalID() {
        return PersonalID;
    }

    public String getBandID() {
        return BandID;
    }

    public Date getSleepStartTime() {
        return SleepStartTime;
    }

    public Date getSleepStopTime() {
        return SleepStopTime;
    }

    public Date getDeepSleepStartTime() {
        return DeepSleepStartTime;
    }

    public Date getLightSleepStartTime() {
        return LightSleepStartTime;
    }

    public Date getOtherSleepStartTime() {
        return OtherSleepStartTime;
    }
}
