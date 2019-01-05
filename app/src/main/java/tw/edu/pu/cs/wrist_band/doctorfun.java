package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class doctorfun extends AppCompatActivity {
    Intent intent;
    Button rawdata,analyze,healthre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("功能選單");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorfun);
        rawdata=findViewById(R.id.button2);
        analyze=findViewById(R.id.button3);
        healthre=findViewById(R.id.button4);
        rawdata.setOnClickListener(myListener);
        analyze.setOnClickListener(myListener);
        healthre.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button2: {
                            intent = new Intent(doctorfun.this, getRawDatainput.class);
                            startActivity(intent);
                            break;
                        }
                        case R.id.button3: {
                            intent = new Intent(doctorfun.this, analyzeData.class);
                            startActivity(intent);
                            break;
                        }
                        case R.id.button4: {
                            intent = new Intent(doctorfun.this, healthre_doc.class);
                            startActivity(intent);
                            break;
                        }
                    }
                }
            };
}
