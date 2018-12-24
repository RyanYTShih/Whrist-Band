package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class, Rawdata.class}, version = 1)
public abstract class BandRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract RawdataDao rawdataDao();

    private static volatile BandRoomDatabase INSTANCE;

    static BandRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BandRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BandRoomDatabase.class, "band_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
