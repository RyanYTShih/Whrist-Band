package tw.edu.pu.cs.wrist_band;


import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;

    private LiveData<List<User>> users;

    EditText edUserid,edPasswd;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUserid = findViewById(R.id.ed_userid);
        edPasswd = findViewById(R.id.ed_passwd);
        bt = findViewById(R.id.button);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.insert(new User("A12345", "王主委", "1", Role.Manage));
        mUserViewModel.insert(new User("B12345", "社工", "2", Role.SocialWorker));
        mUserViewModel.insert(new User("C12345", "長輩", "3", Role.Elder));

        users = mUserViewModel.getAllUsers();

        Toast.makeText(this, users.getValue().get(0).toString(), Toast.LENGTH_SHORT).show();

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

