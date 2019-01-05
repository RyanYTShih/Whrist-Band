package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public String getUserID(String name) throws ExecutionException, InterruptedException {
        return mRepository.getUserID(name);
    }

    public void insert(User user) {
        mRepository.insert(user);
    }
}
