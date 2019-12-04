package tw.edu.pu.cs.wrist_band;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.BleScanCallback;
import com.epson.pulsenseapi.ble.callback.ConnectPeripheralCallback;
import com.epson.pulsenseapi.ble.callback.RequestDifferenceEnumerateDataClassCallback;
import com.epson.pulsenseapi.ble.constant.DataClassId;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.model.IBinaryModel;
import com.epson.pulsenseapi.model.MeasureLogModel;
import com.epson.pulsenseview.wellnesscommunication.bluetooth.Peripheral;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScanActivity extends AppCompatActivity {

    private static final String TAG = "ScanActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10;
    ArrayAdapter<String> UserList;
    List<String> user = new ArrayList<>();
    private WellnessCommunication instance;
    private MeasureLogModel measureLogModel;

    private UserViewModel mUserViewModel;
    //    private RawdataViewModel mRawDataViewModel;
    private MeasureLogViewModel mMeasureLogViewModel;

    private LiveData<List<User>> users;

    private TextView textDeviceName, textUUID, textMeasureLog;

    private String strMeasureLog = "";
    private String name, ID;

    private Button unregisterButton;
    private Button connectButton;
    private Button disconnectButton;
    private Button retrieveButton;

    private ProgressBar retrieveProgress;

    private Switch bluetoothSwitch;
    private Switch scanSwitch;

    private ListView deviceList;

    private Spinner spinner;

    private DeviceAdapter peripheralAdapter;
    private Spinner.OnItemSelectedListener spinnerItemSelLis = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
            name = parent.getSelectedItem().toString();
            try {
                ID = mUserViewModel.getUserID(name);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }

        @Override
        public void onNothingSelected(AdapterView parent) {
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.alert)
                            .setMessage(R.string.need_permission)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(ScanActivity.this,
                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            LOCATION_PERMISSION_REQUEST_CODE);
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.disconnectPeripheral();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setTitle("手環連線 - 工程模式");
        getSupportActionBar().hide();
        instance = WellnessCommunication.getInstance(this);

        textDeviceName = findViewById(R.id.textDeviceName);
        textUUID = findViewById(R.id.textUUID);
        textMeasureLog = findViewById(R.id.textMeasureLog);

        unregisterButton = findViewById(R.id.buttonUnregister);
        connectButton = findViewById(R.id.buttonConnect);
        disconnectButton = findViewById(R.id.buttonDisconnect);
        retrieveButton = findViewById(R.id.buttonRetrieve);

        retrieveProgress = findViewById(R.id.progressBar);

        bluetoothSwitch = findViewById(R.id.switchBluetooth);
        scanSwitch = findViewById(R.id.switchScan);

        deviceList = findViewById(R.id.deviceList);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(spinnerItemSelLis);

        peripheralAdapter = new DeviceAdapter(this, R.layout.device_adapter);
        deviceList.setAdapter(peripheralAdapter);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        users = mUserViewModel.getAllUsers();
        users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "user list updated");
                if (users == null)
                    return;
                try {
                    ScanActivity.this.users = mUserViewModel.getAllUsers();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                int i = 0;
                for (User u : users) {
                    if (u.getRole() == Role.Elder) {
                        user.add(u.getName());
                        i++;
                    }
                }
                UserList = new ArrayAdapter<>(ScanActivity.this, android.R.layout.simple_spinner_dropdown_item, user);
                spinner.setAdapter(UserList);
            }
        });

//        mRawDataViewModel = ViewModelProviders.of(this).get(RawdataViewModel.class);
        mMeasureLogViewModel = ViewModelProviders.of(this).get(MeasureLogViewModel.class);

        if (instance.isBleEnabled()) {
            bluetoothSwitch.setChecked(true);
        } else {
            bluetoothSwitch.setChecked(false);
            scanSwitch.setEnabled(false);
        }
        final Peripheral registeredPeripheral = instance.getRegisteredPeripheral();
        if (registeredPeripheral != null) {
            textDeviceName.setText(registeredPeripheral.getName());
            textUUID.setText(registeredPeripheral.getUuid());
            unregisterButton.setEnabled(true);
            connectButton.setEnabled(true);
        } else {
            unregisterButton.setEnabled(false);
//            connectButton.setEnabled(false);
        }

        bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                instance.enableBle(b);
                scanSwitch.setEnabled(b);
                if (!b) {
                    scanSwitch.setChecked(false);
                }
            }
        });

        scanSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ScanActivity.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                    unregisterDevice();

                    startScan();
                } else {
                    instance.stopScan();
                    peripheralAdapter.clear();
                    Toast.makeText(ScanActivity.this, "stopScan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        unregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregisterDevice();
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instance.getRegisteredPeripheral() != null) {
                    final ProgressDialog connectProgress = ProgressDialog.show(ScanActivity.this, "Connecting", "", true, true);
                    instance.connectPeripheral(new ConnectPeripheralCallback() {
                        @Override
                        public void onConnected() {
                            connectProgress.dismiss();
                            Toast.makeText(ScanActivity.this, "Connected", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onDisconnected() {
                            Toast.makeText(ScanActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(LocalError localError) {
                            Toast.makeText(ScanActivity.this, localError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.disconnectPeripheral();
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.requestDifferenceEnumerateDataClass(DataClassId.MeasurementLog, new RequestDifferenceEnumerateDataClassCallback() {
                    @Override
                    public void onProgress(int i, int i1) {

                    }

                    @Override
                    public void onStartEnumerate(int i) {
                        Toast.makeText(ScanActivity.this, "StartEnumerate - " + i, Toast.LENGTH_SHORT).show();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEnumerate(IBinaryModel iBinaryModel, int i, int i1) {
                        measureLogModel = (MeasureLogModel) iBinaryModel;

                        strMeasureLog += i + "/" + i1 + ". ";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textMeasureLog.setText(strMeasureLog);
                            }
                        });
                        retrieveProgress.setMax(i1);
                        retrieveProgress.setProgress(i, true);

//                        Rawdata rawdata = new Rawdata(
//                                ID,
//                                textUUID.getText().toString(),
////                                measureLogModel.getMeasureStartDate(),
////                                measureLogModel.getMeasureStartTime(),
////                                measureLogModel.getMeasureStopDate(),
////                                measureLogModel.getMeasureStopTime(),
//                                measureLogModel
//                        );

                        String heartrateString = "";
                        int[] heartRate = measureLogModel.getHeartrate();
                        for (int j = 0; j < heartRate.length; j++) {
                            heartrateString += heartRate[j] + "";
                            if (j < heartRate.length - 1) {
                                heartrateString += ",";
                            }
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.TAIWAN);

                        Date startDate = new Date(), stopDate = new Date();
                        try {
                            startDate = dateFormat.parse(measureLogModel.getMeasureStartDate().getYear() + "/"
                                    + measureLogModel.getMeasureStartDate().getMonth() + "/"
                                    + measureLogModel.getMeasureStartDate().getDay() + " "
                                    + measureLogModel.getMeasureStartTime().getHour() + ":"
                                    + measureLogModel.getMeasureStartTime().getMinute() + ":"
                                    + measureLogModel.getMeasureStartTime().getSecond());
                            stopDate = dateFormat.parse(measureLogModel.getMeasureStopDate().getYear() + "/"
                                    + measureLogModel.getMeasureStopDate().getMonth() + "/"
                                    + measureLogModel.getMeasureStopDate().getDay() + " "
                                    + measureLogModel.getMeasureStopTime().getHour() + ":"
                                    + measureLogModel.getMeasureStopTime().getMinute() + ":"
                                    + measureLogModel.getMeasureStopTime().getSecond());
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                        }

                        MeasureLog measureLog = new MeasureLog(
                                0L,
                                ID,
                                textDeviceName.getText().toString(),
                                measureLogModel.getSteps(),
                                new BigInteger(measureLogModel.getDistance() + ""),
                                measureLogModel.getExerciseCalories(),
                                measureLogModel.getRestCalories(),
                                heartrateString,
                                startDate,
                                stopDate
                        );

                        mMeasureLogViewModel.insert(measureLog);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN);

                        webapi.api_UploadMeasureLog(
                                measureLog.getSerialID() + "",
                                measureLog.getPersonalID(),
                                measureLog.getBandID(),
                                measureLog.getSteps() + "",
                                measureLog.getDistance() + "",
                                measureLog.getExerciseCalories() + "",
                                measureLog.getRestCalories() + "",
                                measureLog.getHeartrate(),
                                simpleDateFormat.format(measureLog.getStartTime()),
                                simpleDateFormat.format(measureLog.getStopTime()));

                        String s = measureLog + "\n";
                        strMeasureLog += s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textMeasureLog.setText(strMeasureLog);
                            }
                        });
                    }

                    @Override
                    public void onFinishEnumerate() {
                        Toast.makeText(ScanActivity.this, "FinishEnumerate", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(LocalError localError) {
                        Toast.makeText(ScanActivity.this, localError.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ScanActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void startScan() {
        instance.scanPeripherals(new BleScanCallback() {
            @Override
            public void onScan(final Peripheral peripheral) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addDevice(peripheral);
                    }
                });
            }

            @Override
            public void onError(LocalError localError) {
                Toast.makeText(ScanActivity.this, localError + "", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addDevice(Peripheral peripheral) {
        peripheralAdapter.add(peripheral);
    }

    private void unregisterDevice() {
        try {
            instance.unregisterPeripheral();
            textDeviceName.setText("");
            textUUID.setText("");
            unregisterButton.setEnabled(false);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void registerDevice(Peripheral peripheral) {
        Log.d(TAG, peripheral.getName() + ", " + peripheral.getUuid());
        try {
            scanSwitch.setChecked(false);
            instance.registerPeripheral(peripheral);
            unregisterButton.setEnabled(true);
            Peripheral registeredPeripheral = instance.getRegisteredPeripheral();
            textDeviceName.setText(registeredPeripheral.getName());
            textUUID.setText(registeredPeripheral.getUuid());
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
