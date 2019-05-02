package tw.edu.pu.cs.wrist_band;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.BleScanCallback;
import com.epson.pulsenseapi.ble.callback.ConnectPeripheralCallback;
import com.epson.pulsenseapi.ble.callback.RequestEnumerateDataClassCallback;
import com.epson.pulsenseapi.ble.constant.DataClassId;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.model.IBinaryModel;
import com.epson.pulsenseapi.model.MeasureLogModel;
import com.epson.pulsenseview.wellnesscommunication.bluetooth.Peripheral;

public class ScanActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10;

    private WellnessCommunication instance;

    private MeasureLogModel measureLogModel;

    private TextView textDeviceName, textUUID, textMeasureLog;
    private String strMeasureLog = "";

    private Button unregisterButton;
    private Button connectButton;
    private Button disconnectButton;
    private Button retrieveButton;

    private ProgressBar retrieveProgress;

    private Switch bluetoothSwitch;
    private Switch scanSwitch;

    private ListView deviceList;

    private DeviceAdapter peripheralAdapter;

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
                                            new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                                            LOCATION_PERMISSION_REQUEST_CODE);
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

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

        peripheralAdapter = new DeviceAdapter(this, R.layout.device_adapter);
        deviceList.setAdapter(peripheralAdapter);

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
                                new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }

                    startScan();
                } else {
                    instance.stopScan();
                    Toast.makeText(ScanActivity.this, "stopScan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        unregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.unregisterPeripheral();
                textDeviceName.setText("");
                textUUID.setText("");
                unregisterButton.setEnabled(false);
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instance.getRegisteredPeripheral() != null) {
                    final ProgressDialog connectProgress = ProgressDialog.show(ScanActivity.this, "Connecting", "", true);
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
                instance.requestEnumerateDataClass(DataClassId.MeasurementLog, new RequestEnumerateDataClassCallback() {
                    @Override
                    public void onProgress(int i, int i1) {
                        strMeasureLog += i + "/" + i1 + ". ";
                        textMeasureLog.setText(strMeasureLog);
                        retrieveProgress.setMax(i1);
                        retrieveProgress.setProgress(i, true);
                    }

                    @Override
                    public void onStartEnumerate(int i) {
                        Toast.makeText(ScanActivity.this, "StartEnumerate - " + i, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnumerate(IBinaryModel iBinaryModel) {
                        measureLogModel = (MeasureLogModel) iBinaryModel;
                        String s = measureLogModel.getMeasureStartDate().getYear() + "/"
                                + measureLogModel.getMeasureStartDate().getMonth() + "/"
                                + measureLogModel.getMeasureStartDate().getDay() + " "
                                + measureLogModel.getMeasureStartTime().getHour() + ":"
                                + measureLogModel.getMeasureStartTime().getMinute() + ":"
                                + measureLogModel.getMeasureStartTime().getSecond() + " steps:"
                                + measureLogModel.getSteps() + " distance:"
                                + measureLogModel.getDistance() + " exerciseCalories:"
                                + measureLogModel.getExerciseCalories() + " heartRate:";
                        int heartRate[] = measureLogModel.getHeartrate();
                        for (int r: heartRate) {
                            s += r + ",";
                        }
                        s += measureLogModel.getMeasureStopDate().getYear() + "/"
                                + measureLogModel.getMeasureStopDate().getMonth() + "/"
                                + measureLogModel.getMeasureStopDate().getDay() + " "
                                + measureLogModel.getMeasureStopTime().getHour() + ":"
                                + measureLogModel.getMeasureStopTime().getMinute() + ":"
                                + measureLogModel.getMeasureStopTime().getSecond() + "\n";
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

    public void registerDevice(Peripheral peripheral) {
        Log.d("p", peripheral.getName() + ", " + peripheral.getUuid());
        try {
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
