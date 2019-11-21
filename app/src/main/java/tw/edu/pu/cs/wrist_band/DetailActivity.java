package tw.edu.pu.cs.wrist_band;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private String title;
    private String text;
    private TextView contentText;
    private ScrollView scrollView;
    private FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();
        title = intent.getStringExtra("title");
        text = intent.getStringExtra("text");
        setTitle(title);

        contentText = findViewById(R.id.content);
        contentText.setText(text);

        scrollView = findViewById(R.id.contentScrollView);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "將開始唸誦經文", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new TextToSpeech(DetailActivity.this, text);
            }
        });

        scrollView.setOnScrollChangeListener(scrollChangeListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private View.OnScrollChangeListener scrollChangeListener = new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//            Log.d(TAG, scrollY + "");
            if ((scrollY > oldScrollY || scrollY >= v.getHeight()) && scrollY > 0) {
                fab.hide();
            } else {
                fab.show();
            }
        }
    };
}
