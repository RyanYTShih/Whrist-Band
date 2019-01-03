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
    TextView txv,dt;
    Button btn,request,btn2;
    WellnessCommunication mWellnessCommunication;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txv = findViewById(R.id.txv);
        btn2 = findViewById(R.id.btn2);
        request = findViewById(R.id.request);
        dt = findViewById(R.id.dt);
        edt = findViewById(R.id.edt);



        request.setOnClickListener(mrequestListener);
        mWellnessCommunication = WellnessCommunication.getInstance(getApplicationContext());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openData();
            }
        });
    }

    private View.OnClickListener mrequestListener = new View.OnClickListener(){

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
                            Toast.makeText(MenuActivity.this,"正在讀取",Toast.LENGTH_LONG).show();
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
    public void openData() {
        if (edt.getText().toString().matches("")) {
            Toast.makeText(MenuActivity.this,"請填入您的身分證ID",Toast.LENGTH_SHORT).show();
        } else {
            Intent its = new Intent(MenuActivity.this, DataInfo.class);
            startActivity(its);
        }
    }

}
