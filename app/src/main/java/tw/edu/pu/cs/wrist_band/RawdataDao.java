package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;
import android.provider.ContactsContract;

@Dao
public interface RawdataDao {

    @Insert
    void insert(Rawdata data);

    //@Query("SELECT * FROM Rawdata)

}
