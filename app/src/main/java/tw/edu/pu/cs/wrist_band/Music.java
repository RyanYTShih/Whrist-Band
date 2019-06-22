package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Music extends AppCompatActivity {
    ListView listView;
    ConstraintLayout bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        getSupportActionBar().hide(); //隱藏標題
        bg=findViewById(R.id.music_back);
        bg.getBackground().setAlpha(130);
        listView = (ListView)findViewById(R.id.listview);
        String[] str={"愛我別走-張震嶽","何日君再來-鄧麗君","忠孝東路走久遍-動力火車","再出發-任賢齊","愛情釀的酒-紅螞蟻"};
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(Music.this,MusicPlayer.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(Music.this,MusicPlayer2.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(Music.this,MusicPlayer3.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(Music.this,MusicPlayer4.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(Music.this,MusicPlayer5.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
    }

}