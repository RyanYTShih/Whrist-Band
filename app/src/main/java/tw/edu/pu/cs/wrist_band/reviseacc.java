package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class reviseacc extends AppCompatActivity {

    Button back1;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("修改帳號");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviseacc);
        Spinner spinner3 = (Spinner)findViewById(R.id.spinner4);
        final String[] acter = {"角色選擇", "長輩", "醫生", "社工", "社區主委"};
        ArrayAdapter<String> peopleList = new ArrayAdapter<>(reviseacc.this,
                android.R.layout.simple_spinner_dropdown_item,
                acter);
        spinner3.setAdapter(peopleList);
        back1=findViewById(R.id.button11);
        back1.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button11: {
                            intent = new Intent(reviseacc.this, MANAGEMENT.class);
                            startActivity(intent);
                            finish();
                            break;
                        }

                    }
                }
            };
}
