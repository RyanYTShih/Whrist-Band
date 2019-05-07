package tw.edu.pu.cs.wrist_band;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EzGame extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor sr;
    ImageView igv;
    TextView txv,txv2;
    RelativeLayout layout;
    SoundPool soundPool;
    int Loud;

    double mx = 0,my = 0;
    float x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ez_game);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sr =  sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        txv = findViewById(R.id.txv);
        igv = findViewById(R.id.igvmove);
        layout = findViewById(R.id.layout);
        txv2 = findViewById(R.id.txv2);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,5);
        Loud = soundPool.load(this,R.raw.loud,1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        if(mx == 0){
            mx = (layout.getWidth()-igv.getWidth())/10.0;
            my = (layout.getWidth()-igv.getWidth())/10.0;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) igv.getLayoutParams();
        params.leftMargin = (int)((5-event.values[0]*2* mx));
        params.topMargin = (int)((5+event.values[1]*2*my));
        igv.setLayoutParams(params);
        String values = "X:"+String.valueOf(event.values[0])+"\n"+
                "Y:"+String.valueOf(event.values[1])+"\n"+
                "Z:"+String.valueOf(event.values[2]);
        if (event.sensor.equals(sr))
            txv.setText(values);
        if (Math.abs(x)<1 && Math.abs(y)>4 && Math.abs(y)<5 && Math.abs(z)<10 && Math.abs(z)>8){
            txv2.setText("成功");

        }else{
            txv2.setText("請找尋小紅帽");
            soundPool.play(Loud,1.0F,1.0F,0,0,1.0F);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sm.registerListener(this,sr,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.unregisterListener(this);
    }


}


