package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class getRawDatainput extends AppCompatActivity {

    private static final String TAG = "getRawDatainput";
    private RawdataViewModel mRawdataViewModel;
    private LiveData<List<Rawdata>> data;
    private UserViewModel mUserViewModel;
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> bandid = new ArrayList<>();
    private ArrayList<String> heart = new ArrayList<>();
    private ArrayAdapter id_adapter,bandid_adapter,heart_adapter;
    ListView id_view,bandid_view,heart_view;
    Button input_ana;
    Intent intent;
    String id_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("原始資料");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_raw_datainput);
        input_ana=findViewById(R.id.button13);
        input_ana.setOnClickListener(myListener);

        id_view=findViewById(R.id.id_view);
        bandid_view=findViewById(R.id.bandid_view);
        heart_view=findViewById(R.id.heart_view);

        id_adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,id);
        bandid_adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,bandid);
        heart_adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,heart);

        id_view.setAdapter(id_adapter);
        bandid_view.setAdapter(bandid_adapter);
        heart_view.setAdapter(heart_adapter);


        mRawdataViewModel = ViewModelProviders.of(getRawDatainput.this).get(RawdataViewModel.class);
        data = mRawdataViewModel.getAllRawdata();
        data.observe(getRawDatainput.this, new Observer<List<Rawdata>>() {
            @Override
            public void onChanged(@Nullable List<Rawdata> rawdata) {
                Log.d(TAG, "rawdata list updated");
                if (rawdata == null)
                    return;
                try {
                    getRawDatainput.this.data = mRawdataViewModel.getAllRawdata();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                for(Rawdata r : rawdata){
                    id.add(r.getId());
                    bandid.add(r.getBand_id());
                    heart.add(r.getHeart_rate());
                }
                id_adapter.notifyDataSetChanged();
                bandid_adapter.notifyDataSetChanged();
                heart_adapter.notifyDataSetChanged();
            }
        });

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
