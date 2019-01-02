package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.pulsenseapi.WellnessCommunication;
import com.epson.pulsenseapi.ble.callback.BleScanCallback;
import com.epson.pulsenseapi.ble.callback.ConnectPeripheralCallback;
import com.epson.pulsenseapi.ble.constant.LocalError;
import com.epson.pulsenseapi.exception.WellnessCommunicationException;
import com.epson.pulsenseview.wellnesscommunication.bluetooth.Peripheral;

import java.util.ArrayList;

public class BLECOLLECT extends AppCompatActivity {
    private WellnessCommunication mWellnessCommunication;
    private TextView name_text,uuid_text,name_,uuid_;
    private Switch sw;
    private ListView name_list,uuid_list;
    private Button start_button,stop_button,register_button,unregister_button,connect_button;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> uuid = new ArrayList<>();
    private ArrayList<Peripheral> device = new ArrayList<>();
    private ArrayAdapter name_adapter,uuid_adpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blecollect);
        name_text = findViewById(R.id.name_text);
        uuid_text = findViewById(R.id.uuid_text);
        name_ = findViewById(R.id.name);
        uuid_ = findViewById(R.id.uuid);
        sw = findViewById(R.id.switch1);
        name_list = findViewById(R.id.ListView1);
        uuid_list = findViewById(R.id.ListView2);
        start_button = findViewById(R.id.button);
        stop_button = findViewById(R.id.button2);
        register_button = findViewById(R.id.register_button);
        unregister_button = findViewById(R.id.unregister_button);
        connect_button = findViewById(R.id.connect_button);

        name_adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,name);
        name_list.setAdapter(name_adapter);

        uuid_adpater = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,uuid);
        uuid_list.setAdapter(uuid_adpater);

        sw.setOnCheckedChangeListener(swChancedChangeListener);
        start_button.setOnClickListener(startbuttonChangeListener);
        stop_button.setOnClickListener(stopbuttonChangeListener);
        register_button.setOnClickListener(registerbuttonChangeListener);
        unregister_button.setOnClickListener(unregisterbuttonChangeListener);
        name_list.setOnItemClickListener(namelistOnItemClickListener);
        connect_button.setOnClickListener(connectbuttonChangeListner);

        mWellnessCommunication = WellnessCommunication.getInstance(getApplicationContext());
        mWellnessCommunication.setConnectPeripheralCallback(mConnectPheCallback);

        Peripheral peripheral = mWellnessCommunication.getRegisteredPeripheral();
        if(peripheral!=null){
            name_.setText(peripheral.getName());
            uuid_.setText(peripheral.getUuid());

        }

    }

    private CompoundButton.OnCheckedChangeListener swChancedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mWellnessCommunication.enableBle(isChecked);
        }
    };

    private View.OnClickListener startbuttonChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWellnessCommunication.scanPeripherals(new BleScanCallback() {
                @Override
                public void onScan(Peripheral peripheral) {
                    if(name.size() > 0) {
                        for (int i = 0; i < name.size(); i++) {
                            if (name.get(i).equals(peripheral.getName()))
                                return;
                        }
                        name.add(peripheral.getName());
                        uuid.add(peripheral.getUuid());
                        device.add(peripheral);
                    }
                    else{
                        name.add(peripheral.getName());
                        uuid.add(peripheral.getUuid());
                        device.add(peripheral);
                    }
                }
                @Override
                public void onError(LocalError localError) {
                }
            });
            name_adapter.notifyDataSetChanged();
            uuid_adpater.notifyDataSetChanged();

        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mWellnessCommunication.setConnectPeripheralCallback(mConnectPheCallback);
    }
    @Override
    protected void onPause() {
        super.onPause();

        mWellnessCommunication.setConnectPeripheralCallback(null);

    }

    private View.OnClickListener stopbuttonChangeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            mWellnessCommunication.stopScan();
            name_adapter.clear();
            uuid_adpater.clear();
            name_text.setText("name");
            uuid_text.setText("uuid");
        }
    };

    private  View.OnClickListener registerbuttonChangeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            mWellnessCommunication.stopScan();
            name_adapter.clear();
            uuid_adpater.clear();
            Peripheral peripheral = new Peripheral();
            try{
                for(int i=0;i<device.size();i++) {
                    if (name_text.getText().equals(device.get(i).getName())){
                        peripheral = peripheral.create(device.get(i).getName(), device.get(i).getUuid(),device.get(i).getRssi(), device.get(i).isBonded());
                        break;
                    }
                }
                mWellnessCommunication.registerPeripheral(peripheral);
                name_.setText(peripheral.getName());
                uuid_.setText(peripheral.getUuid());
                Toast.makeText(BLECOLLECT.this,"註冊成功",Toast.LENGTH_SHORT).show();
            }
            catch(WellnessCommunicationException e){
                Toast.makeText(getApplicationContext(),e.getLocalError().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener unregisterbuttonChangeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            mWellnessCommunication.unregisterPeripheral();
            name_.setText(" ");
            uuid_.setText(" ");
            Toast.makeText(BLECOLLECT.this,"取消註冊",Toast.LENGTH_SHORT).show();
        }
    };

    private  AdapterView.OnItemClickListener namelistOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            name_text.setText(parent.getItemAtPosition(position).toString());
            uuid_text.setText(uuid_list.getItemAtPosition(position).toString());
        }
    };
    private  View.OnClickListener connectbuttonChangeListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mWellnessCommunication.connectPeripheral(mConnectPheCallback);
        }
    };

    private ConnectPeripheralCallback mConnectPheCallback = new ConnectPeripheralCallback() {
        @Override
        public void onConnected() {

            Intent intent = new Intent(BLECOLLECT.this, MenuActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            startActivity(intent);

        }

        @Override
        public void onDisconnected() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BLECOLLECT.this);
                    String message = getString(R.string.ble_disabled_message);
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setPositiveButton(R.string.dialog_positive_button, null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }

        @Override
        public void onError(LocalError localError) {

        }
    };

}
