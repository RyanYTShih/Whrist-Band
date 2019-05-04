package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class Level extends AppCompatActivity {
    ImageButton imageButton,imageButton2,imageButton3;
    TextView txv,txv2,txv3;
    Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        imageButton = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        txv=findViewById(R.id.txv);
        txv2 = findViewById(R.id.txv2);
        txv3 = findViewById(R.id.txv3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Level.this,ExerLevel.class);
                startActivity(intent);
            }
        });

    }
}
