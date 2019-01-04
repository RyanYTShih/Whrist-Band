package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.RequestGetDataClassCallback;
import com.epson.pulsenseapi.ble.constant.DataClassId;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.model.BioInformationModel;
import com.epson.pulsenseapi.model.HardwareInformationModel;
import com.epson.pulsenseapi.model.IBinaryModel;

import java.util.List;

import static java.lang.String.valueOf;

public class MenuActivity extends AppCompatActivity {

    private String TAG="MenuActivity";
    private UserViewModel mUserViewModel;
    private LiveData<List<User>> users;
    TextView txv,dt;
    Button btn,btn2;
    Spinner spinner;
    WellnessCommunication mWellnessCommunication;
    EditText edt;
    String ID,band_serial;
    String user[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        band_serial=intent.getStringExtra("name");

        txv = findViewById(R.id.txv);
        btn2 = findViewById(R.id.btn2);
        dt = findViewById(R.id.dt);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(spinnerItemSelLis);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        users = mUserViewModel.getAllUsers();
        users.observe(MenuActivity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "user list updated");
                if (users == null)
                    return;
                try {
                    MenuActivity.this.users = mUserViewModel.getAllUsers();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                int i=0;
                for(User u : users){
                    if(u.getRole()==Role.Elder) {
                        user[i] = u.getId();
                        i++;
                    }
                }
                ArrayAdapter<String> UserList = new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_spinner_dropdown_item, user);
                spinner.setAdapter(UserList);
            }
        });
        dt.setText(band_serial);

        //request.setOnClickListener(mrequestListener);
        mWellnessCommunication = WellnessCommunication.getInstance(getApplicationContext());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openData();
            }
        });
    }

    /*private View.OnClickListener mrequestListener = new View.OnClickListener(){

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
    };*/

    public void openData() {
        /*if (edt.getText().toString().matches("")) {
            Toast.makeText(MenuActivity.this,"請填入您的身分證ID",Toast.LENGTH_SHORT).show();
        } else {*/
        String str = "您的身分證字號為：" +ID+"\n手環名稱為："+band_serial;
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
        Intent its = new Intent(MenuActivity.this, DataInfo.class);
        its.putExtra("ID",ID);
        its.putExtra("dt",dt.getText().toString());
        startActivity(its);
       // }
    }

    private Spinner.OnItemSelectedListener spinnerItemSelLis =
            new Spinner.OnItemSelectedListener () {
                public void onItemSelected(AdapterView parent, View v, int position, long id) {
                    ID = parent.getSelectedItem().toString();
                }
                public void onNothingSelected(AdapterView parent) {
                }
            };


}
