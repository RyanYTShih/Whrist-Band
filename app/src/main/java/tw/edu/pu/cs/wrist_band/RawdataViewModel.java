package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RawdataViewModel extends AndroidViewModel {

    private RawdataRepository mRepository;

    private LiveData<List<Rawdata>> mAllRawdata;

    public RawdataViewModel(Application application) {
        super(application);
        mRepository = new RawdataRepository(application);
        mAllRawdata = mRepository.getAllRawdata();
    }

    LiveData<List<Rawdata>> getAllRawdata() {
        return mAllRawdata;
    }

    public void insert(Rawdata data) {
        mRepository.insert(data);
    }
}
