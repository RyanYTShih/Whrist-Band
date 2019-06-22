package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class SleepSummaryViewModel extends AndroidViewModel {

    private SleepSummaryRepository mRepository;

    private LiveData<List<SleepSummary>> mAllSleepSummary;

    public SleepSummaryViewModel(Application application) {
        super(application);
        mRepository = new SleepSummaryRepository(application);
        mAllSleepSummary = mRepository.getAllSleepSummary();
    }

    LiveData<List<SleepSummary>> getAllSleepSummary() {
        return mAllSleepSummary;
    }

    public void insert(SleepSummary sleepSummary) {
        mRepository.insert(sleepSummary);
    }
}
