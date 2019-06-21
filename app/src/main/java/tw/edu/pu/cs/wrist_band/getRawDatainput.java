package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class getRawDatainput extends AppCompatActivity {

    private static final String TAG = "getRawDatainput";

    private TextView startTimeText;
    private TextView stopTimeText;

    private Button input_ana;
    private Intent intent;

//    private RawdataViewModel mRawdataViewModel;
    private MeasureLogViewModel mMeasureLogViewModel;
    private UserViewModel mUserViewModel;
    private LiveData<List<MeasureLog>> measureLog;

    private ListView dataList;
    private MeasureLogAdapter dataAdapter;

    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button13: {
                            intent = new Intent(getRawDatainput.this, healthreport.class);
                            startActivity(intent);
                            break;
                        }

                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("原始資料");
        setContentView(R.layout.activity_get_raw_datainput);

        startTimeText = findViewById(R.id.startTime);
        stopTimeText = findViewById(R.id.stopTime);

        input_ana = findViewById(R.id.button13);
        input_ana.setOnClickListener(myListener);

//        startTimeText.setText("資料開始時間");
//        stopTimeText.setText("資料結束時間");

        dataList = findViewById(R.id.RawDataList);
        dataAdapter = new MeasureLogAdapter(this, R.layout.measure_log_adapter);
        dataList.setAdapter(dataAdapter);

        mUserViewModel = ViewModelProviders.of(getRawDatainput.this).get(UserViewModel.class);
//        mRawdataViewModel = ViewModelProviders.of(getRawDatainput.this).get(RawdataViewModel.class);
        mMeasureLogViewModel = ViewModelProviders.of(getRawDatainput.this).get(MeasureLogViewModel.class);
        measureLog = mMeasureLogViewModel.getAllMeasureLog();
        measureLog.observe(getRawDatainput.this, new Observer<List<MeasureLog>>() {
            @Override
            public void onChanged(@Nullable List<MeasureLog> data) {
                Log.d(TAG, "rawdata list updated");
                if (data == null)
                    return;
                try {
                    getRawDatainput.this.measureLog = mMeasureLogViewModel.getAllMeasureLog();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                for (MeasureLog m : data) {

                    try {
                        dataAdapter.add(m);
                        Log.d(TAG, m.getSerialID() + "");
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        });

    }
}
