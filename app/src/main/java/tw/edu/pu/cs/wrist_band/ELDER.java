package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ELDER extends AppCompatActivity {
    Intent intent;
    Button searchre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("功能選單");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elder);
        searchre =findViewById(R.id.datasearch);
        searchre.setOnClickListener(myListener);
    }
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
}
