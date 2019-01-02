package tw.edu.pu.cs.wrist_band;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.RequestGetDataClassCallback;
import com.epson.pulsenseapi.ble.callback.RequestSetDataClassCallback;
import com.epson.pulsenseapi.ble.constant.DataClassId;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.model.IBinaryModel;
import com.epson.pulsenseapi.model.PhysicalFitnessModel;

public class DataInfo extends AppCompatActivity {
    WellnessCommunication mWellnessCommunication;
    Button mGetButton;
    Button mSetButton;
    EditText mBaseEdit;
    EditText mMaxEdit;
    EditText mRestEdit;
    PhysicalFitnessModel mModel;
    TextView HeartRateBase, HeartRateMax, HeartRateRest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_info);
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


        mWellnessCommunication = WellnessCommunication.getInstance(getApplicationContext());

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
}

