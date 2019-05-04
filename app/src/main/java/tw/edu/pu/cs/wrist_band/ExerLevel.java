package tw.edu.pu.cs.wrist_band;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private LevelView levelView;
    private TextView tvHorz;
    private TextView tvVert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_level);
        levelView = (LevelView) findViewById(R.id.gv_hv);
        tvVert = (TextView) findViewById(R.id.tvv_vertical);
        tvHorz = (TextView) findViewById(R.id.tvv_horz);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

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
