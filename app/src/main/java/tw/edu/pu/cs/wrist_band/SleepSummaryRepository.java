package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SleepSummaryRepository {

    private SleepSummaryDao sleepSummaryDao;
    private LiveData<List<SleepSummary>> mAllSleepSummary;

    SleepSummaryRepository(Application application) {
        BandRoomDatabase db = BandRoomDatabase.getDatabase(application);
        sleepSummaryDao = db.sleepSummaryDao();
        mAllSleepSummary = sleepSummaryDao.getAllSleepSummary();
    }

    LiveData<List<SleepSummary>> getAllSleepSummary() {
        return mAllSleepSummary;
    }

    public void insert(SleepSummary sleepSummary) {
        new SleepSummaryRepository.insertAsyncTask(sleepSummaryDao).execute(sleepSummary);
    }

    private static class insertAsyncTask extends AsyncTask<SleepSummary, Void, Void> {

        private SleepSummaryDao mAsyncTaskDao;

        insertAsyncTask(SleepSummaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SleepSummary... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
