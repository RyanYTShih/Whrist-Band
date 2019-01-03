package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.RequestGetDataClassCallback;
import com.epson.pulsenseapi.ble.constant.DataClassId;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.model.BioInformationModel;
import com.epson.pulsenseapi.model.HardwareInformationModel;
import com.epson.pulsenseapi.model.IBinaryModel;

public class MenuActivity extends AppCompatActivity {
    TextView txv, dt;
    Button btn, request, mGetButton, mSetButton, btn2;
    WellnessCommunication mWellnessCommunication;
    EditText edt;
    BioInformationModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txv = findViewById(R.id.txv);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        request = findViewById(R.id.request);
        dt = findViewById(R.id.dt);
        edt = findViewById(R.id.edt);
        mGetButton = findViewById(R.id.mGetButton);
        mSetButton = findViewById(R.id.mSetButton);


        request.setOnClickListener(mrequestListener);
        mGetButton.setOnClickListener(mGetButtonListner);
        mSetButton.setOnClickListener(mSetButtonListner);
        mWellnessCommunication = WellnessCommunication.getInstance(getApplicationContext());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openData();
            }
        });
    }

    public void openActivity() {
        finish();
    }

    private View.OnClickListener mrequestListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mWellnessCommunication.requestGetDataClass(DataClassId.HardwareInformation, new RequestGetDataClassCallback() {
                @Override
                public void onSuccess(final IBinaryModel model) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HardwareInformationModel hardwareInformationModel = (HardwareInformationModel) model;
                            dt.setText(hardwareInformationModel.getSerialNumber());
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

    private View.OnClickListener mGetButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWellnessCommunication.requestGetDataClass(DataClassId.BioInformation, new RequestGetDataClassCallback() {
                @Override
                public void onSuccess(final IBinaryModel model) {
                    mModel = (BioInformationModel) model;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSetButton.setEnabled(true);
                            edt.setText(String.valueOf(mModel.getWeight()));
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
    private View.OnClickListener mSetButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int Id = Integer.valueOf(edt.getText().toString());
            mModel.setWeight(Id);
        }
    };

    public void openData() {
        Intent its = new Intent(MenuActivity.this, DataInfo.class);
        its.putExtra("edt",edt.getText().toString());
        its.putExtra("dt",dt.getText().toString());
        startActivity(its);
    }

}
