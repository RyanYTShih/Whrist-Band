package tw.edu.pu.cs.wrist_band;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class addaccount extends AppCompatActivity {

    private Button back1;

    private Spinner roleSpinner;

    private EditText edName;
    private EditText edId;
    private EditText edPasswd;

    private UserViewModel mUserViewModel;

    private static final String[] actor = {"長輩", "社工", "醫生", "社區主委"};
    private int selectedRole = Role.Elder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("新增帳號");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);

        edName = findViewById(R.id.editText3);
        edId = findViewById(R.id.editText);
        edPasswd = findViewById(R.id.editText2);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        roleSpinner = findViewById(R.id.spinner4);

        ArrayAdapter<String> peopleList = new ArrayAdapter<>(addaccount.this,
                android.R.layout.simple_spinner_dropdown_item,
                actor);

        roleSpinner.setAdapter(peopleList);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back1 = findViewById(R.id.button9);
        back1.setOnClickListener(myListener);
    }

    private Button.OnClickListener myListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button9:
                    String name = edName.getText().toString();
                    String id = edId.getText().toString();
                    String passwd = edPasswd.getText().toString();

                    if (name.equals("") || id.equals("") || passwd.equals("")) {
                        new AlertDialog.Builder(addaccount.this)
                                .setTitle(R.string.alert)
                                .setMessage("欄位不得為空")
                                .setPositiveButton(R.string.dialog_positive_button, null)
                                .show();
                        break;
                    } else {
                        User user = new User(id, name, passwd, selectedRole);
                        mUserViewModel.insert(user);
                        Toast.makeText(addaccount.this, "已為" + user.getName() + "建立新" + actor[user.getRole()] + "帳號", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
