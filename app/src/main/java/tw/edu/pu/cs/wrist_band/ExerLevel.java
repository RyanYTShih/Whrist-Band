package tw.edu.pu.cs.wrist_band;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerLevel extends AppCompatActivity implements SensorEventListener {
    SoundPool soundPool;
    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private Sensor mag_sensor;
    private float[] accValues = new float[3];
    private float[] magValues = new float[3];
    private float r[] = new float[9];
    private float values[] = new float[3];
    int sound,theEnd,Warn;
    TextView txv;
    Button btn;
    private LevelView levelView;
    private TextView tvHorz;
    private TextView tvVert;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_level);
        levelView = (LevelView) findViewById(R.id.gv_hv);
        tvVert = (TextView) findViewById(R.id.tvv_vertical);
        tvHorz = (TextView) findViewById(R.id.tvv_horz);
        txv = findViewById(R.id.txv);
        btn = findViewById(R.id.btn);
        img = findViewById(R.id.img);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        soundPool =new SoundPool(1, AudioManager.STREAM_MUSIC,5);
        theEnd = soundPool.load(this,R.raw.theend,1);
        sound = soundPool.load(this,R.raw.warning1,1);
        Warn = soundPool.load(this,R.raw.warn,1);
        btn.setOnClickListener(restart);
    }
    Button.OnClickListener restart = new
            Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    timer.start();
                    btn.setEnabled(false);
                    img.setVisibility(View.INVISIBLE);
                }
            };
    CountDownTimer timer = new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txv.setText("剩餘時間:"+millisUntilFinished/1000);
                soundPool.play(sound,1.0F,1.0F,0,0,1.0F);

        }

        @Override
        public void onFinish() {
            txv.setText("結束");
            txv.setEnabled(true);
            soundPool.play(theEnd,1.0F,1.0F,0,0,1.0F);
            btn.setEnabled(true);
            img.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {

        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {

        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accValues = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magValues = event.values.clone();
                break;
        }
        SensorManager.getRotationMatrix(r, null, accValues, magValues);
        SensorManager.getOrientation(r, values);


        float azimuth = values[0];
        float pitchAngle = values[1];
        float rollAngle = -values[2];

        onAngleChanged(rollAngle, pitchAngle, azimuth);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void onAngleChanged(float rollAngle, float pitchAngle, float azimuth) {

        levelView.setAngle(rollAngle, pitchAngle);

        tvHorz.setText(String.valueOf((int) Math.toDegrees(rollAngle)) + "°");
        tvVert.setText(String.valueOf((int) Math.toDegrees(pitchAngle)) + "°");

    }
}
