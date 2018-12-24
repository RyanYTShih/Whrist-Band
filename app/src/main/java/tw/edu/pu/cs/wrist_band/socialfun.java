package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class socialfun extends AppCompatActivity {
    Intent intent;
    Button coll,upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialfun);
        coll=findViewById(R.id.blecoll);
        upload=findViewById(R.id.uploadd);
        coll.setOnClickListener(myListener);
        upload.setOnClickListener(myListener);
    }

    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.blecoll: {
                            intent = new Intent(socialfun.this, BLECOLLECT.class);
                            startActivity(intent);
//                            finish();
                            break;
                        }
                        case R.id.uploadd: {
                            intent = new Intent(socialfun.this, UPLOADDATA.class);
                            startActivity(intent);
//                            finish();
                            break;
                        }
                    }
                }
    /*public void blecollect(View view){
        Intent intent = new Intent(this, blecollectmain.class);
        startActivity(intent);
        finish();
    }
    public  void uploaddata(View view){
        Intent intent = new Intent(this, socialfun.class);
        startActivity(intent);
        finish();
    }*/
            };
}
