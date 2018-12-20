package tw.edu.pu.cs.wrist_band;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

public class BLECOLLECT extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch blesw;
    ImageView ble;
    ProgressBar pgb;
    ListView lv;
    ListAdapter listAdapter;
    boolean blean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blecollect);
        ble=findViewById(R.id.imageView8);
        blesw = findViewById(R.id.switch3);
        blesw.setOnCheckedChangeListener(this);
        pgb=findViewById(R.id.progressBar5);
        lv=findViewById(R.id.listview1);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"PS-500-3R2    林小宏", "PS-500-5K2    時小唐", "PS-500-5T3    廖小勛"});

    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switch3:
                if (compoundButton.isChecked()) {
                    Toast.makeText(this, "藍芽:ON", Toast.LENGTH_SHORT).show();
                    ble.setColorFilter(Color.BLUE);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            lv.setAdapter(listAdapter);
                        }

                    }, 3 * 1000);
                }

                else {
                    Toast.makeText(this, "藍芽狀態:OFF", Toast.LENGTH_SHORT).show();
                    lv.setAdapter(null);
                    ble.setColorFilter(Color.BLACK);
                }
                break;

        }
    }
}

