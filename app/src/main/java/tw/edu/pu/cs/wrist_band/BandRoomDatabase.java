package tw.edu.pu.cs.wrist_band;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {User.class, Rawdata.class, MeasureLog.class, SleepSummary.class}, version = 3, exportSchema = false)
@TypeConverters({RawDataConverter.class, MeasureLogConverter.class})
public abstract class BandRoomDatabase extends RoomDatabase {

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

    public abstract UserDao userDao();

    public abstract RawdataDao rawdataDao();

    public abstract MeasureLogDao measureLogDao();

    public abstract SleepSummaryDao sleepSummaryDao();
}
