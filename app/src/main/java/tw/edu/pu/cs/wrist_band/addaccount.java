package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class addaccount extends AppCompatActivity {
    Intent intent;
    Button back1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);
        Spinner spinner3 = (Spinner)findViewById(R.id.spinner4);
        final String[] acter = {"角色選擇", "長輩", "醫生", "社工", "社區主委"};
        ArrayAdapter<String> peopleList = new ArrayAdapter<>(addaccount.this,
                android.R.layout.simple_spinner_dropdown_item,
                acter);
        spinner3.setAdapter(peopleList);
        back1=findViewById(R.id.button9);
        back1.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button9: {
                            intent = new Intent(addaccount.this, MANAGEMENT.class);
                            startActivity(intent);
                            finish();
                            break;
                        }

                    }
                }
            };
}
