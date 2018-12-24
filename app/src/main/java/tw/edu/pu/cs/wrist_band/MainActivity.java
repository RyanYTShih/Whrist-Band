package tw.edu.pu.cs.wrist_band;


import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edUserid,edPasswd;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUserid = findViewById(R.id.ed_userid);
        edPasswd = findViewById(R.id.ed_passwd);
        bt =findViewById(R.id.button);



    }

    public void login(View v) {
//        EditText edUserid = findViewById(R.id.ed_userid);
//        EditText edPasswd = findViewById(R.id.ed_passwd);
        String uid = edUserid.getText().toString();
        String pw = edPasswd.getText().toString();
        if (uid.equals("worker") && pw.equals("1")) { //登入成功
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, socialfun.class);
            startActivity(intent);
//            finish();
        }
        else if (uid.equals("elder") && pw.equals("1")) { //登入成功
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, ELDER.class);
            startActivity(intent);
//            finish();
        }
        else if (uid.equals("chair") && pw.equals("1")) { //登入成功
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MANAGEMENT.class);
            startActivity(intent);
//            finish();
        }
        else {  //登入失敗
            new AlertDialog.Builder(this)
                    .setTitle("返老「環」童")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

}

