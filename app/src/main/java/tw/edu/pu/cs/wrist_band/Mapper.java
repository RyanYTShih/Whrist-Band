package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Mapper extends AppCompatActivity {
    ImageButton pin,pin2,pin3,pin4,pin5,pin6,pin7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapper);
        getSupportActionBar().hide(); //隱藏標題
        pin = findViewById(R.id.pin);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        pin4 = findViewById(R.id.pin4);
        pin5 = findViewById(R.id.pin5);
        pin6 = findViewById(R.id.pin6);
        pin7 = findViewById(R.id.pin7);

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mapper.this,PinActivity.class);
                startActivity(intent);
            }
        });
        pin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Mapper.this,Pin2Activity.class);
                startActivity(intent2);
            }
        });
        pin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Mapper.this,Pin3Activity.class);
                startActivity(intent3);
            }
        });
        pin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Mapper.this,Pin4Activity.class);
                startActivity(intent4);
            }
        });
        pin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Mapper.this,Pin5Activity.class);
                startActivity(intent5);
            }
        });
        pin6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(Mapper.this,Pin6Activity.class);
                startActivity(intent6);
            }
        });
        pin7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(Mapper.this,Pin7Activity.class);
                startActivity(intent7);
            }
        });
    }
}
