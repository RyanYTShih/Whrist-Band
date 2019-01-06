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
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private UserViewModel mUserViewModel;

    private LiveData<List<User>> users;
    private static final User[] sampleUsers = {
            new User("A12345", "主委", "1", Role.Manager),
            new User("B12345", "社工", "2", Role.SocialWorker),
            new User("C12345", "長輩", "3", Role.Elder),
            new User("D12345", "醫師", "4", Role.Doctor)
    };

    private EditText edUserid, edPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //隱藏標題
        edUserid = findViewById(R.id.ed_userid);
        edPasswd = findViewById(R.id.ed_passwd);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        for (User user: sampleUsers) {
            mUserViewModel.insert(user);
        }


        users = mUserViewModel.getAllUsers();

        users.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "user list updated");
                if (users == null)
                    return;
                try {
                    MainActivity.this.users = mUserViewModel.getAllUsers();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
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
                    case Role.Manager:
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
                        startActivity(new Intent(this, doctorfun.class));
                        break;
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("登入失敗")
                        .setMessage("密碼錯誤")
                        .setPositiveButton(R.string.dialog_positive_button, null)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("查無此帳號")
                    .setPositiveButton(R.string.dialog_positive_button, null)
                    .show();
        }
    }
}

