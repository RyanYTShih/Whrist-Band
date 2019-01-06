package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MANAGEMENT extends AppCompatActivity {
    Intent intent;
    Button addcount,delcount,reset,revise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("功能選單");
        setContentView(R.layout.activity_management);
        addcount=findViewById(R.id.button2);
        delcount=findViewById(R.id.button3);
        reset=findViewById(R.id.button4);
        revise=findViewById(R.id.button5);
        addcount.setOnClickListener(myListener);
        delcount.setOnClickListener(myListener);
        reset.setOnClickListener(myListener);
        revise.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button2: {
                            intent = new Intent(MANAGEMENT.this, addaccount.class);
                            startActivity(intent);
                            break;
                        }
                        case R.id.button3: {
                            intent = new Intent(MANAGEMENT.this, delaccount.class);
                            startActivity(intent);
                            break;
                        }
                        case R.id.button4: {
                            intent = new Intent(MANAGEMENT.this, resetPasswd.class);
                            startActivity(intent);
                            break;
                        }
                        case R.id.button5: {
                            intent = new Intent(MANAGEMENT.this, reviseaccount.class);
                            startActivity(intent);
                            break;
                        }
                    }
                }
            };
}
