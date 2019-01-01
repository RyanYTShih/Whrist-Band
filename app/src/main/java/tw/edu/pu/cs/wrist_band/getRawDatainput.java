package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class getRawDatainput extends AppCompatActivity {
    Button input_ana;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_raw_datainput);
        input_ana=findViewById(R.id.button13);
        input_ana.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button13: {
                            intent = new Intent(getRawDatainput.this, healthreport.class);
                            startActivity(intent);
                            break;
                        }

                    }
                }
            };
}
