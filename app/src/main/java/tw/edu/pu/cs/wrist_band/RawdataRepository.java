package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RawdataRepository {

    private RawdataDao mRawdao;
    private LiveData<List<Rawdata>> mAllRawdata;

    RawdataRepository(Application application) {
        BandRoomDatabase db = BandRoomDatabase.getDatabase(application);
        mRawdao= db.rawdataDao();
        mAllRawdata = mRawdao.getAllRawdata();
    }

    LiveData<List<Rawdata>> getAllRawdata(){
        return mAllRawdata;
    }

    public void insert(Rawdata data) {
        new insertAsyncTask(mRawdao).execute(data);
    }

    private static class insertAsyncTask extends AsyncTask<Rawdata,Void ,Void>{

        private RawdataDao mAsyncTaskDao;

        insertAsyncTask(RawdataDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Rawdata... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
