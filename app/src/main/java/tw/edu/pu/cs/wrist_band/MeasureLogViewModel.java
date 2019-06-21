package tw.edu.pu.cs.wrist_band;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MeasureLogViewModel extends AndroidViewModel {
    private MeasureLogRepository mRepository;

    private LiveData<List<MeasureLog>> mAllMeasureLog;

    public MeasureLogViewModel(Application application) {
        super(application);
        mRepository = new MeasureLogRepository(application);
        mAllMeasureLog = mRepository.getAllMeasureLog();
    }

    LiveData<List<MeasureLog>> getAllMeasureLog() {
        return mAllMeasureLog;
    }

    public void insert(MeasureLog measureLog) {
        mRepository.insert(measureLog);
    }
}
