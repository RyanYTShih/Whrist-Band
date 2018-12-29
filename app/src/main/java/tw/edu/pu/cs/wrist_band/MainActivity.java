package tw.edu.pu.cs.wrist_band;


import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserViewModel mUserViewModel;

    private LiveData<List<User>> users;

    EditText edUserid, edPasswd;
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
        mUserViewModel.insert(new User("D12345", "醫師", "4", Role.Doctor));

        users = mUserViewModel.getAllUsers();

        users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

            }
        });
    }

    public void login(View v) {

        String uid = edUserid.getText().toString();
        String pw = edPasswd.getText().toString();

        User selectedUser = null;

        List<User> userList;

        try {
            userList = users.getValue();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            new AlertDialog.Builder(this)
                .setTitle("返老「環」童")
                .setMessage("資料庫失敗")
                .setPositiveButton("OK", null)
                .show();
            return;
        }

        for (User user: userList) {
            Log.d(TAG, user.toString());
            if (user.getId().equals(uid)) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser != null) {
            if (pw.equals(selectedUser.getPasswd())) {
                switch (selectedUser.getRole()) {
                    case Role.Manage:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MANAGEMENT.class));
                        break;
                    case Role.SocialWorker:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, socialfun.class));
                        break;
                    case Role.Elder:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, ELDER.class));
                        break;
                    case Role.Doctor:
                        Toast.makeText(this, "您好，" + selectedUser.getName() + "！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MANAGEMENT.class));
                        break;
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("登入失敗")
                        .setMessage("密碼錯誤")
                        .setPositiveButton("OK", null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("查無此帳號")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}

