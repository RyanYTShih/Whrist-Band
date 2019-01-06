package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    UserRepository(Application application) {
        BandRoomDatabase db = BandRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }

    public String getUserID(String name) throws ExecutionException, InterruptedException {
        return new selectIDAsyncTask(mUserDao).execute(name).get();
    }

    private static class selectIDAsyncTask extends AsyncTask<String,Void,String>{

        private UserDao mAsyncTaskDao;

        selectIDAsyncTask(UserDao dao) {
            mAsyncTaskDao=dao;
        }

        @Override
        protected String doInBackground(String ... s) {
            return mAsyncTaskDao.getUserID(s[0]);
        }
    }

    public String getUserNAME(String id) throws ExecutionException, InterruptedException{
        return new selectNAMEAsyncTask(mUserDao).execute(id).get();
    }

    private static class selectNAMEAsyncTask extends AsyncTask<String,Void,String>{

        private UserDao mAsyncTaskDao;

        selectNAMEAsyncTask(UserDao dao) {mAsyncTaskDao=dao; }

        @Override
        protected String doInBackground(String ... s) { return mAsyncTaskDao.getUserNAME(s[0]); }
    }
}
