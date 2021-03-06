package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SleepSummaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SleepSummary sleepSummary);

    @Query("SELECT * FROM SleepSummary")
    LiveData<List<SleepSummary>> getAllSleepSummary();

    @Query("DELETE FROM SleepSummary")
    void deleteAll();
}
