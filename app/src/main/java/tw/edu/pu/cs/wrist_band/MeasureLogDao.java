package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MeasureLogDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MeasureLog measureLog);

    @Query("SELECT * FROM MeasureLog")
    LiveData<List<MeasureLog>> getAllMeasureLog();

    @Query("DELETE FROM MeasureLog")
    void deleteAll();
}
