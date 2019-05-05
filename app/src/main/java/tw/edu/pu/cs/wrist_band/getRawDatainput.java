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

    private RawdataViewModel mRawdataViewModel;
    private UserViewModel mUserViewModel;
    private LiveData<List<Rawdata>> data;

    private ListView dataList;
    private RawDataAdapter dataAdapter;

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

        startTimeText.setText("資料開始時間");
        stopTimeText.setText("資料結束時間");

        dataList = findViewById(R.id.RawDataList);
        dataAdapter = new RawDataAdapter(this, R.layout.rawdata_adapter);
        dataList.setAdapter(dataAdapter);

        mUserViewModel = ViewModelProviders.of(getRawDatainput.this).get(UserViewModel.class);
        mRawdataViewModel = ViewModelProviders.of(getRawDatainput.this).get(RawdataViewModel.class);
        data = mRawdataViewModel.getAllRawdata();
        data.observe(getRawDatainput.this, new Observer<List<Rawdata>>() {
            @Override
            public void onChanged(@Nullable List<Rawdata> rawdata) {
                Log.d(TAG, "rawdata list updated");
                if (rawdata == null)
                    return;
                try {
                    getRawDatainput.this.data = mRawdataViewModel.getAllRawdata();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                for (Rawdata r : rawdata) {

                    try {
                        dataAdapter.add(r);
                        Log.d(TAG, r.getSerial_id() + "");
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        });

    }
}
