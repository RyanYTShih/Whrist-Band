package tw.edu.pu.cs.wrist_band;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ELDER extends AppCompatActivity {
    Intent intent;
    ImageButton searchre, motion, mapbtn, musicbtn;
    View v;
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.datasearch: {
                            intent = new Intent(ELDER.this, healthreport.class);
                            startActivity(intent);
                            break;
                        }

                    }
                }
            };
    private Button.OnClickListener moListener = new
            Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    intent = new Intent(ELDER.this, Level.class);
                    startActivity(intent);
                }
            };
    private Button.OnClickListener mapListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mapIntent = new Intent(ELDER.this, Mapper.class);
            startActivity(mapIntent);
        }
    };
    private Button.OnClickListener musicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent musicIntent = new Intent(ELDER.this, Music.class);
            startActivity(musicIntent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("功能選單");
        setContentView(R.layout.activity_elder);
        searchre = findViewById(R.id.datasearch);
        searchre.setOnClickListener(myListener);
        motion = findViewById(R.id.motion);
        motion.setOnClickListener(moListener);
        mapbtn = findViewById(R.id.mapbtn);
        mapbtn.setOnClickListener(mapListener);
        musicbtn = findViewById(R.id.musicbtn);
        musicbtn.setOnClickListener(musicListener);
        v=findViewById(R.id.elder_fun_back);
        v.getBackground().setAlpha(125);
        getSupportActionBar().hide();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.message)
                    .setMessage(R.string.logout_msg)
                    .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.dialog_negative_button, null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
