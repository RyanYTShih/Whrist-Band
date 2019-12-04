package tw.edu.pu.cs.wrist_band;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EzGame extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sr;
    ImageView igv, redcap;
    TextView txv2;
    RelativeLayout layout;
    ImageButton imgbtn;
    double mx = 0, my = 0;
    int county;
    float x, y, z;
    ImageButton.OnClickListener wolf = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            igv.setVisibility(View.VISIBLE);
            redcap.setVisibility(View.VISIBLE);
            imgbtn.setVisibility(View.INVISIBLE);
            county = 1;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ez_game);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        igv = findViewById(R.id.igvmove);
        layout = findViewById(R.id.layout);
        txv2 = findViewById(R.id.txv2);
        getSupportActionBar().hide();
        imgbtn = findViewById(R.id.imgbtn);
        imgbtn.setOnClickListener(wolf);
        redcap = findViewById(R.id.redcap);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        if (mx == 0) {
            mx = (layout.getWidth() - igv.getWidth()) / 10.0;
            my = (layout.getWidth() - igv.getWidth()) / 10.0;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) igv.getLayoutParams();
        params.leftMargin = (int) ((5 - event.values[0] * 2 * mx));
        params.topMargin = (int) ((5 + event.values[1] * 2 * my));
        igv.setLayoutParams(params);
        String values = "X:" + String.valueOf(event.values[0]) + "\n" +
                "Y:" + String.valueOf(event.values[1]) + "\n" +
                "Z:" + String.valueOf(event.values[2]);
        if (county == 1) {
            if (Math.abs(x) < 1 && Math.abs(y) > 2 && Math.abs(y) < 5 && Math.abs(z) < 9 && Math.abs(z) > 8) {
                txv2.setText("成功");

            } else {
                txv2.setText("請找尋小紅帽");
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.unregisterListener(this);
    }


}


