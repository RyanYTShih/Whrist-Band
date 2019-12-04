package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.util.Date;

@Entity(indices = {@Index("PersonalID")},
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "PersonalID",
                childColumns = "PersonalID"))

public class MeasureLog {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long SerialID;

    private String PersonalID;

    @NonNull
    private String BandID;

    private int Steps;

    private BigInteger Distance;

    private int ExerciseCalories;

    private int RestCalories;

    private String Heartrate;

    private Date StartTime;

    private Date StopTime;

    public MeasureLog(Long SerialID,
                      String PersonalID,
                      String BandID,
                      int Steps,
                      BigInteger Distance,
                      int ExerciseCalories,
                      int RestCalories,
                      String Heartrate,
                      Date StartTime,
                      Date StopTime) {
        this.SerialID = SerialID;
        this.PersonalID = PersonalID;
        this.BandID = BandID;
        this.Steps = Steps;
        this.Distance = Distance;
        this.ExerciseCalories = ExerciseCalories;
        this.RestCalories = RestCalories;
        this.Heartrate = Heartrate;
        this.StartTime = StartTime;
        this.StopTime = StopTime;
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

    public void setPersonalID(String PersonalID) {
        this.PersonalID = PersonalID;
    }

    public String getBandID() {
        return BandID;
    }

    public void setBandID(String BandID) {
        this.BandID = BandID;
    }

    public int getSteps() {
        return Steps;
    }

//    public void setSteps(int Steps) {
//        this.Steps = Steps;
//    }

    public BigInteger getDistance() {
        return Distance;
    }

    public int getExerciseCalories() {
        return ExerciseCalories;
    }

    public int getRestCalories() {
        return RestCalories;
    }

    public String getHeartrate() {
        return Heartrate;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public Date getStopTime() {
        return StopTime;
    }

    @Override
    public String toString() {
        return SerialID + ". "
                + PersonalID + "; "
                + BandID + "; steps:"
                + Steps + "; distance:"
                + Distance + "; exerciseCalories:"
                + ExerciseCalories + "; restCalories:"
                + RestCalories + "; heartRate:"
                + Heartrate + "; startTime:"
                + StartTime + "; stopTime:"
                + StopTime;
    }
}
