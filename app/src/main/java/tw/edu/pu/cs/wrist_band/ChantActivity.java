package tw.edu.pu.cs.wrist_band;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChantActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 10;

    private ListView cardList;
    private CardAdapter cardAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chant);

        setTitle("讀經");

        Book[] books = new Book[] {
                new Book(getString(R.string.心經title), getString(R.string.心經text), ""),
                new Book(getString(R.string.大悲咒title), getString(R.string.大悲咒text), ""),
                new Book(getString(R.string.金剛經title), getString(R.string.金剛經text), ""),
                new Book(getString(R.string.陀羅尼title), getString(R.string.陀羅尼text), "")
//                new Book("PS-100-425", "CD:C1:16:DD:35:E4", ""),
//                new Book("PS-100-525", "CD:C1:16:DD:35:E4", ""),
//                new Book("PS-100-625", "CD:C1:16:DD:35:E4", ""),
//                new Book("PS-100-725", "CD:C1:16:DD:35:E4", "")
        };

        cardAdapter = new CardAdapter(this, R.layout.article_adapter);
        cardAdapter.addAll(books);

        cardList = findViewById(R.id.cardList);
        cardList.setAdapter(cardAdapter);

        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = cardAdapter.getItem(position);
                Intent intent = new Intent(ChantActivity.this, DetailActivity.class)
                        .putExtra("title", book.title)
                        .putExtra("text", book.text);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}