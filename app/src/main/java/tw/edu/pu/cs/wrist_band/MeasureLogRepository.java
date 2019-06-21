package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MeasureLogRepository {
    private MeasureLogDao measureLogDao;
    private LiveData<List<MeasureLog>> mAllMeasureLog;

    MeasureLogRepository(Application application) {
        BandRoomDatabase db = BandRoomDatabase.getDatabase(application);
        measureLogDao = db.measureLogDao();
        mAllMeasureLog = measureLogDao.getAllMeasureLog();
    }

    LiveData<List<MeasureLog>> getAllMeasureLog() {
        return mAllMeasureLog;
    }

    public void insert(MeasureLog measureLog) {
        new MeasureLogRepository.insertAsyncTask(measureLogDao).execute(measureLog);
    }

    private static class insertAsyncTask extends AsyncTask<MeasureLog, Void, Void> {

        private MeasureLogDao mAsyncTaskDao;

        insertAsyncTask(MeasureLogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MeasureLog... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
