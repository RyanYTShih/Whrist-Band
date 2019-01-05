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
import com.epson.pulsenseapi.model.PhysicalFitnessModel;

import java.util.List;

public class DataInfo extends AppCompatActivity {

    private static final String TAG = "DataInfo";
    private RawdataViewModel mRawdataViewModel;
    private LiveData<List<Rawdata>> data;
    private UserViewModel mUserViewModel;
    private LiveData<List<User>> users;
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
        setContentView(R.layout.activity_data_info);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        HD_serial = intent.getStringExtra("dt");

        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
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

        mUserViewModel = ViewModelProviders.of(DataInfo.this).get(UserViewModel.class);
        users=mUserViewModel.getAllUsers();
        users.observe(DataInfo.this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "user list updated");
                if (users == null)
                    return;
                try {
                    DataInfo.this.users = mUserViewModel.getAllUsers();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                for(User u : users)
                    if(u.getName()==name)
                        ID=u.getId();
            }
        });

        mRawdataViewModel = ViewModelProviders.of(DataInfo.this).get(RawdataViewModel.class);
        data=mRawdataViewModel.getAllRawdata();
        data.observe(DataInfo.this, new Observer<List<Rawdata>>(){
            @Override
            public void onChanged(@Nullable List<Rawdata> data) {
                Log.d(TAG, "rawdata list updated");
                if (data == null)
                    return;
                try {
                    DataInfo.this.data = mRawdataViewModel.getAllRawdata();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                for(Rawdata d : data)
                    Toast.makeText(getApplication(),d.getId(),Toast.LENGTH_SHORT).show();
            }
        });

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
            Toast.makeText(getApplicationContext(),"上傳成功",Toast.LENGTH_SHORT).show();

        }
    };


}

