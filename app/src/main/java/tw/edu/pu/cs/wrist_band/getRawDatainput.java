package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class getRawDatainput extends AppCompatActivity {

    private RawdataViewModel mRawdataViewModel;
    private LiveData<List<Rawdata>> data;
    private ArrayList<String> id_= new ArrayList<>();
    private ArrayList<String> heart_ = new ArrayList<>();
    private ArrayAdapter id_adapter,heart_adapter;
    ListView id_view,heart_view;
    Button input_ana;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_raw_datainput);
        input_ana=findViewById(R.id.button13);
        input_ana.setOnClickListener(myListener);
        id_view=findViewById(R.id.id_view);
        heart_view=findViewById(R.id.heartrate_view);

        id_adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,id_);
        id_view.setAdapter(id_adapter);

        heart_adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,heart_);
        heart_view.setAdapter(heart_adapter);

        show_data();
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

    private void show_data(){
        mRawdataViewModel = ViewModelProviders.of(this).get(RawdataViewModel.class);
        data = mRawdataViewModel.getAllRawdata();
        List<Rawdata>list = data.getValue();

        for(Rawdata r:list){
            id_.add(r.getId());
            heart_.add(r.getHeart_rate());
        }

        //id_adapter.notifyDataSetChanged();
        //heart_adapter.notifyDataSetChanged();
    }
}
