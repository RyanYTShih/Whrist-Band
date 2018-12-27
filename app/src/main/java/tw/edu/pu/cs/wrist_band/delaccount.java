package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class delaccount extends AppCompatActivity {
    Intent intent;
    Button back1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delaccount);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] where = {"靜宜社區", "宜靜社區", "靜宜一街", "宜靜一街", "靜宜街坊"};
        ArrayAdapter<String> whereList = new ArrayAdapter<>(delaccount.this,
                android.R.layout.simple_spinner_dropdown_item,
                where);
        spinner.setAdapter(whereList);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        final String[] people = {"人物選擇", "林小宏", "時小唐", "張小心", "廖小勛"};
        ArrayAdapter<String> peopleList = new ArrayAdapter<>(delaccount.this,
                android.R.layout.simple_spinner_dropdown_item,
                people);
        spinner2.setAdapter(peopleList);
        back1=findViewById(R.id.button8);
        back1.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button8: {
                            intent = new Intent(delaccount.this, MANAGEMENT.class);
                            startActivity(intent);
                            finish();
                            break;
                        }

                    }
                }
            };
}
