package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Level extends AppCompatActivity {
    ImageButton imageButton3, imageButton2, imageButton;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton = findViewById(R.id.imageButton);
        getSupportActionBar().hide();
        View v= findViewById(R.id.elder_level_back);
        v.getBackground().setAlpha(150);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Level.this, ExerLevel.class);
                startActivity(intent);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Level.this, EzGame.class);
                startActivity(intent);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Level.this, Mario.class);
                startActivity(intent);
            }
        });
        }

    }

