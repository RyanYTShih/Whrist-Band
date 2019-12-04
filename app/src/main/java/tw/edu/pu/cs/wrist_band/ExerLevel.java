package tw.edu.pu.cs.wrist_band;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerLevel extends AppCompatActivity implements SensorEventListener {
    SoundPool soundPool;
    int sound, theEnd, Warn, correct, county;
    TextView txv2, txv3;
    Button btn;
    ImageView img;
    CountDownTimer timer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txv2.setText("剩餘時間:" + millisUntilFinished / 1000);
            soundPool.play(sound, 1.0F, 1.0F, 0, 0, 1.0F);


        }

        @Override
        public void onFinish() {
            county = 0;
            txv2.setText("結束");
            txv2.setEnabled(true);
            soundPool.play(theEnd, 1.0F, 1.0F, 0, 0, 1.0F);
            btn.setEnabled(true);
            img.setVisibility(View.VISIBLE);


        }
    };
    Button.OnClickListener restart = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    county = 1;
                    timer.start();
                    btn.setEnabled(false);
                    img.setVisibility(View.INVISIBLE);

                }
            };
    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private Sensor mag_sensor;
    private float[] accValues = new float[3];
    private float[] magValues = new float[3];
    private float r[] = new float[9];
    private float values[] = new float[3];
    private LevelView levelView;
    private TextView tvHorz;
    private TextView tvVert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_level);
        getSupportActionBar().hide(); //隱藏標題
        levelView = (LevelView) findViewById(R.id.gv_hv);
        tvVert = (TextView) findViewById(R.id.tvv_vertical);
        tvHorz = (TextView) findViewById(R.id.tvv_horz);
        txv2 = findViewById(R.id.txv2);

        btn = findViewById(R.id.imgbtn);
        img = findViewById(R.id.img);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        theEnd = soundPool.load(this, R.raw.theend, 1);
        sound = soundPool.load(this, R.raw.warning1, 1);
        Warn = soundPool.load(this, R.raw.warn, 1);
        correct = soundPool.load(this, R.raw.correct, 1);

        btn.setOnClickListener(restart);

    }

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

        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    protected void onStop() {

        super.onStop();
        sensorManager.unregisterListener(this);
        timer.cancel();
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

        if (county == 1) {
            if (levelView.isCenter()) {

                btn.setEnabled(true);
                img.setVisibility(View.VISIBLE);
                txv2.setText("結束");
                soundPool.play(correct, 1.0F, 1.0F, 0, 0, 1.0F);
                timer.cancel();
                county = 0;
            }
        }
    }

}
