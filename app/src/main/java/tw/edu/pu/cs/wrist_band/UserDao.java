package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM User")
    void deleteAll();

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT PersonalID FROM User WHERE name = :name_")
    String getUserID(String name_);

    @Query("SELECT name FROM User WHERE PersonalID = :id_")
    String getUserNAME(String id_);

}
