package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class healthre_doc extends AppCompatActivity {

    Button next;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("健康報告");
        setContentView(R.layout.activity_healthre_doc);
        Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);
        final String[] where = {"社區選擇","靜宜社區", "宜靜社區", "靜宜一街", "宜靜一街", "靜宜街坊"};
        ArrayAdapter<String> whereList = new ArrayAdapter<>(healthre_doc.this,
                android.R.layout.simple_spinner_dropdown_item,
                where);
        spinner3.setAdapter(whereList);
        Spinner spinner5 = (Spinner)findViewById(R.id.spinner5);
        final String[] people = {"人物選擇", "林小宏", "時小唐", "張小心", "廖小勛"};
        ArrayAdapter<String> peopleList = new ArrayAdapter<>(healthre_doc.this,
                android.R.layout.simple_spinner_dropdown_item,
                people);
        spinner5.setAdapter(peopleList);

        final String[] year = {"年","2019"};
        ArrayAdapter<String> yearList = new ArrayAdapter<>(healthre_doc.this,
                android.R.layout.simple_spinner_dropdown_item,
                year);

        Spinner spinner7 = (Spinner)findViewById(R.id.spinner7);
        Spinner spinner10 = (Spinner)findViewById(R.id.spinner10);
        final String[] month = {"月","1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> monthList = new ArrayAdapter<>(healthre_doc.this,
                android.R.layout.simple_spinner_dropdown_item,
                month);
        spinner7.setAdapter(monthList);
        spinner10.setAdapter(monthList);
        Spinner spinner8 = (Spinner)findViewById(R.id.spinner8);
        Spinner spinner11 = (Spinner)findViewById(R.id.spinner11);
        final String[] day = {"日","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        ArrayAdapter<String> dayList = new ArrayAdapter<>(healthre_doc.this,
                android.R.layout.simple_spinner_dropdown_item,
                day);
        spinner8.setAdapter(dayList);
        spinner11.setAdapter(dayList);
        next=findViewById(R.id.button12);
        next.setOnClickListener(myListener);

    }
    private Button.OnClickListener myListener = new
            Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button12: {
                            intent = new Intent(healthre_doc.this, healthreport.class);
                            startActivity(intent);
                            break;
                        }

                    }
                }
            };
}
