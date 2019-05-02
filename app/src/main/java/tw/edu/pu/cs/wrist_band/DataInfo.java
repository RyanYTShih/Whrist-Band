package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.RequestGetDataClassCallback;
import com.epson.pulsenseapi.ble.callback.RequestSetDataClassCallback;
import com.epson.pulsenseapi.ble.constant.DataClassId;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.model.IBinaryModel;
import com.epson.pulsenseapi.model.LightMeasureLogModel;
import com.epson.pulsenseapi.model.PhysicalFitnessModel;
import com.epson.pulsenseapi.model.WorkoutSummaryModel;

import java.util.List;

public class DataInfo extends AppCompatActivity {

    private static final String TAG = "DataInfo";
    private RawdataViewModel mRawdataViewModel;
    private LiveData<List<Rawdata>> data;
    WellnessCommunication mWellnessCommunication;
    Button mGetButton;
    Button mSetButton;
    Button mUpload;
    EditText mBaseEdit;
    EditText mMaxEdit;
    EditText mRestEdit;
    PhysicalFitnessModel mModel;
    TextView HeartRateBase, HeartRateMax, HeartRateRest;
    String ID,HD_serial,Heartrate,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("原始資料");
        setContentView(R.layout.activity_data_info);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ID = intent.getStringExtra("ID");
        HD_serial = intent.getStringExtra("dt");

        HeartRateBase = findViewById(R.id.HeartRateBase);
        HeartRateMax =  findViewById(R.id.HeartRateMax);
        HeartRateRest =  findViewById(R.id.HeartRateRest);
        mSetButton =  findViewById(R.id.mSetButton);
        mGetButton = findViewById(R.id.mGetButton);
        mBaseEdit =  findViewById(R.id.mBaseEdit);
        mMaxEdit =  findViewById(R.id.mMaxEdit);
        mRestEdit = findViewById(R.id.mRestEdit);


        mGetButton.setOnClickListener(mGetButtonClickListener);
        mSetButton.setOnClickListener(mSetButtonClickListener);
        mSetButton.setEnabled(false);

        mUpload = findViewById(R.id.upload_but);
        mUpload.setOnClickListener(mUploadClickListener);

        mWellnessCommunication = WellnessCommunication.getInstance(getApplicationContext());

        mRawdataViewModel = ViewModelProviders.of(DataInfo.this).get(RawdataViewModel.class);

    }

    private View.OnClickListener mGetButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mWellnessCommunication.requestGetDataClass(DataClassId.PhysicalFitness, new RequestGetDataClassCallback() {
                @Override
                public void onSuccess(final IBinaryModel model) {
                    mModel = (PhysicalFitnessModel) model;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSetButton.setEnabled(true);
                            mBaseEdit.setText(String.valueOf(mModel.getHeartRateBase()));
                            mMaxEdit.setText(String.valueOf(mModel.getHeartRateMax()));
                            mRestEdit.setText(String.valueOf(mModel.getHeartRateRest()));

                            Heartrate = mBaseEdit.getText().toString();

                        }
                    });
                }

                @Override
                public void onError(LocalError localError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }

    };
    private View.OnClickListener mSetButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            int base = Integer.valueOf(mBaseEdit.getText().toString());
            if (base < 28 || base > 240) {
                return;
            }
            mModel.setHeartRateBase(base);


            int max = Integer.valueOf(mMaxEdit.getText().toString());
            if (max < 28 || max > 240) {
                return;
            }
            mModel.setHeartRateMax(max);


            int rest = Integer.valueOf(mRestEdit.getText().toString());
            if (rest < 28 || rest > 240) {
                return;
            }
            mModel.setHeartRateRest(rest);

            mWellnessCommunication.requestSetDataClass(mModel, DataClassId.PhysicalFitness, new RequestSetDataClassCallback() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

                @Override
                public void onError(LocalError localError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }
    };

    private View.OnClickListener mUploadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRawdataViewModel.insert(new Rawdata(ID,HD_serial,null,null,null,Heartrate));
            Toast.makeText(getApplicationContext(),"儲存成功",Toast.LENGTH_SHORT).show();
        }
    };

}

