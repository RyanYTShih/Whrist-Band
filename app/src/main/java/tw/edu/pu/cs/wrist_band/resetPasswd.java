package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class resetPasswd extends AppCompatActivity {
    Intent intent;
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwd);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] where = {"社區選擇","靜宜社區", "宜靜社區", "靜宜一街", "宜靜一街", "靜宜街坊"};
        ArrayAdapter<String> whereList = new ArrayAdapter<>(resetPasswd.this,
                android.R.layout.simple_spinner_dropdown_item,
                where);
        spinner.setAdapter(whereList);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        final String[] people = {"人物選擇", "林小宏", "時小唐", "張小心", "廖小勛"};
        ArrayAdapter<String> peopleList = new ArrayAdapter<>(resetPasswd.this,
                android.R.layout.simple_spinner_dropdown_item,
                people);
        spinner2.setAdapter(peopleList);
        reset=findViewById(R.id.button8);
        reset.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button8: {
                            finish();
                            break;
                        }

                    }
                }
            };
}
