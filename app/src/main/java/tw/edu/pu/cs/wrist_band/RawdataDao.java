package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RawdataDao {

    @Insert
    void insert(Rawdata data);

    @Query("SELECT * FROM Rawdata")
    LiveData<List<Rawdata>> getAllRawdata();

}